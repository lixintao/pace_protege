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
package org.semanticweb.owlapi.vocab;

import static org.semanticweb.owlapi.model.EntityType.*;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.*;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 03-Oct-2007
 */
public enum SKOSVocabulary implements HasShortForm, HasIRI, HasPrefixedName {
    //@formatter:off
    /** BROADMATCH        */  BROADMATCH          ("broadMatch",          OBJECT_PROPERTY), 
    /** BROADER           */  BROADER             ("broader",             OBJECT_PROPERTY), 
    /** BROADERTRANSITIVE */  BROADERTRANSITIVE   ("broaderTransitive",   OBJECT_PROPERTY), 
    /** CLOSEMATCH        */  CLOSEMATCH          ("closeMatch",          OBJECT_PROPERTY), 
    /** EXACTMATCH        */  EXACTMATCH          ("exactMatch",          OBJECT_PROPERTY), 
    /** HASTOPCONCEPT     */  HASTOPCONCEPT       ("hasTopConcept",       OBJECT_PROPERTY), 
    /** INSCHEME          */  INSCHEME            ("inScheme",            OBJECT_PROPERTY), 
    /** MAPPINGRELATION   */  MAPPINGRELATION     ("mappingRelation",     OBJECT_PROPERTY), 
    /** MEMBER            */  MEMBER              ("member",              OBJECT_PROPERTY), 
    /** MEMBERLIST        */  MEMBERLIST          ("memberList",          OBJECT_PROPERTY), 
    /** NARROWMATCH       */  NARROWMATCH         ("narrowMatch",         OBJECT_PROPERTY), 
    /** NARROWER          */  NARROWER            ("narrower",            OBJECT_PROPERTY), 
    /** NARROWTRANSITIVE  */  NARROWTRANSITIVE    ("narrowTransitive",    OBJECT_PROPERTY), 
    /** RELATED           */  RELATED             ("related",             OBJECT_PROPERTY), 
    /** RELATEDMATCH      */  RELATEDMATCH        ("relatedMatch",        OBJECT_PROPERTY), 
    /** SEMANTICRELATION  */  SEMANTICRELATION    ("semanticRelation",    OBJECT_PROPERTY), 
    /** TOPCONCEPTOF      */  TOPCONCEPTOF        ("topConceptOf",        OBJECT_PROPERTY), 
    /** COLLECTION        */  COLLECTION          ("Collection",          CLASS), 
    /** CONCEPT           */  CONCEPT             ("Concept",             CLASS), 
    /** CONCEPTSCHEME     */  CONCEPTSCHEME       ("ConceptScheme",       CLASS), 
    /** ORDEREDCOLLECTION */  ORDEREDCOLLECTION   ("OrderedCollection",   CLASS), 
    /** TOPCONCEPT        */  TOPCONCEPT          ("TopConcept",          CLASS), 
    /** ALTLABEL          */  ALTLABEL            ("altLabel",            ANNOTATION_PROPERTY), 
    /** CHANGENOTE        */  CHANGENOTE          ("changeNote",          ANNOTATION_PROPERTY), 
    /** DEFINITION        */  DEFINITION          ("definition",          ANNOTATION_PROPERTY), 
    /** EDITORIALNOTE     */  EDITORIALNOTE       ("editorialNote",       ANNOTATION_PROPERTY), 
    /** EXAMPLE           */  EXAMPLE             ("example",             ANNOTATION_PROPERTY), 
    /** HIDDENLABEL       */  HIDDENLABEL         ("hiddenLabel",         ANNOTATION_PROPERTY), 
    /** HISTORYNOTE       */  HISTORYNOTE         ("historyNote",         ANNOTATION_PROPERTY), 
    /** NOTE              */  NOTE                ("note",                ANNOTATION_PROPERTY), 
    /** PREFLABEL         */  PREFLABEL           ("prefLabel",           ANNOTATION_PROPERTY), 
    /** SCOPENOTE         */  SCOPENOTE           ("scopeNote",           ANNOTATION_PROPERTY),
    /** @deprecated No longer used */
    @Deprecated
    DOCUMENT("Document", EntityType.CLASS),
    /** @deprecated No longer used */
    @Deprecated
    IMAGE("Image", EntityType.CLASS),
    /** @deprecated No longer used */
    @Deprecated
    COLLECTABLEPROPERTY("CollectableProperty", EntityType.ANNOTATION_PROPERTY),
    /** @deprecated No longer used */
    @Deprecated
    RESOURCE("Resource", EntityType.CLASS),
    /** @deprecated No longer used */
    @Deprecated
    COMMENT("comment", EntityType.ANNOTATION_PROPERTY);
    //@formatter:on
    /** all IRIs */
    public static final Set<IRI> ALL_IRIS;
    static {
        ALL_IRIS = new HashSet<IRI>();
        for (SKOSVocabulary v : SKOSVocabulary.values()) {
            ALL_IRIS.add(v.getIRI());
        }
    }
    private final String localName;
    private final IRI iri;
    private final EntityType<?> entityType;
    private final String prefixedName;

