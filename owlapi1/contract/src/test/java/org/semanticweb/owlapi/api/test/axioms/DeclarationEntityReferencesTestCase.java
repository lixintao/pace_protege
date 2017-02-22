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
package org.semanticweb.owlapi.api.test.axioms;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.TestUtils;
import org.semanticweb.owlapi.api.test.baseclasses.AbstractOWLAPITestCase;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * A test case which ensures that an ontology contains entity references when
 * that ontology only contains entity declaration axioms. In other words, entity
 * declaration axioms produce the correct entity references.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group, Date: 01-May-2007
 */
@SuppressWarnings("javadoc")
public class DeclarationEntityReferencesTestCase extends AbstractOWLAPITestCase {

    @Test
    public void testOWLClassDeclarationAxiom() throws Exception {
        OWLClass cls = Class(TestUtils.createIRI());
        OWLAxiom ax = Declaration(cls);
        OWLOntologyManager man = getManager();
        OWLOntology ont = man.createOntology(TestUtils.createIRI());
        man.applyChange(new AddAxiom(ont, ax));
        assertTrue(ont.getClassesInSignature().contains(cls));
    }

    @Test
    public void testOWLObjectPropertyDeclarationAxiom() throws Exception {
        OWLObjectProperty prop = ObjectProperty(TestUtils.createIRI());
        OWLAxiom ax = Declaration(prop);
        OWLOntologyManager man = getManager();
        OWLOntology ont = man.createOntology(TestUtils.createIRI());
        man.applyChange(new AddAxiom(ont, ax));
        assertTrue(ont.getObjectPropertiesInSignature().contains(prop));
    }

    @Test
    public void testOWLDataPropertyDeclarationAxiom() throws Exception {
        OWLDataProperty prop = DataProperty(TestUtils.createIRI());
        OWLAxiom ax = Declaration(prop);
        OWLOntologyManager man = getManager();
        OWLOntology ont = man.createOntology(TestUtils.createIRI());
        man.applyChange(new AddAxiom(ont, ax));
        assertTrue(ont.getDataPropertiesInSignature().contains(prop));
    }

    @Test
    public void testOWLIndividualDeclarationAxiom() throws Exception {
        OWLNamedIndividual ind = NamedIndividual(TestUtils.createIRI());
        OWLAxiom ax = Declaration(ind);
        OWLOntologyManager man = getManager();
        OWLOntology ont = man.createOntology(TestUtils.createIRI());
        man.applyChange(new AddAxiom(ont, ax));
        assertTrue(ont.getIndividualsInSignature().contains(ind));
    }
}
