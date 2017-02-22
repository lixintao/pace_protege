package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

/**
 * This is a test of the property type guessing for rdf:Property instances of
 * the kind encountered when attempting to parse an rdfs schema.
 * <p/>
 * The CIDOC schema is a good test case, because it every property has a domain
 * and a range specified; some properties have a range of Literal (and hence are
 * data properties), and there are subclass relations specified for both data
 * and object properties.
 * <p/>
 * There should be no Annotation Properties.
 * <p/>
 * See <a href="http://www.cidoc-crm.org/">The CIDOC Web Site</a> for more
 * details.
 */
@SuppressWarnings("javadoc")
public class GuessRDFSPropertyTypeTestCase {

    private static final String CIDOC_FILE = "/cidoc_crm_v5.0.4_official_release.rdfs.xml";
    private static final String CIDOC_PREFIX = "http://www.cidoc-crm.org/cidoc-crm/";
    private OWLOntology cidocOntology;
    private PrefixOWLOntologyFormat prefixOWLOntologyFormat;

    @Before
    public void setUp()
            throws org.semanticweb.owlapi.model.UnknownOWLOntologyException,
            OWLOntologyCreationException {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntologyLoaderConfiguration config = new OWLOntologyLoaderConfiguration()
                .setStrict(false);
        InputStream in = this.getClass().getResourceAsStream(CIDOC_FILE);
        assertNotNull("can't find CIDOC_FILE", in);
        cidocOntology = manager.loadOntologyFromOntologyDocument(
                new StreamDocumentSource(in), config);
        assertNotNull(cidocOntology);
        OWLOntologyFormat format = manager.getOntologyFormat(cidocOntology);
        assertNotNull(format);
        assertTrue(format.isPrefixOWLOntologyFormat());
        prefixOWLOntologyFormat = format.asPrefixOWLOntologyFormat();
        prefixOWLOntologyFormat.setDefaultPrefix(CIDOC_PREFIX);
    }

    @Test
    public void testObjectProperty() {
        testProperty("P11_had_participant", "E5_Event", "E39_Actor",
                "P12_occurred_in_the_presence_of");
    }

    @Test
    public void testDataProperty() {
        testProperty("P79_beginning_is_qualified_by", "E52_Time-Span",
                "http://www.w3.org/2000/01/rdf-schema#Literal", "P3_has_note");
    }

    public void testProperty(String propertyName, String expectedDomain,
            String expectedRange, String expectedSuperProperty) {
        IRI p11_IRI = prefixOWLOntologyFormat.getIRI(propertyName);
        Set<OWLEntity> had_participant = cidocOntology
                .getEntitiesInSignature(p11_IRI);
        Iterator<OWLEntity> iterator = had_participant.iterator();
        assertTrue("should have found " + propertyName, iterator.hasNext());
        OWLEntity entity = iterator.next();
        assertTrue("EntityType",
                OWLProperty.class.isAssignableFrom(entity.getClass()));
        OWLProperty p11_property = (OWLProperty) entity;
        Set<OWLClassExpression> ranges = p11_property.getRanges(cidocOntology);
        assertEquals("should have 1 range", 1, ranges.size());
        HasIRI range = (HasIRI) ranges.iterator().next();
        IRI rangeIRI = range.getIRI();
        IRI expectedIRI = IRI.create(expectedRange);
        if (!expectedIRI.isAbsolute()) {
            expectedIRI = prefixOWLOntologyFormat.getIRI(expectedRange);
        }
        assertEquals("range", expectedIRI, rangeIRI);
        Set<OWLClassExpression> domains = p11_property
                .getDomains(cidocOntology);
        assertEquals("should have 1 domain", 1, domains.size());
        HasIRI domain = (HasIRI) domains.iterator().next();
        IRI domainIRI = domain.getIRI();
        assertEquals("domain should be E5_Event",
                prefixOWLOntologyFormat.getIRI(expectedDomain), domainIRI);
        Set<OWLPropertyExpression> superProperties = p11_property
                .getSuperProperties(cidocOntology);
        assertEquals("should have 1 super Property", 1, superProperties.size());
        HasIRI superProperty = (HasIRI) superProperties.iterator().next();
        IRI superPropertyIRI = superProperty.getIRI();
        assertEquals("super property ",
                prefixOWLOntologyFormat.getIRI(expectedSuperProperty),
                superPropertyIRI);
    }

    @Test
    public void testObjectPropertyAndDataPropertySetsNonTriviallyDisjoint() {
        Set<OWLObjectProperty> objectProperties = cidocOntology
                .getObjectPropertiesInSignature();
        Set<OWLDataProperty> dataProperties = cidocOntology
                .getDataPropertiesInSignature();
        assertFalse("should have some object Properties",
                objectProperties.isEmpty());
        assertFalse("should have some data Properties",
                dataProperties.isEmpty());
        assertTrue("object properties and data properties should be disjoint",
                Collections.disjoint(objectProperties, dataProperties));
    }

    @Test
    public void testAnnotationPropertyCount() {
        Set<OWLAnnotationProperty> annotationProperties = cidocOntology
                .getAnnotationPropertiesInSignature();
        assertEquals("should only have 2 rdfs annotation properties", 2,
                annotationProperties.size());
    }
}
