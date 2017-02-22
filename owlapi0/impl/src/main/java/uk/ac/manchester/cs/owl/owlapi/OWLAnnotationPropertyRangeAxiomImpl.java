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

import java.util.Collection;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management
 *         Group, Date: 17-Jan-2009
 */
public class OWLAnnotationPropertyRangeAxiomImpl extends OWLAxiomImpl implements
        OWLAnnotationPropertyRangeAxiom {

    private static final long serialVersionUID = 30406L;
    private final OWLAnnotationProperty property;
    protected final IRI range;

    /**
     * @param property
     *        property
     * @param range
     *        range
     * @param annotations
     *        annotations on the axiom
     */
    public OWLAnnotationPropertyRangeAxiomImpl(OWLAnnotationProperty property,
            IRI range, Collection<? extends OWLAnnotation> annotations) {
        super(annotations);
        this.property = property;
        this.range = range;
    }

    @Override
    public OWLAnnotationPropertyRangeAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return new OWLAnnotationPropertyRangeAxiomImpl(getProperty(),
                getRange(), NO_ANNOTATIONS);
    }

    @Override
    public OWLAnnotationPropertyRangeAxiom getAnnotatedAxiom(
            Set<OWLAnnotation> annotations) {
        return new OWLAnnotationPropertyRangeAxiomImpl(getProperty(),
                getRange(), mergeAnnos(annotations));
    }

    @Override
    public OWLAnnotationProperty getProperty() {
        return property;
    }

    @Override
    public IRI getRange() {
        return range;
    }

    @Override
    public AxiomType<?> getAxiomType() {
        return AxiomType.ANNOTATION_PROPERTY_RANGE;
    }

    @Override
    public boolean isLogicalAxiom() {
        return false;
    }

    @Override
    public boolean isAnnotationAxiom() {
        return true;
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        OWLAnnotationPropertyRangeAxiom other = (OWLAnnotationPropertyRangeAxiom) object;
        int diff = property.compareTo(other.getProperty());
        if (diff != 0) {
            return diff;
        }
        return range.compareTo(other.getRange());
    }

    @Override
    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void accept(OWLAxiomVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLAxiomVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            // superclass is responsible for null, identity, owlaxiom type and
            // annotations
            if (!(obj instanceof OWLAnnotationPropertyRangeAxiom)) {
                return false;
            }
            OWLAnnotationPropertyRangeAxiom other = (OWLAnnotationPropertyRangeAxiom) obj;
            return property.equals(other.getProperty())
                    && range.equals(other.getRange());
        }
        return false;
    }
}
