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

import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 26-Oct-2006
 */
public class OWLObjectExactCardinalityImpl extends
        OWLObjectCardinalityRestrictionImpl implements
        OWLObjectExactCardinality {

    private static final long serialVersionUID = 30406L;

    /**
     * @param property
     *        property
     * @param cardinality
     *        cardinality
     * @param filler
     *        filler
     */
    public OWLObjectExactCardinalityImpl(OWLObjectPropertyExpression property,
            int cardinality, OWLClassExpression filler) {
        super(property, cardinality, filler);
    }

    @Override
    public ClassExpressionType getClassExpressionType() {
        return ClassExpressionType.OBJECT_EXACT_CARDINALITY;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return obj instanceof OWLObjectExactCardinality;
        }
        return false;
    }

    @Override
    public OWLClassExpression asIntersectionOfMinMax() {
        return new OWLObjectIntersectionOfImpl(new HashSet<OWLClassExpression>(
                Arrays.asList(new OWLObjectMinCardinalityImpl(getProperty(),
                        getCardinality(), getFiller()),
                        new OWLObjectMaxCardinalityImpl(getProperty(),
                                getCardinality(), getFiller()))));
    }

    @Override
    public void accept(OWLClassExpressionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLClassExpressionVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }
}