    SKOSVocabulary(String localname, EntityType<?> entityType) {
        localName = localname;
        this.prefixedName = Namespaces.SKOS.getPrefixName() + ":" + localname;
        this.entityType = entityType;
        iri = IRI.create(Namespaces.SKOS.toString(), localname);
    }

    /** @return entity type */
    public EntityType<?> getEntityType() {
        return entityType;
    }

    /**
     * @return local name
     */
    public String getLocalName() {
        return localName;
    }

    /** @return iri */
    @Override
    public IRI getIRI() {
        return iri;
    }

    /**
     * @return uri
     * @deprecated use getIRI()
     */
    @Deprecated
    public URI getURI() {
        return iri.toURI();
    }

    /**
     * @param dataFactory
     *        data factory to use
     * @return set of SKOS annotation properties
     */
    public static Set<OWLAnnotationProperty> getAnnotationProperties(
            OWLDataFactory dataFactory) {
        Set<OWLAnnotationProperty> result = new HashSet<OWLAnnotationProperty>();
        for (SKOSVocabulary v : values()) {
            if (v.entityType.equals(EntityType.ANNOTATION_PROPERTY)) {
                result.add(dataFactory.getOWLAnnotationProperty(v.iri));
            }
        }
        return result;
    }

    /**
     * @param dataFactory
     *        data factory to use
     * @return set of SKOS object properties
     */
    public static Set<OWLObjectProperty> getObjectProperties(
            OWLDataFactory dataFactory) {
        Set<OWLObjectProperty> result = new HashSet<OWLObjectProperty>();
        for (SKOSVocabulary v : values()) {
            if (v.entityType.equals(EntityType.OBJECT_PROPERTY)) {
                result.add(dataFactory.getOWLObjectProperty(v.iri));
            }
        }
        return result;
    }

    /**
     * @param dataFactory
     *        data factory to use
     * @return set of SKOS data properties
     */
    public static Set<OWLDataProperty> getDataProperties(
            OWLDataFactory dataFactory) {
        Set<OWLDataProperty> result = new HashSet<OWLDataProperty>();
        for (SKOSVocabulary v : values()) {
            if (v.entityType.equals(EntityType.DATA_PROPERTY)) {
                result.add(dataFactory.getOWLDataProperty(v.iri));
            }
        }
        return result;
    }

    /**
     * @param dataFactory
     *        data factory to use
     * @return set of SKOS classes
     */
    public static Set<OWLClass> getClasses(OWLDataFactory dataFactory) {
        Set<OWLClass> result = new HashSet<OWLClass>();
        for (SKOSVocabulary v : values()) {
            if (v.entityType.equals(EntityType.CLASS)) {
                result.add(dataFactory.getOWLClass(v.iri));
            }
        }
        return result;
    }

    /**
     * Gets the short form for this vocabulary element. Short forms are the
     * local name e.g. "broader" for {@link #BROADER} etc.
     * 
     * @return The short form. Not {@code null}.
     */
    @Override
    public String getShortForm() {
        return localName;
    }

    @Override
    public String getPrefixedName() {
        return prefixedName;
    }
}
