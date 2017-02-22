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
package org.semanticweb.owlapi.api.test.ontology;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.AbstractOWLAPITestCase;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.util.OWLEntityRenamer;

/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group, Date: 29-Nov-2009
 */
@SuppressWarnings("javadoc")
public class RenameEntityTestCase extends AbstractOWLAPITestCase {

    @Test
    public void testRenameClass() {
        OWLOntology ont = getOWLOntology("testont");
        OWLClass clsAIRI1 = Class(getIRI("ClsA1"));
        OWLClass clsAIRI2 = Class(getIRI("ClsA2"));
        OWLClass clsB = Class(getIRI("ClsB"));
        OWLClass clsC = Class(getIRI("ClsC"));
        OWLObjectPropertyExpression propA = ObjectProperty(getIRI("propA"));
        OWLDataPropertyExpression propB = DataProperty(getIRI("propA"));
        OWLIndividual indA = NamedIndividual(getIRI("indA"));
        OWLAnnotationProperty annoProp = AnnotationProperty(getIRI("annoProp"));
        Set<OWLAxiom> axioms1 = new HashSet<OWLAxiom>();
        axioms1.add(SubClassOf(clsAIRI1, clsB));
        axioms1.add(EquivalentClasses(clsAIRI1, clsC));
        axioms1.add(DisjointClasses(clsAIRI1, clsC));
        axioms1.add(ObjectPropertyDomain(propA, clsAIRI1));
        axioms1.add(ObjectPropertyRange(propA, clsAIRI1));
        axioms1.add(DataPropertyDomain(propB, clsAIRI1));
        axioms1.add(ClassAssertion(clsAIRI1, indA));
        axioms1.add(AnnotationAssertion(annoProp, clsAIRI1.getIRI(),
                Literal("X")));
        getManager().addAxioms(ont, axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<OWLAxiom>();
        axioms2.add(SubClassOf(clsAIRI2, clsB));
        axioms2.add(EquivalentClasses(clsAIRI2, clsC));
        axioms2.add(DisjointClasses(clsAIRI2, clsC));
        axioms2.add(ObjectPropertyDomain(propA, clsAIRI2));
        axioms2.add(ObjectPropertyRange(propA, clsAIRI2));
        axioms2.add(DataPropertyDomain(propB, clsAIRI2));
        axioms2.add(ClassAssertion(clsAIRI2, indA));
        axioms2.add(AnnotationAssertion(annoProp, clsAIRI2.getIRI(),
                Literal("X")));
        OWLEntityRenamer entityRenamer = new OWLEntityRenamer(getManager(),
                Collections.singleton(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(clsAIRI1,
                clsAIRI2.getIRI());
        getManager().applyChanges(changes);
        assertEquals(ont.getAxioms(), axioms2);
        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(
                clsAIRI2.getIRI(), clsAIRI1.getIRI());
        getManager().applyChanges(changes2);
        assertEquals(ont.getAxioms(), axioms1);
    }

    @Test
    public void testRenameObjectProperty() {
        OWLOntology ont = getOWLOntology("testont");
        OWLClass clsA = Class(getIRI("ClsA"));
        OWLObjectProperty propA = ObjectProperty(getIRI("propA"));
        OWLObjectProperty propA2 = ObjectProperty(getIRI("propA2"));
        OWLObjectPropertyExpression propB = ObjectProperty(getIRI("propB"))
                .getInverseProperty();
        OWLIndividual indA = NamedIndividual(getIRI("indA"));
        OWLIndividual indB = NamedIndividual(getIRI("indB"));
        OWLAnnotationProperty annoProp = AnnotationProperty(getIRI("annoProp"));
        Set<OWLAxiom> axioms1 = new HashSet<OWLAxiom>();
        axioms1.add(SubObjectPropertyOf(propA, propB));
        axioms1.add(EquivalentObjectProperties(propA, propB));
        axioms1.add(DisjointObjectProperties(propA, propB));
        axioms1.add(ObjectPropertyDomain(propA, clsA));
        axioms1.add(ObjectPropertyRange(propA, clsA));
        axioms1.add(FunctionalObjectProperty(propA));
        axioms1.add(InverseFunctionalObjectProperty(propA));
        axioms1.add(SymmetricObjectProperty(propA));
        axioms1.add(AsymmetricObjectProperty(propA));
        axioms1.add(TransitiveObjectProperty(propA));
        axioms1.add(ReflexiveObjectProperty(propA));
        axioms1.add(IrreflexiveObjectProperty(propA));
        axioms1.add(ObjectPropertyAssertion(propA, indA, indB));
        axioms1.add(NegativeObjectPropertyAssertion(propA, indA, indB));
        axioms1.add(AnnotationAssertion(annoProp, propA.getIRI(), Literal("X")));
        getManager().addAxioms(ont, axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<OWLAxiom>();
        axioms2.add(SubObjectPropertyOf(propA2, propB));
        axioms2.add(EquivalentObjectProperties(propA2, propB));
        axioms2.add(DisjointObjectProperties(propA2, propB));
        axioms2.add(ObjectPropertyDomain(propA2, clsA));
        axioms2.add(ObjectPropertyRange(propA2, clsA));
        axioms2.add(FunctionalObjectProperty(propA2));
        axioms2.add(InverseFunctionalObjectProperty(propA2));
        axioms2.add(SymmetricObjectProperty(propA2));
        axioms2.add(AsymmetricObjectProperty(propA2));
        axioms2.add(TransitiveObjectProperty(propA2));
        axioms2.add(ReflexiveObjectProperty(propA2));
        axioms2.add(IrreflexiveObjectProperty(propA2));
        axioms2.add(ObjectPropertyAssertion(propA2, indA, indB));
        axioms2.add(NegativeObjectPropertyAssertion(propA2, indA, indB));
        axioms2.add(AnnotationAssertion(annoProp, propA2.getIRI(), Literal("X")));
        OWLEntityRenamer entityRenamer = new OWLEntityRenamer(getManager(),
                Collections.singleton(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(propA,
                propA2.getIRI());
        getManager().applyChanges(changes);
        assertEquals(ont.getAxioms(), axioms2);
        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(
                propA2.getIRI(), propA.getIRI());
        getManager().applyChanges(changes2);
        assertEquals(ont.getAxioms(), axioms1);
    }

    @Test
    public void testRenameDataProperty() {
        OWLOntology ont = getOWLOntology("testont");
        OWLClass clsA = Class(getIRI("ClsA"));
        OWLDataProperty propA = DataProperty(getIRI("propA"));
        OWLDataProperty propA2 = DataProperty(getIRI("propA2"));
        OWLDataPropertyExpression propB = DataProperty(getIRI("propB"));
        OWLIndividual indA = NamedIndividual(getIRI("indA"));
        OWLAnnotationProperty annoProp = AnnotationProperty(getIRI("annoProp"));
        Set<OWLAxiom> axioms1 = new HashSet<OWLAxiom>();
        axioms1.add(SubDataPropertyOf(propA, propB));
        axioms1.add(EquivalentDataProperties(propA, propB));
        axioms1.add(DisjointDataProperties(propA, propB));
        axioms1.add(DataPropertyDomain(propA, clsA));
        axioms1.add(DataPropertyRange(propA, TopDatatype()));
        axioms1.add(FunctionalDataProperty(propA));
        axioms1.add(DataPropertyAssertion(propA, indA, Literal(33)));
        axioms1.add(NegativeDataPropertyAssertion(propA, indA, Literal(44)));
        axioms1.add(AnnotationAssertion(annoProp, propA.getIRI(), Literal("X")));
        getManager().addAxioms(ont, axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<OWLAxiom>();
        axioms2.add(SubDataPropertyOf(propA2, propB));
        axioms2.add(EquivalentDataProperties(propA2, propB));
        axioms2.add(DisjointDataProperties(propA2, propB));
        axioms2.add(DataPropertyDomain(propA2, clsA));
        axioms2.add(DataPropertyRange(propA2, TopDatatype()));
        axioms2.add(FunctionalDataProperty(propA2));
        axioms2.add(DataPropertyAssertion(propA2, indA, Literal(33)));
        axioms2.add(NegativeDataPropertyAssertion(propA2, indA, Literal(44)));
        axioms2.add(AnnotationAssertion(annoProp, propA2.getIRI(), Literal("X")));
        OWLEntityRenamer entityRenamer = new OWLEntityRenamer(getManager(),
                Collections.singleton(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(propA,
                propA2.getIRI());
        getManager().applyChanges(changes);
        assertEquals(ont.getAxioms(), axioms2);
        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(
                propA2.getIRI(), propA.getIRI());
        getManager().applyChanges(changes2);
        assertEquals(ont.getAxioms(), axioms1);
    }

    @Test
    public void testRenameIndividual() {
        OWLOntology ont = getOWLOntology("testont");
        OWLClass clsA = Class(getIRI("ClsA"));
        OWLDataProperty propA = DataProperty(getIRI("propA"));
        OWLObjectProperty propB = ObjectProperty(getIRI("propB"));
        OWLNamedIndividual indA = NamedIndividual(getIRI("indA"));
        OWLNamedIndividual indB = NamedIndividual(getIRI("indA"));
        OWLAnnotationProperty annoProp = AnnotationProperty(getIRI("annoProp"));
        Set<OWLAxiom> axioms1 = new HashSet<OWLAxiom>();
        axioms1.add(ClassAssertion(clsA, indA));
        axioms1.add(DataPropertyAssertion(propA, indA, Literal(33)));
        axioms1.add(NegativeDataPropertyAssertion(propA, indA, Literal(44)));
        axioms1.add(AnnotationAssertion(annoProp, propA.getIRI(), Literal("X")));
        axioms1.add(ObjectPropertyAssertion(propB, indA, indB));
        axioms1.add(NegativeObjectPropertyAssertion(propB, indA, indB));
        getManager().addAxioms(ont, axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<OWLAxiom>();
        axioms2.add(ClassAssertion(clsA, indB));
        axioms2.add(DataPropertyAssertion(propA, indB, Literal(33)));
        axioms2.add(NegativeDataPropertyAssertion(propA, indB, Literal(44)));
        axioms2.add(AnnotationAssertion(annoProp, propA.getIRI(), Literal("X")));
        axioms2.add(ObjectPropertyAssertion(propB, indB, indB));
        axioms2.add(NegativeObjectPropertyAssertion(propB, indB, indB));
        OWLEntityRenamer entityRenamer = new OWLEntityRenamer(getManager(),
                Collections.singleton(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(indA,
                indB.getIRI());
        getManager().applyChanges(changes);
        assertEquals(ont.getAxioms(), axioms2);
        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(
                indB.getIRI(), indA.getIRI());
        getManager().applyChanges(changes2);
        assertEquals(ont.getAxioms(), axioms1);
    }

    @Test
    public void testRenameDatatype() {
        OWLOntology ont = getOWLOntology("testont");
        OWLDatatype dtA = Datatype(getIRI("DtA"));
        OWLDatatype dtB = Datatype(getIRI("DtB"));
        OWLDatatype dtC = Datatype(getIRI("DtC"));
        OWLDataRange rng1 = DataIntersectionOf(dtA, dtB);
        OWLDataRange rng1R = DataIntersectionOf(dtC, dtB);
        OWLDataRange rng2 = DataUnionOf(dtA, dtB);
        OWLDataRange rng2R = DataUnionOf(dtC, dtB);
        OWLDataRange rng3 = DataComplementOf(dtA);
        OWLDataRange rng3R = DataComplementOf(dtC);
        OWLDataPropertyExpression propB = DataProperty(getIRI("propA"));
        Set<OWLAxiom> axioms1 = new HashSet<OWLAxiom>();
        axioms1.add(DataPropertyRange(propB, rng1));
        axioms1.add(DataPropertyRange(propB, rng2));
        axioms1.add(DataPropertyRange(propB, rng3));
        getManager().addAxioms(ont, axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<OWLAxiom>();
        axioms2.add(DataPropertyRange(propB, rng1R));
        axioms2.add(DataPropertyRange(propB, rng2R));
        axioms2.add(DataPropertyRange(propB, rng3R));
        OWLEntityRenamer entityRenamer = new OWLEntityRenamer(getManager(),
                Collections.singleton(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(dtA,
                dtC.getIRI());
        getManager().applyChanges(changes);
        assertEquals(ont.getAxioms(), axioms2);
        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(
                dtC.getIRI(), dtA.getIRI());
        getManager().applyChanges(changes2);
        assertEquals(ont.getAxioms(), axioms1);
    }

    @Test
    public void testRenameAnnotationProperty() {
        OWLOntology ont = getOWLOntology("testont");
        OWLNamedIndividual indA = NamedIndividual(getIRI("indA"));
        OWLNamedIndividual indB = NamedIndividual(getIRI("indB"));
        OWLAnnotationProperty annoProp = AnnotationProperty(getIRI("annoProp"));
        OWLAnnotationProperty annoPropR = AnnotationProperty(getIRI("annoPropR"));
        OWLAnnotationProperty annoProp2 = AnnotationProperty(getIRI("annoProp2"));
        Set<OWLAxiom> axioms1 = new HashSet<OWLAxiom>();
        axioms1.add(Declaration(annoProp));
        axioms1.add(AnnotationAssertion(annoProp, indA.getIRI(), indB.getIRI()));
        axioms1.add(SubAnnotationPropertyOf(annoProp, annoProp2));
        axioms1.add(AnnotationPropertyRange(annoProp, indA.getIRI()));
        axioms1.add(AnnotationPropertyDomain(annoProp, indA.getIRI()));
        getManager().addAxioms(ont, axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<OWLAxiom>();
        axioms2.add(Declaration(annoPropR));
        axioms2.add(AnnotationAssertion(annoPropR, indA.getIRI(), indB.getIRI()));
        axioms2.add(SubAnnotationPropertyOf(annoPropR, annoProp2));
        axioms2.add(AnnotationPropertyRange(annoPropR, indA.getIRI()));
        axioms2.add(AnnotationPropertyDomain(annoPropR, indA.getIRI()));
        OWLEntityRenamer entityRenamer = new OWLEntityRenamer(getManager(),
                Collections.singleton(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(annoProp,
                annoPropR.getIRI());
        getManager().applyChanges(changes);
        assertEquals(ont.getAxioms(), axioms2);
        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(
                annoPropR.getIRI(), annoProp.getIRI());
        getManager().applyChanges(changes2);
        assertEquals(ont.getAxioms(), axioms1);
    }
}
