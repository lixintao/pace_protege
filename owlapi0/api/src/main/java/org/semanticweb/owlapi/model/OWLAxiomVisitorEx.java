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
package org.semanticweb.owlapi.model;

/**
 * An interface for objects that can accept visits from axioms. (See the <a
 * href="http://en.wikipedia.org/wiki/Visitor_pattern">Visitor Patterns</a>)
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group, Date: 16-Apr-2008
 * @param <O>
 *        visitor type
 */
public interface OWLAxiomVisitorEx<O> extends OWLAnnotationAxiomVisitorEx<O> {

    /**
     * visit OWLSubClassOfAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLSubClassOfAxiom axiom);

    /**
     * visit OWLNegativeObjectPropertyAssertionAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLNegativeObjectPropertyAssertionAxiom axiom);

    /**
     * visit OWLAsymmetricObjectPropertyAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLAsymmetricObjectPropertyAxiom axiom);

    /**
     * visit OWLReflexiveObjectPropertyAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLReflexiveObjectPropertyAxiom axiom);

    /**
     * visit OWLDisjointClassesAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLDisjointClassesAxiom axiom);

    /**
     * visit OWLDataPropertyDomainAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLDataPropertyDomainAxiom axiom);

    /**
     * visit OWLObjectPropertyDomainAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLObjectPropertyDomainAxiom axiom);

    /**
     * visit OWLEquivalentObjectPropertiesAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLEquivalentObjectPropertiesAxiom axiom);

    /**
     * visit OWLNegativeDataPropertyAssertionAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLNegativeDataPropertyAssertionAxiom axiom);

    /**
     * visit OWLDifferentIndividualsAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLDifferentIndividualsAxiom axiom);

    /**
     * visit OWLDisjointDataPropertiesAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLDisjointDataPropertiesAxiom axiom);

    /**
     * visit OWLDisjointObjectPropertiesAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLDisjointObjectPropertiesAxiom axiom);

    /**
     * visit OWLObjectPropertyRangeAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLObjectPropertyRangeAxiom axiom);

    /**
     * visit OWLObjectPropertyAssertionAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLObjectPropertyAssertionAxiom axiom);

    /**
     * visit OWLFunctionalObjectPropertyAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLFunctionalObjectPropertyAxiom axiom);

    /**
     * visit OWLSubObjectPropertyOfAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLSubObjectPropertyOfAxiom axiom);

    /**
     * visit OWLDisjointUnionAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLDisjointUnionAxiom axiom);

    /**
     * visit OWLDeclarationAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLDeclarationAxiom axiom);

    @Override
    /** visit OWLAnnotationAssertionAxiom type
     * @param axiom object to visit
     * @return visitor value */
    O visit(OWLAnnotationAssertionAxiom axiom);

    /**
     * visit OWLSymmetricObjectPropertyAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLSymmetricObjectPropertyAxiom axiom);

    /**
     * visit OWLDataPropertyRangeAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLDataPropertyRangeAxiom axiom);

    /**
     * visit OWLFunctionalDataPropertyAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLFunctionalDataPropertyAxiom axiom);

    /**
     * visit OWLEquivalentDataPropertiesAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLEquivalentDataPropertiesAxiom axiom);

    /**
     * visit OWLClassAssertionAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLClassAssertionAxiom axiom);

    /**
     * visit OWLEquivalentClassesAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLEquivalentClassesAxiom axiom);

    /**
     * visit OWLDataPropertyAssertionAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLDataPropertyAssertionAxiom axiom);

    /**
     * visit OWLTransitiveObjectPropertyAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLTransitiveObjectPropertyAxiom axiom);

    /**
     * visit OWLIrreflexiveObjectPropertyAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLIrreflexiveObjectPropertyAxiom axiom);

    /**
     * visit OWLSubDataPropertyOfAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLSubDataPropertyOfAxiom axiom);

    /**
     * visit OWLInverseFunctionalObjectPropertyAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLInverseFunctionalObjectPropertyAxiom axiom);

    /**
     * visit OWLSameIndividualAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLSameIndividualAxiom axiom);

    /**
     * visit OWLSubPropertyChainOfAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLSubPropertyChainOfAxiom axiom);

    /**
     * visit OWLInverseObjectPropertiesAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLInverseObjectPropertiesAxiom axiom);

    /**
     * visit OWLHasKeyAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLHasKeyAxiom axiom);

    /**
     * visit OWLDatatypeDefinitionAxiom type
     * 
     * @param axiom
     *        object to visit
     * @return visitor value
     */
    O visit(OWLDatatypeDefinitionAxiom axiom);

    /**
     * visit SWRLRule type
     * 
     * @param rule
     *        object to visit
     * @return visitor value
     */
    O visit(SWRLRule rule);
}
