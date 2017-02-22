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
package org.semanticweb.owlapi.api.test.syntax.rdfxml;

import java.io.File;
import java.net.URL;

import org.coode.owlapi.rdfxml.parser.RDFXMLParserFactory;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.io.OWLParserFactoryRegistry;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;
import uk.ac.manchester.cs.owl.owlapi.ParsableOWLOntologyFactory;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 16-Mar-2007
 */
@SuppressWarnings("javadoc")
public class RDFParserTestCase {

    private OWLOntologyManager man;

    @Before
    public void setUp() {
        // Use the reference implementation
        man = new OWLOntologyManagerImpl(new OWLDataFactoryImpl());
        OWLParserFactoryRegistry.getInstance().registerParserFactory(
                new RDFXMLParserFactory());
        ParsableOWLOntologyFactory factory = new ParsableOWLOntologyFactory();
        man.addOntologyFactory(factory);
    }

    @Test
    public void testOWLAPI() throws Exception {
        parseFiles("/owlapi/");
    }

    private void parseFiles(String base) throws Exception {
        URL url = getClass().getResource(base);
        File file = new File(url.toURI());
        for (File testSuiteFolder : file.listFiles()) {
            if (testSuiteFolder.isDirectory()) {
                for (File ontologyFile : testSuiteFolder.listFiles()) {
                    if (ontologyFile.getName().endsWith(".rdf")
                            || ontologyFile.getName().endsWith(".owlapi")) {
                        OWLOntology ont = man
                                .loadOntologyFromOntologyDocument(ontologyFile);
                        man.removeOntology(ont);
                    }
                }
            }
        }
    }
}
