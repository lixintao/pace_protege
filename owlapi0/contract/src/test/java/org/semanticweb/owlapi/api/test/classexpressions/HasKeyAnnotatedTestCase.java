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
package org.semanticweb.owlapi.api.test.classexpressions;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.api.test.baseclasses.AbstractAxiomsRoundTrippingTestCase;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management
 *         Group, Date: 28-May-2009
 */
public class HasKeyAnnotatedTestCase extends
        AbstractAxiomsRoundTrippingTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        OWLAnnotationProperty ap = AnnotationProperty(IRI("http://annotation.com/annos#prop"));
        OWLLiteral val = Literal("Test", "");
        OWLAnnotation anno = Annotation(ap, val);
        Set<OWLAnnotation> annos = new HashSet<OWLAnnotation>();
        annos.add(anno);
        OWLClassExpression ce = Class(getIRI("A"));
        OWLObjectProperty p1 = ObjectProperty(getIRI("p1"));
        OWLObjectProperty p2 = ObjectProperty(getIRI("p2"));
        OWLObjectProperty p3 = ObjectProperty(getIRI("p3"));
        OWLHasKeyAxiom ax = HasKey(annos, ce, p1, p2, p3);
        Set<OWLAxiom> axs = new HashSet<OWLAxiom>();
        axs.add(ax);
        axs.add(Declaration(ap));
        axs.add(Declaration(p1));
        axs.add(Declaration(p2));
        axs.add(Declaration(p3));
        return axs;
    }
}
