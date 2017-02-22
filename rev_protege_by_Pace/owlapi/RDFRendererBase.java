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
package org.coode.owlapi.rdf.renderer;

import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.coode.owlapi.rdf.model.RDFGraph;
import org.coode.owlapi.rdf.model.RDFNode;
import org.coode.owlapi.rdf.model.RDFResourceNode;
import org.coode.owlapi.rdf.model.RDFTranslator;
import org.coode.owlapi.rdf.model.RDFTriple;
import org.semanticweb.owlapi.io.RDFOntologyFormat;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValueVisitorEx;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLRelation;
import org.semanticweb.owlapi.model.OWLEntityVisitor;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.util.AxiomSubjectProvider;
import org.semanticweb.owlapi.util.SWRLVariableExtractor;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 26-Jan-2008
 */
public abstract class RDFRendererBase {

    private static final String ANNOTATION_PROPERTIES_BANNER_TEXT = "Annotation properties";
    private static final String DATATYPES_BANNER_TEXT = "Datatypes";
    private static final String OBJECT_PROPERTIES_BANNER_TEXT = "Object Properties";
    private static final String DATA_PROPERTIES_BANNER_TEXT = "Data properties";
    private static final String CLASSES_BANNER_TEXT = "Classes";
    private static final String INDIVIDUALS_BANNER_TEXT = "Individuals";
    private static final String ANNOTATED_IRIS_BANNER_TEXT = "Annotations";
    /** general axioms */
    public static final String GENERAL_AXIOMS_BANNER_TEXT = "General axioms";
    /** rules banner */
    public static final String RULES_BANNER_TEXT = "Rules";
    protected OWLOntology ontology;
    private RDFGraph graph;
    protected Set<IRI> prettyPrintedTypes;
    private OWLOntologyFormat format;
	public boolean isClassRendering = false;
	public OWLClass currClass;
	
    /**
     * @param ontology
     *        ontology
     */
    public RDFRendererBase(OWLOntology ontology) {
        this(ontology, ontology.getOWLOntologyManager().getOntologyFormat(
                ontology));
    }

    /**
     * @param ontology
     *        ontology
     * @param manager
     *        manager
     */
    @Deprecated
    @SuppressWarnings("unused")
    public RDFRendererBase(OWLOntology ontology, OWLOntologyManager manager) {
        this(ontology, ontology.getOWLOntologyManager().getOntologyFormat(
                ontology));
    }

    @Deprecated
    @SuppressWarnings("unused")
    protected RDFRendererBase(OWLOntology ontology, OWLOntologyManager manager,
            OWLOntologyFormat format) {
        this(ontology, format);
    }

    protected RDFRendererBase(OWLOntology ontology, OWLOntologyFormat format) {
        this.ontology = ontology;
        this.format = format;
    }

    /** @return graph */
    public RDFGraph getGraph() {
        return graph;
    }

