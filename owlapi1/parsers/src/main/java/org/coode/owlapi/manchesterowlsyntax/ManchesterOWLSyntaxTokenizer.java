/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.coode.owlapi.manchesterowlsyntax;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 19-May-2008
 */
public class ManchesterOWLSyntaxTokenizer {

    /** EOF */
    public static final String EOF = "|EOF|";

    /**
     * @param s
     *        string to check
     * @return true if EOF
     */
    public static boolean EOF(String s) {
        return EOF.equals(s);
    }

    protected Set<Character> skip = new HashSet<Character>();
    protected Set<Character> commentDelimiters = new HashSet<Character>();
    protected Set<Character> delims = new HashSet<Character>();
    private String buffer;
    private int pos;
    private int col;
    private int row;
    int startPos = 0;
    int startCol = 1;
    int startRow = 1;
    List<Token> tokens = new ArrayList<Token>();
    private StringBuilder sb;
    private static final char ESCAPE_CHAR = '\\';

    /**
     * @param buffer
     *        buffer
     */
    public ManchesterOWLSyntaxTokenizer(String buffer) {
        this.buffer = buffer;
        skip.add(' ');
        skip.add('\n');
        skip.add('\r');
        skip.add('\t');
        commentDelimiters.add('#');
        commentDelimiters.add('*');
        delims.add('(');
        delims.add(')');
        delims.add('[');
        delims.add(']');
        delims.add(',');
        delims.add('{');
        delims.add('}');
        delims.add('^');
        delims.add('@');
        delims.add('<');
        delims.add('>');
        delims.add('=');
        delims.add('?');
    }

    private void reset() {
        sb = new StringBuilder();
        tokens.clear();
        startRow = 1;
        startCol = 1;
        startPos = 0;
        pos = 0;
        row = 1;
        col = 1;
    }

    /** @return tokens */
    public List<Token> tokenize() {
        reset();
        int bufferLen = buffer.length();
        char lastChar = ' ';
        while (pos < bufferLen) {
            char ch = readChar();
            if (ch == ESCAPE_CHAR) {
                lastChar = ch;
                ch = readChar();
            }
            if (ch == '\"' && lastChar != '\\') {
                readString('\"', true);
            } else if (ch == '\'' && lastChar != '\\') {
                readString('\'', true);
            } else if (ch == '<') {
                // Potentially the start of an IRI
                readIRI();
            } else if (skip.contains(ch)) {
                consumeToken();
            } else if (commentDelimiters.contains(ch)) {
                consumeToken();
                readComment();
            } else if (delims.contains(ch)) {
                consumeToken();
                sb.append(ch);
                if (ch != '@') {
                    consumeToken();
                }
            } else {
                sb.append(ch);
            }
            lastChar = ch;
        }
        consumeToken();
        tokens.add(new Token(EOF, pos, col, row));
        return new ArrayList<Token>(tokens);
    }

    private void consumeToken() {
        if (sb.length() > 0) {
            tokens.add(new Token(sb.toString(), startPos, startCol, startRow));
            sb = new StringBuilder();
        }
        startPos = pos;
        startCol = col;
        startRow = row;
    }

    private void readComment() {
        char ch = '#';
        while (ch != '\n' && pos < buffer.length()) {
            ch = readChar();
        }
        consumeToken();
    }

    private void readString(char terminator, boolean appendTerminator) {
        if (appendTerminator) {
            sb.append(terminator);
        }
        while (pos < buffer.length()) {
            char ch = readChar();
            if (ch == ESCAPE_CHAR) {
                int j = pos + 1;
                if (j < buffer.length()) {
                    char escapedChar = readChar();
                    if (escapedChar == '\"' || escapedChar == '\''
                            || escapedChar == '\\') {
                        sb.append(escapedChar);
                    } else {
                        sb.append(ch);
                        sb.append(escapedChar);
                    }
                } else {
                    sb.append('\\');
                }
            } else if (ch == terminator) {
                if (appendTerminator) {
                    sb.append(ch);
                }
                break;
            } else {
                sb.append(ch);
            }
        }
        consumeToken();
    }

    private void readIRI() {
        sb = new StringBuilder("<");
        int startPos1 = pos;
        while (pos < buffer.length()) {
            char ch = readChar();
            if (Character.isWhitespace(ch)) {
                // Not an IRI -- go back to where we started
                pos = startPos1;
                sb = new StringBuilder("<");
                consumeToken();
                break;
            } else if (ch == '>') {
                // End of IRI
                sb.append(">");
                consumeToken();
                break;
            } else {
                sb.append(ch);
            }
        }
    }

    private char readChar() {
        char ch = buffer.charAt(pos);
        pos++;
        col++;
        if (ch == '\n') {
            row++;
            col = 0;
        }
        return ch;
    }

    /** token */
    public static class Token {

        private String token;
        private int pos;
        private int col;
        private int row;

        /**
         * @param token
         *        token
         * @param pos
         *        pos
         * @param col
         *        col
         * @param row
         *        row
         */
        public Token(String token, int pos, int col, int row) {
            this.token = token;
            this.pos = pos;
            this.col = col;
            this.row = row;
        }

        /** @return token */
        public String getToken() {
            return token;
        }

        /** @return position */
        public int getPos() {
            return pos;
        }

        /** @return column */
        public int getCol() {
            return col;
        }

        /** @return row */
        public int getRow() {
            return row;
        }

        @Override
        public String toString() {
            return token + " [" + pos + ", " + col + ", " + row + "]";
        }
    }
}
