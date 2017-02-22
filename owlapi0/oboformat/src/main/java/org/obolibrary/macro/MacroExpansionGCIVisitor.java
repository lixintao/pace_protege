package org.obolibrary.macro;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.coode.owlapi.manchesterowlsyntax.OntologyAxiomPair;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNamedObject;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

/** macro expansion gci visitor */
public class MacroExpansionGCIVisitor {

    protected static final Logger log = Logger
            .getLogger(MacroExpansionGCIVisitor.class.getName());
    private OWLOntology inputOntology;
    private OWLOntologyManager outputManager;
    private OWLOntology outputOntology;
    protected ManchesterSyntaxTool manchesterSyntaxTool;
    private GCIVisitor visitor;

    /**
     * @param inputOntology
     *        inputOntology
     * @param outputManager
     *        outputManager
     */
    public MacroExpansionGCIVisitor(OWLOntology inputOntology,
            OWLOntologyManager outputManager) {
        super();
        this.inputOntology = inputOntology;
        visitor = new GCIVisitor(inputOntology);
        manchesterSyntaxTool = new ManchesterSyntaxTool(inputOntology);
        this.outputManager = outputManager;
        try {
            outputOntology = outputManager.createOntology(inputOntology
                    .getOntologyID());
        } catch (Exception ex) {
            log.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    protected void output(OWLAxiom axiom) {
        if (axiom == null) {
            log.log(Level.SEVERE, "no axiom");
            return;
        }
        // System.out.println("adding:"+axiom);
        AddAxiom addAx = new AddAxiom(outputOntology, axiom);
        try {
            outputManager.applyChange(addAx);
        } catch (Exception e) {
            log.log(Level.SEVERE, "COULD NOT TRANSLATE AXIOM", e);
        }
    }

    /** @return ontology for gci */
    public OWLOntology createGCIOntology() {
        for (OWLAxiom ax : inputOntology.getAxioms()) {
            if (ax instanceof OWLSubClassOfAxiom) {
                visitor.visit((OWLSubClassOfAxiom) ax);
            } else if (ax instanceof OWLEquivalentClassesAxiom) {
                visitor.visit((OWLEquivalentClassesAxiom) ax);
            } else if (ax instanceof OWLClassAssertionAxiom) {
                visitor.visit((OWLClassAssertionAxiom) ax);
            } else if (ax instanceof OWLAnnotationAssertionAxiom) {
                expand((OWLAnnotationAssertionAxiom) ax);
            }
        }
        return outputOntology;
    }

    private void expand(OWLAnnotationAssertionAxiom ax) {
        OWLAnnotationProperty prop = ax.getProperty();
        String expandTo = visitor.expandAssertionToMap.get(prop.getIRI());
        if (expandTo != null) {
            if (log.isLoggable(Level.FINE)) {
                log.log(Level.SEVERE, "Template to Expand" + expandTo);
            }
            expandTo = expandTo.replaceAll("\\?X",
                    manchesterSyntaxTool.getId((IRI) ax.getSubject()));
            expandTo = expandTo.replaceAll("\\?Y",
                    manchesterSyntaxTool.getId((IRI) ax.getValue()));
            if (log.isLoggable(Level.FINE)) {
                log.log(Level.SEVERE, "Expanding " + expandTo);
            }
            try {
                Set<OntologyAxiomPair> setAxp = manchesterSyntaxTool
                        .parseManchesterExpressionFrames(expandTo);
                for (OntologyAxiomPair axp : setAxp) {
                    output(axp.getAxiom());
                }
            } catch (Exception ex) {
                log.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }

    private class GCIVisitor extends AbstractMacroExpansionVisitor {

        GCIVisitor(OWLOntology inputOntology) {
            super(inputOntology, MacroExpansionGCIVisitor.log);
        }

        @Override
        protected OWLClassExpression expandOWLObjSomeVal(
                OWLClassExpression filler, OWLObjectPropertyExpression p) {
            OWLClassExpression gciRHS = expandObject(filler, p);
            if (gciRHS != null) {
                OWLClassExpression gciLHS = dataFactory
                        .getOWLObjectSomeValuesFrom(p, filler);
                OWLEquivalentClassesAxiom ax = dataFactory
                        .getOWLEquivalentClassesAxiom(gciLHS, gciRHS);
                output(ax);
            }
            return gciRHS;
        }

        @Override
        protected OWLClassExpression expandOWLObjHasVal(OWLObjectHasValue desc,
                OWLIndividual filler, OWLObjectPropertyExpression p) {
            OWLClassExpression gciRHS = expandObject(filler, p);
            if (gciRHS != null) {
                OWLClassExpression gciLHS = dataFactory.getOWLObjectHasValue(p,
                        filler);
                OWLEquivalentClassesAxiom ax = dataFactory
                        .getOWLEquivalentClassesAxiom(gciLHS, gciRHS);
                output(ax);
            }
            return gciRHS;
        }

        private OWLClassExpression expandObject(Object filler,
                OWLObjectPropertyExpression p) {
            OWLClassExpression result = null;
            IRI iri = ((OWLObjectProperty) p).getIRI();
            IRI templateVal = null;
            if (expandExpressionMap.containsKey(iri)) {
                if (filler instanceof OWLObjectOneOf) {
                    Set<OWLIndividual> inds = ((OWLObjectOneOf) filler)
                            .getIndividuals();
                    if (inds.size() == 1) {
                        OWLIndividual ind = inds.iterator().next();
                        if (ind instanceof OWLNamedIndividual) {
                            templateVal = ((OWLNamedObject) ind).getIRI();
                        }
                    }
                }
                if (filler instanceof OWLNamedObject) {
                    templateVal = ((OWLNamedObject) filler).getIRI();
                }
                if (templateVal != null) {
                    String tStr = expandExpressionMap.get(iri);
                    String exStr = tStr.replaceAll("\\?Y",
                            manchesterSyntaxTool.getId(templateVal));
                    try {
                        result = manchesterSyntaxTool
                                .parseManchesterExpression(exStr);
                    } catch (ParserException e) {
                        log.log(Level.SEVERE, e.getMessage(), e);
                    }
                }
            }
            return result;
        }
    }

    /** Call this method to clear internal references. */
    public void dispose() {
        manchesterSyntaxTool.dispose();
    }
}