    /** @return ontology */
    public OWLOntology getOntology() {
        return ontology;
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // /////
    // ///// Hooks for subclasses
    // /////
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Called before the ontology document is rendered.
     * 
     * @throws IOException
     *         if there was a problem writing to the output stream
     */
    protected abstract void beginDocument() throws IOException;

    /**
     * Called after the ontology document has been rendered.
     * 
     * @throws IOException
     *         if there was a problem writing to the output stream
     */
    protected abstract void endDocument() throws IOException;

    /**
     * Called before an OWLObject such as an entity, anonymous individual, rule
     * etc. is rendered.
     * 
     * @throws IOException
     *         if there was a problem writing to the output stream
     */
    @SuppressWarnings("unused")
    protected void beginObject() throws IOException {}

    /**
     * Called after an OWLObject such as an entity, anonymous individual, rule
     * etc. has been rendered.
     * 
     * @throws IOException
     *         if there was a problem writing to the output stream
     */
    @SuppressWarnings("unused")
    protected void endObject() throws IOException {}

    /**
     * Called before an annotation property is rendered to give subclasses the
     * chance to prefix the rendering with comments etc.
     * 
     * @param prop
     *        The property being rendered
     * @throws IOException
     *         if there was a problem writing to the output stream
     */
    protected abstract void writeAnnotationPropertyComment(
            OWLAnnotationProperty prop) throws IOException;

    /**
     * Called before a data property is rendered to give subclasses the chance
     * to prefix the rendering with comments etc.
     * 
     * @param prop
     *        The property being rendered
     * @throws IOException
     *         if there was a problem writing to the output stream
     */
    protected abstract void writeDataPropertyComment(OWLDataProperty prop)
            throws IOException;

    /**
     * Called before an object property is rendered.
     * 
     * @param prop
     *        The property being rendered
     * @throws IOException
     *         if there was a problem writing to the output stream
     */
    protected abstract void writeObjectPropertyComment(OWLObjectProperty prop)
            throws IOException;

    /**
     * Called before a class is rendered to give subclasses the chance to prefix
     * the rendering with comments etc.
     * 
     * @param cls
     *        The class being rendered
     * @throws IOException
     *         if there was a problem writing to the output stream
     */
    protected abstract void writeClassComment(OWLClass cls) throws IOException;

    /**
     * Called before a datatype is rendered to give subclasses the chance to
     * prefix the rendering with comments etc.
     * 
     * @param datatype
     *        The datatype being rendered
     * @throws IOException
     *         if there was a problem writing to the output stream
     */
    protected abstract void writeDatatypeComment(OWLDatatype datatype)
            throws IOException;

    /**
     * Called before an individual is rendered to give subclasses the chance to
     * prefix the rendering with comments etc.
     * 
     * @param ind
     *        The individual being rendered
     * @throws IOException
     *         if there was a problem writing to the output stream
     */
    protected abstract void writeIndividualComments(OWLNamedIndividual ind)
            throws IOException;

    /**
     * @throws IOException
     *         io error
     */
    public void render() throws IOException {
        beginDocument();
        renderOntologyHeader();
        renderOntologyComponents();
        endDocument();
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // /////
    // ///// Rendering implementation
    // /////
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void renderOntologyComponents() throws IOException {
        renderInOntologySignatureEntities();
        renderAnonymousIndividuals();
        renderUntypedIRIAnnotationAssertions();
        renderGeneralAxioms();
        renderSWRLRules();
    }

    private void renderInOntologySignatureEntities() throws IOException {
        renderAnnotationProperties();
        renderDatatypes();
		renderRelations();
        renderObjectProperties();
        renderDataProperties();
        renderClasses();
        renderNamedIndividuals();
    }

    private void renderAnnotationProperties() throws IOException {
        Set<OWLAnnotationProperty> annotationProperties = ontology
                .getAnnotationPropertiesInSignature();
        renderEntities(annotationProperties, ANNOTATION_PROPERTIES_BANNER_TEXT);
    }

    private void renderNamedIndividuals() throws IOException {
        Set<OWLNamedIndividual> individuals = ontology
                .getIndividualsInSignature();
        renderEntities(individuals, INDIVIDUALS_BANNER_TEXT);
    }

    private void renderClasses() throws IOException {
        Set<OWLClass> clses = ontology.getClassesInSignature();
		isClassRendering = true;
        renderEntities(clses, CLASSES_BANNER_TEXT);
		isClassRendering = false;
    }

    private void renderDataProperties() throws IOException {
        Set<OWLDataProperty> dataProperties = ontology
                .getDataPropertiesInSignature();
        renderEntities(dataProperties, DATA_PROPERTIES_BANNER_TEXT);
    }

    private void renderObjectProperties() throws IOException {
        Set<OWLObjectProperty> objectProperties = ontology
                .getObjectPropertiesInSignature();
        renderEntities(objectProperties, OBJECT_PROPERTIES_BANNER_TEXT);
    }

    private void renderDatatypes() throws IOException {
        Set<OWLDatatype> datatypes = ontology.getDatatypesInSignature();
        renderEntities(datatypes, DATATYPES_BANNER_TEXT);
    }

	private void renderRelations() throws IOException{
		try{
			List<OWLRelation> lisRelation = ontology.getAllRelations();
			if(lisRelation.size() > 0)
				writeBanner("Relations");
			for( OWLRelation rel: lisRelation)
				render(rel);
		}
		catch(Exception e){
			throw new IOException();
		}	
	}
	
    /**
     * Renders a set of entities.
     * 
     * @param entities
     *        The entities. Not null.
     * @param bannerText
     *        The banner text that will prefix the rendering of the entities if
     *        anything is rendered. Not null. May be empty, in which case no
     *        banner will be written.
     * @throws IOException
     *         If there was a problem writing the rendering
     */
    private void renderEntities(Set<? extends OWLEntity> entities,
            String bannerText) throws IOException {
        boolean firstRendering = true;
        for (OWLEntity entity : toSortedSet(entities)) {
            if (createGraph(entity)) {
                if (firstRendering) {
                    firstRendering = false;
                    if (!bannerText.isEmpty()) {
                        writeBanner(bannerText);
                    }
                }
                renderEntity(entity);
            }
        }
    }

    private void renderEntity(OWLEntity entity) throws IOException {
        beginObject();
        writeEntityComment(entity);
		if(isClassRendering)
			currClass = (OWLClass) entity;
        render(new RDFResourceNode(entity.getIRI()));
        renderAnonRoots();
        endObject();
    }

    /**
     * Calls the appropriate hook method to write the comments for an entity.
     * 
     * @param entity
     *        The entity for which comments should be written.
     * @throws IOException
     *         if there was a problem writing the comment
     */
    private void writeEntityComment(OWLEntity entity) throws IOException {
        if (entity.isOWLClass()) {
            writeClassComment(entity.asOWLClass());
        } else if (entity.isOWLDatatype()) {
            writeDatatypeComment(entity.asOWLDatatype());
        } else if (entity.isOWLObjectProperty()) {
            writeObjectPropertyComment(entity.asOWLObjectProperty());
        } else if (entity.isOWLDataProperty()) {
            writeDataPropertyComment(entity.asOWLDataProperty());
        } else if (entity.isOWLAnnotationProperty()) {
            writeAnnotationPropertyComment(entity.asOWLAnnotationProperty());
        } else if (entity.isOWLNamedIndividual()) {
            writeIndividualComments(entity.asOWLNamedIndividual());
        }
    }

    private void renderUntypedIRIAnnotationAssertions() throws IOException {
        Set<IRI> annotatedIRIs = new HashSet<IRI>();
        for (OWLAnnotationAssertionAxiom ax : ontology
                .getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
            OWLAnnotationSubject subject = ax.getSubject();
            if (subject instanceof IRI) {
                IRI iri = (IRI) subject;
                if (!ontology.containsEntityInSignature(iri)) {
                    annotatedIRIs.add(iri);
                }
            }
        }
        if (!annotatedIRIs.isEmpty()) {
            writeBanner(ANNOTATED_IRIS_BANNER_TEXT);
            for (IRI iri : annotatedIRIs) {
                beginObject();
                createGraph(ontology.getAnnotationAssertionAxioms(iri));
                render(new RDFResourceNode(iri));
                renderAnonRoots();
                endObject();
            }
        }
    }

    private void renderAnonymousIndividuals() throws IOException {
        for (OWLAnonymousIndividual anonInd : ontology
                .getReferencedAnonymousIndividuals()) {
            boolean anonRoot = true;
            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            for (OWLAxiom ax : ontology.getReferencingAxioms(anonInd)) {
                if (!(ax instanceof OWLDifferentIndividualsAxiom)) {
                    AxiomSubjectProvider subjectProvider = new AxiomSubjectProvider();
                    OWLObject obj = subjectProvider.getSubject(ax);
                    if (!obj.equals(anonInd)) {
                        anonRoot = false;
                        break;
                    } else {
                        axioms.add(ax);
                    }
                }
            }
            if (anonRoot) {
                createGraph(axioms);
                renderAnonRoots();
            }
        }
    }

    private void renderSWRLRules() throws IOException {
        Set<SWRLRule> ruleAxioms = ontology.getAxioms(AxiomType.SWRL_RULE);
        createGraph(ruleAxioms);
        if (!ruleAxioms.isEmpty()) {
            writeBanner(RULES_BANNER_TEXT);
            SWRLVariableExtractor variableExtractor = new SWRLVariableExtractor();
            for (SWRLRule rule : ruleAxioms) {
                rule.accept(variableExtractor);
            }
            for (SWRLVariable var : variableExtractor.getVariables()) {
                render(new RDFResourceNode(var.getIRI()));
            }
            renderAnonRoots();
        }
    }

    private void renderGeneralAxioms() throws IOException {
        Set<OWLAxiom> generalAxioms = getGeneralAxioms();
        createGraph(generalAxioms);
        Set<RDFResourceNode> rootNodes = graph.getRootAnonymousNodes();
        if (!rootNodes.isEmpty()) {
            writeBanner(GENERAL_AXIOMS_BANNER_TEXT);
            beginObject();
            renderAnonRoots();
            endObject();
        }
    }

    /**
     * Gets the general axioms in the ontology. These are axioms such as
     * DifferentIndividuals, General Class axioms which do not describe or
     * define a named class and so can't be written out as a frame, nary
     * disjoint classes, disjoint object properties, disjoint data properties
     * and HasKey axioms where the class expression is anonymous.
     * 
     * @return A set of axioms that are general axioms (and can't be written out
     *         in a frame-based style).
     */
    private Set<OWLAxiom> getGeneralAxioms() {
        Set<OWLAxiom> generalAxioms = new HashSet<OWLAxiom>();
        generalAxioms.addAll(ontology.getGeneralClassAxioms());
        generalAxioms.addAll(ontology
                .getAxioms(AxiomType.DIFFERENT_INDIVIDUALS));
        for (OWLDisjointClassesAxiom ax : ontology
                .getAxioms(AxiomType.DISJOINT_CLASSES)) {
            if (ax.getClassExpressions().size() > 2) {
                generalAxioms.add(ax);
            }
        }
        for (OWLDisjointObjectPropertiesAxiom ax : ontology
                .getAxioms(AxiomType.DISJOINT_OBJECT_PROPERTIES)) {
            if (ax.getProperties().size() > 2) {
                generalAxioms.add(ax);
            }
        }
        for (OWLDisjointDataPropertiesAxiom ax : ontology
                .getAxioms(AxiomType.DISJOINT_DATA_PROPERTIES)) {
            if (ax.getProperties().size() > 2) {
                generalAxioms.add(ax);
            }
        }
        for (OWLHasKeyAxiom ax : ontology.getAxioms(AxiomType.HAS_KEY)) {
            if (ax.getClassExpression().isAnonymous()) {
                generalAxioms.add(ax);
            }
        }
        return generalAxioms;
    }

    private void renderOntologyHeader() throws IOException {
        graph = new RDFGraph();
        OWLOntologyID ontID = ontology.getOntologyID();
        RDFResourceNode ontologyHeaderNode = createOntologyHeaderNode();
        addVersionIRIToOntologyHeader(ontologyHeaderNode);
        addImportsDeclarationsToOntologyHeader(ontologyHeaderNode);
        addAnnotationsToOntologyHeader(ontologyHeaderNode);
        if (!ontID.isAnonymous() || !graph.isEmpty()) {
            graph.addTriple(new RDFTriple(ontologyHeaderNode,
                    new RDFResourceNode(RDF_TYPE.getIRI()),
                    new RDFResourceNode(OWL_ONTOLOGY.getIRI())));
        }
        if (!graph.isEmpty()) {
            render(ontologyHeaderNode);
        }
    }

    private RDFResourceNode createOntologyHeaderNode() {
        OWLOntologyID ontID = ontology.getOntologyID();
        if (ontID.isAnonymous()) {
            return new RDFResourceNode(System.identityHashCode(ontology));
        } else {
            return new RDFResourceNode(ontID.getOntologyIRI());
        }
    }

    private void addVersionIRIToOntologyHeader(
            RDFResourceNode ontologyHeaderNode) {
        OWLOntologyID ontID = ontology.getOntologyID();
        if (ontID.getVersionIRI() != null) {
            graph.addTriple(new RDFTriple(ontologyHeaderNode,
                    new RDFResourceNode(OWL_VERSION_IRI.getIRI()),
                    new RDFResourceNode(ontID.getVersionIRI())));
        }
    }

    private void addImportsDeclarationsToOntologyHeader(
            RDFResourceNode ontologyHeaderNode) {
		System.out.println("--> addImportsDeclarationsToOnlotogyHeader");
        for (OWLImportsDeclaration decl : ontology.getImportsDeclarations()) {
            graph.addTriple(new RDFTriple(ontologyHeaderNode,
                    new RDFResourceNode(OWL_IMPORTS.getIRI()),
                    new RDFResourceNode(decl.getIRI())));
			System.out.println("--> element");
			System.out.println(decl.getIRI());
        }
    }

    private void addAnnotationsToOntologyHeader(
            RDFResourceNode ontologyHeaderNode) {
        for (OWLAnnotation anno : ontology.getAnnotations()) {
            OWLAnnotationValueVisitorEx<RDFNode> valVisitor = new OWLAnnotationValueVisitorEx<RDFNode>() {

                @Override
                public RDFNode visit(IRI iri) {
                    return new RDFResourceNode(iri);
                }

                @Override
                public RDFNode visit(OWLAnonymousIndividual individual) {
                    return new RDFResourceNode(
                            System.identityHashCode(individual));
                }

                @Override
                public RDFNode visit(OWLLiteral literal) {
                    return RDFTranslator.translateLiteralNode(literal);
                }
            };
            RDFNode node = anno.getValue().accept(valVisitor);
            graph.addTriple(new RDFTriple(ontologyHeaderNode,
                    new RDFResourceNode(anno.getProperty().getIRI()), node));
        }
    }

    private boolean createGraph(OWLEntity entity) {
        final Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        // Don't write out duplicates for punned annotations!
        if (!isIndividualAndClass(entity)) {
            axioms.addAll(entity.getAnnotationAssertionAxioms(ontology));
        }
        axioms.addAll(ontology.getDeclarationAxioms(entity));
        entity.accept(new OWLEntityVisitor() {

            @Override
            public void visit(OWLClass cls) {
                for (OWLAxiom ax : ontology.getAxioms(cls)) {
                    if (ax instanceof OWLDisjointClassesAxiom) {
                        OWLDisjointClassesAxiom disjAx = (OWLDisjointClassesAxiom) ax;
                        if (disjAx.getClassExpressions().size() > 2) {
                            continue;
                        }
                    }
                    axioms.add(ax);
                }
                for (OWLHasKeyAxiom ax : ontology.getAxioms(AxiomType.HAS_KEY)) {
                    if (ax.getClassExpression().equals(cls)) {
                        axioms.add(ax);
                    }
                }
            }

            @Override
            public void visit(OWLDatatype datatype) {
                axioms.addAll(ontology.getDatatypeDefinitions(datatype));
                createGraph(axioms);
            }

            @Override
            public void visit(OWLNamedIndividual individual) {
                for (OWLAxiom ax : ontology.getAxioms(individual)) {
                    if (ax instanceof OWLDifferentIndividualsAxiom) {
                        continue;
                    }
                    axioms.add(ax);
                }
            }

            @Override
            public void visit(OWLDataProperty property) {
                for (OWLAxiom ax : ontology.getAxioms(property)) {
                    if (ax instanceof OWLDisjointDataPropertiesAxiom) {
                        if (((OWLDisjointDataPropertiesAxiom) ax)
                                .getProperties().size() > 2) {
                            continue;
                        }
                    }
                    axioms.add(ax);
                }
            }

            @Override
            public void visit(OWLObjectProperty property) {
                for (OWLAxiom ax : ontology.getAxioms(property)) {
                    if (ax instanceof OWLDisjointObjectPropertiesAxiom) {
                        if (((OWLDisjointObjectPropertiesAxiom) ax)
                                .getProperties().size() > 2) {
                            continue;
                        }
                    }
                    axioms.add(ax);
                }
                for (OWLSubPropertyChainOfAxiom ax : ontology
                        .getAxioms(AxiomType.SUB_PROPERTY_CHAIN_OF)) {
                    if (ax.getSuperProperty().equals(property)) {
                        axioms.add(ax);
                    }
                }
                axioms.addAll(ontology.getAxioms(ontology
                        .getOWLOntologyManager().getOWLDataFactory()
                        .getOWLObjectInverseOf(property)));
            }

            @Override
            public void visit(OWLAnnotationProperty property) {
                axioms.addAll(ontology.getAxioms(property));
            }
        });
        if (axioms.isEmpty() && shouldInsertDeclarations()) {
            if (RDFOntologyFormat.isMissingType(entity, ontology)) {
                axioms.add(ontology.getOWLOntologyManager().getOWLDataFactory()
                        .getOWLDeclarationAxiom(entity));
            }
        }
        createGraph(axioms);
        return !axioms.isEmpty();
    }

    private boolean isIndividualAndClass(OWLEntity entity) {
        return entity.isOWLNamedIndividual()
                && ontology.containsClassInSignature(entity.getIRI());
    }

    protected boolean shouldInsertDeclarations() {
        return !(format instanceof RDFOntologyFormat)
                || ((RDFOntologyFormat) format).isAddMissingTypes();
    }

    protected void createGraph(Set<? extends OWLObject> objects) {
        RDFTranslator translator = new RDFTranslator(
                ontology.getOWLOntologyManager(), ontology,
                shouldInsertDeclarations());
        for (OWLObject obj : objects) {
            obj.accept(translator);
        }
        graph = translator.getGraph();
    }

    protected abstract void writeBanner(String name) throws IOException;

    private static List<OWLEntity>
            toSortedSet(Set<? extends OWLEntity> entities) {
        List<OWLEntity> results = new ArrayList<OWLEntity>(entities);
        Collections.sort(results, OWL_ENTITY_IRI_COMPARATOR);
        return results;
    }

    /**
     * @throws IOException
     *         io error
     */
    public void renderAnonRoots() throws IOException {
        for (RDFResourceNode node : graph.getRootAnonymousNodes()) {
            render(node);
        }
    }

    /**
     * Renders the triples in the current graph into a concrete format.
     * Subclasses of this class decide upon how the triples get rendered.
     * 
     * @param node
     *        The main node to be rendered
     * @throws IOException
     *         If there was a problem rendering the triples.
     */
    public abstract void render(RDFResourceNode node) throws IOException;
	
	public void render(OWLRelation rel) throws IOException{
	}

    protected boolean isObjectList(RDFResourceNode node) {
        for (RDFTriple triple : graph.getTriplesForSubject(node, false)) {
            if (triple.getProperty().getIRI().equals(RDF_TYPE.getIRI())) {
                if (!triple.getObject().isAnonymous()) {
                    if (triple.getObject().getIRI().equals(RDF_LIST.getIRI())) {
                        List<RDFNode> items = new ArrayList<RDFNode>();
                        toJavaList(node, items);
                        for (RDFNode n : items) {
                            if (n.isLiteral()) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    protected void toJavaList(RDFNode n, List<RDFNode> list) {
        RDFNode currentNode = n;
        while (currentNode != null) {
            for (RDFTriple triple : graph.getTriplesForSubject(currentNode,
                    false)) {
                if (triple.getProperty().getIRI().equals(RDF_FIRST.getIRI())) {
                    list.add(triple.getObject());
                }
            }
            for (RDFTriple triple : graph.getTriplesForSubject(currentNode,
                    false)) {
                if (triple.getProperty().getIRI().equals(RDF_REST.getIRI())) {
                    if (!triple.getObject().isAnonymous()) {
                        if (triple.getObject().getIRI()
                                .equals(RDF_NIL.getIRI())) {
                            // End of list
                            currentNode = null;
                        }
                    } else {
                        // Should be another list
                        currentNode = triple.getObject();
                        // toJavaList(triple.getObject(), list);
                    }
                }
            }
        }
    }

    /** Comparator that uses IRI ordering to order entities. */
    private static final class OWLEntityIRIComparator implements
            Comparator<OWLEntity>, Serializable {

        private static final long serialVersionUID = 30406L;

        public OWLEntityIRIComparator() {}

        @Override
        public int compare(OWLEntity o1, OWLEntity o2) {
            return o1.getIRI().compareTo(o2.getIRI());
        }
    }

    private static final OWLEntityIRIComparator OWL_ENTITY_IRI_COMPARATOR = new OWLEntityIRIComparator();
}
