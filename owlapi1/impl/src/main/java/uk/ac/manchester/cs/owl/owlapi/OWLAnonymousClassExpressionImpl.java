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

import java.util.Collections;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnonymousClassExpression;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.util.NNF;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 26-Oct-2006
 */
public abstract class OWLAnonymousClassExpressionImpl extends
        OWLClassExpressionImpl implements OWLAnonymousClassExpression {

    private static final long serialVersionUID = 30406L;

    /** default constructor */
    public OWLAnonymousClassExpressionImpl() {
        super();
    }

    @Override
    public boolean isAnonymous() {
        return true;
    }

    @Override
    public boolean isOWLThing() {
        return false;
    }

    @Override
    public boolean isOWLNothing() {
        return false;
    }

    @Override
    public OWLClassExpression getNNF() {
        NNF nnf = new NNF(new OWLDataFactoryImpl());
        return accept(nnf);
    }

    @Override
    public OWLClassExpression getComplementNNF() {
        NNF nnf = new NNF(new OWLDataFactoryImpl());
        return new OWLObjectComplementOfImpl(this).accept(nnf);
    }

    @Override
    public OWLClassExpression getObjectComplementOf() {
        return new OWLObjectComplementOfImpl(this);
    }

    @Override
    public OWLClass asOWLClass() {
        throw new OWLRuntimeException(
                "Not an OWLClass.  This method should only be called if the isAnonymous method returns false!");
    }

    @Override
    public Set<OWLClassExpression> asConjunctSet() {
        return Collections.singleton((OWLClassExpression) this);
    }

    @Override
    public boolean containsConjunct(OWLClassExpression ce) {
        return ce.equals(this);
    }

    @Override
    public Set<OWLClassExpression> asDisjunctSet() {
        return Collections.singleton((OWLClassExpression) this);
    }
}
