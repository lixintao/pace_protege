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
package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 13-Dec-2006
 */
public class OWLLiteralElementHandler extends
        AbstractOWLElementHandler<OWLLiteral> {

    private OWLLiteral literal;
    private IRI iri;
    private String lang;

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLLiteralElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void startElement(String name) throws OWLXMLParserException {
        super.startElement(name);
    }

    @Override
    public void attribute(String localName, String value)
            throws OWLParserException {
        if (localName.equals(OWLXMLVocabulary.DATATYPE_IRI.getShortName())) {
            iri = getIRI(value);
        } else if (localName.equals("lang")) {
            lang = value;
        }
    }

    @Override
    public void endElement() throws OWLParserException,
            UnloadableImportException {
        if (iri != null && !iri.isPlainLiteral()) {
            literal = getOWLDataFactory().getOWLLiteral(getText(),
                    getOWLDataFactory().getOWLDatatype(iri));
        } else {
            literal = getOWLDataFactory().getOWLLiteral(getText(), lang);
        }
        lang = null;
        iri = null;
        getParentHandler().handleChild(this);
    }

    @Override
    public OWLLiteral getOWLObject() {
        return literal;
    }

    @Override
    public boolean isTextContentPossible() {
        return true;
    }
}
