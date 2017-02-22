package org.semanticweb.owlapi.api.test.swrl;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.coode.owlapi.turtle.TurtleOntologyFormat;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 04/04/2014
 */
@SuppressWarnings("javadoc")
public class SWRLAtomOrderingRoundTripTestCase {

    private Set<SWRLAtom> body;
    private Set<SWRLAtom> head;
    private SWRLRule rule;

    @Before
    public void setUp() {
        body = new LinkedHashSet<SWRLAtom>();
        head = new LinkedHashSet<SWRLAtom>();

        OWLDataFactory dataFactory = new OWLDataFactoryImpl();
        PrefixManager pm = new DefaultPrefixManager("http://stuff.com/A/");
        OWLClass clsA = Class("A", pm);
        OWLClass clsB = Class("B", pm);
        OWLClass clsC = Class("C", pm);
        OWLClass clsD = Class("D", pm);
        OWLClass clsE = Class("E", pm);
        SWRLVariable varA = dataFactory.getSWRLVariable(IRI.create("http://other.com/A/VarA"));
        SWRLVariable varB = dataFactory.getSWRLVariable(IRI.create("http://other.com/A/VarA"));
        SWRLVariable varC = dataFactory.getSWRLVariable(IRI.create("http://other.com/A/VarA"));
        body.add(dataFactory.getSWRLClassAtom(clsC, varA));
        body.add(dataFactory.getSWRLClassAtom(clsB, varB));
        body.add(dataFactory.getSWRLClassAtom(clsA, varC));
        head.add(dataFactory.getSWRLClassAtom(clsE, varA));
        head.add(dataFactory.getSWRLClassAtom(clsD, varA));
        rule = dataFactory.getSWRLRule(body, head);
    }

    @Test
    public void shouldPreserveOrderingInRDFXMLRoundTrip() throws OWLOntologyCreationException, OWLOntologyStorageException {
        roundTrip(new RDFXMLOntologyFormat());
    }

    private void roundTrip(OWLOntologyFormat ontologyFormat) throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        OWLOntology ont = man.createOntology();
        man.addAxiom(ont, rule);

        StringDocumentTarget documentTarget = new StringDocumentTarget();
        man.saveOntology(ont, ontologyFormat, documentTarget);

        OWLOntologyManager man2 = OWLManager.createOWLOntologyManager();
        OWLOntology ont2 = man2.loadOntologyFromOntologyDocument(new StringDocumentSource(documentTarget.toString()));

        Set<SWRLRule> rules = ont2.getAxioms(AxiomType.SWRL_RULE);
        assertThat(rules.size(), is(1));

        SWRLRule parsedRule = rules.iterator().next();
        assertThat(parsedRule, is(equalTo(rule)));

        List<SWRLAtom> originalBody = new ArrayList<SWRLAtom>(body);
        List<SWRLAtom> parsedBody = new ArrayList<SWRLAtom>(parsedRule.getBody());
        assertThat(parsedBody, is(equalTo(originalBody)));

        List<SWRLAtom> originalHead = new ArrayList<SWRLAtom>(head);
        List<SWRLAtom> parsedHead = new ArrayList<SWRLAtom>(parsedRule.getHead());
        assertThat(originalHead, is(equalTo(parsedHead)));
    }

    @Test
    public void shouldPreserveOrderingInTurtleRoundTrip() throws OWLOntologyCreationException,
            OWLOntologyStorageException {
        roundTrip(new TurtleOntologyFormat());
    }

    @Test
    public void shouldPreserveOrderingInManchesterSyntaxRoundTrip() throws OWLOntologyCreationException,
            OWLOntologyStorageException {
        roundTrip(new ManchesterOWLSyntaxOntologyFormat());
    }

    @Test
    public void shouldPreserveOrderingInOWLXMLRoundTrip() throws OWLOntologyCreationException,
            OWLOntologyStorageException {
        roundTrip(new OWLXMLOntologyFormat());
    }
}
