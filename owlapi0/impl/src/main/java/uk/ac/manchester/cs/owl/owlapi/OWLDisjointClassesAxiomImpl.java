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
package uk.ac.manchester.cs.owl.owlapi;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 26-Oct-2006
 */
public class OWLDisjointClassesAxiomImpl extends OWLNaryClassAxiomImpl
        implements OWLDisjointClassesAxiom {

    private static final long serialVersionUID = 30406L;

    /**
     * @param classExpressions
     *        disjoint classes
     * @param annotations
     *        annotations
     */
    public OWLDisjointClassesAxiomImpl(
            Set<? extends OWLClassExpression> classExpressions,
            Set<? extends OWLAnnotation> annotations) {
        super(classExpressions, annotations);
    }

    @Override
    public OWLDisjointClassesAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return new OWLDisjointClassesAxiomImpl(getClassExpressions(),
                NO_ANNOTATIONS);
    }

    @Override
    public OWLDisjointClassesAxiom getAnnotatedAxiom(
            Set<OWLAnnotation> annotations) {
        return new OWLDisjointClassesAxiomImpl(getClassExpressions(),
                mergeAnnos(annotations));
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && obj instanceof OWLDisjointClassesAxiom;
    }

    @Override
    public void accept(OWLAxiomVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLAxiomVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public AxiomType<?> getAxiomType() {
        return AxiomType.DISJOINT_CLASSES;
    }

    @Override
    public Set<OWLDisjointClassesAxiom> asPairwiseAxioms() {
        Set<OWLDisjointClassesAxiom> result = new HashSet<OWLDisjointClassesAxiom>();
        List<OWLClassExpression> list = getClassExpressionsAsList();
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                result.add(new OWLDisjointClassesAxiomImpl(
                        new HashSet<OWLClassExpression>(Arrays.asList(
                                list.get(i), list.get(j))), NO_ANNOTATIONS));
            }
        }
        return result;
    }

    @Override
    public Set<OWLSubClassOfAxiom> asOWLSubClassOfAxioms() {
        Set<OWLSubClassOfAxiom> result = new HashSet<OWLSubClassOfAxiom>();
        List<OWLClassExpression> list = getClassExpressionsAsList();
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                result.add(new OWLSubClassOfAxiomImpl(list.get(i), list.get(j)
                        .getObjectComplementOf(), NO_ANNOTATIONS));
            }
        }
        return result;
    }
}