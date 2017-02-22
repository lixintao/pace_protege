package org.obolibrary.obo2owl;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatParser;
import org.obolibrary.oboformat.parser.OBOFormatParserException;
import org.obolibrary.oboformat.writer.OBOFormatWriter;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

@SuppressWarnings("javadoc")
public class OboFormatTestBasics {

    @BeforeClass
    public static void setUp() {
        Logger globalLogger = Logger.getLogger("");
        Handler[] handlers = globalLogger.getHandlers();
        for (Handler handler : handlers) {
            globalLogger.removeHandler(handler);
        }
        globalLogger.setLevel(Level.OFF);
        globalLogger.setUseParentHandlers(false);
    }

    protected OBODoc parseOBOURL(String fn) throws IOException,
            OBOFormatParserException {
        OBOFormatParser p = new OBOFormatParser();
        OBODoc obodoc = p.parseURL(fn);
        assertTrue(obodoc.getTermFrames().size() > 0);
        return obodoc;
    }

    protected OBODoc parseOBOFile(String fn) throws IOException,
            OBOFormatParserException {
        return parseOBOFile(fn, false);
    }

    protected OBODoc parseOBOFile(String fn, boolean allowEmptyFrames)
            throws IOException, OBOFormatParserException {
        InputStream inputStream = getInputStream(fn);
        OBOFormatParser p = new OBOFormatParser();
        OBODoc obodoc = p.parse(new BufferedReader(new InputStreamReader(
                inputStream)));
        assertNotNull("The obodoc should not be null", obodoc);
        if (obodoc.getTermFrames().size() == 0 && !allowEmptyFrames) {
            fail("Term frames should not be empty.");
        }
        return obodoc;
    }

    protected OBODoc parseOBOFile(Reader fn, boolean allowEmptyFrames)
            throws IOException, OBOFormatParserException {
        OBOFormatParser p = new OBOFormatParser();
        OBODoc obodoc = p.parse(new BufferedReader(fn));
        assertNotNull("The obodoc should not be null", obodoc);
        if (obodoc.getTermFrames().size() == 0 && !allowEmptyFrames) {
            fail("Term frames should not be empty.");
        }
        return obodoc;
    }

    protected InputStream getInputStream(String fn) {
        InputStream inputStream = getClass().getResourceAsStream(fn);
        if (inputStream == null) {
            inputStream = getClass().getResourceAsStream("obo/" + fn);
        }
        if (inputStream == null) {
            inputStream = ClassLoader.getSystemResourceAsStream(fn);
        }
        if (inputStream == null) {
            inputStream = ClassLoader.getSystemResourceAsStream("obo/" + fn);
        }
        if (inputStream == null) {
            try {
                inputStream = new FileInputStream(new File("obo/" + fn));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return inputStream;
    }

    protected OBODoc parseOBOFile(File file) throws IOException,
            OBOFormatParserException {
        OBOFormatParser p = new OBOFormatParser();
        OBODoc obodoc = p.parse(file.getCanonicalPath());
        return obodoc;
    }

    protected OWLOntology parseOWLFile(String fn)
            throws OWLOntologyCreationException {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        // TODO replace
        OWLOntology ontology = manager
                .loadOntologyFromOntologyDocument(getInputStream(fn));
        return ontology;
    }

    protected OWLOntology convert(OBODoc obodoc)
            throws OWLOntologyCreationException {
        OWLAPIObo2Owl bridge = new OWLAPIObo2Owl(
                OWLManager.createOWLOntologyManager());
        OWLOntology ontology = bridge.convert(obodoc);
        return ontology;
    }

    protected OWLOntology convertOBOFile(String fn) throws Exception {
        OWLOntology convert = convert(parseOBOFile(fn));
        writeOWL(convert);
        return convert;
    }

    protected OBODoc convert(OWLOntology ontology) {
        OWLAPIOwl2Obo bridge = new OWLAPIOwl2Obo(
                OWLManager.createOWLOntologyManager());
        OBODoc doc = bridge.convert(ontology);
        return doc;
    }

    protected String writeOBO(OBODoc obodoc, String fn) throws IOException {
        if (!fn.toLowerCase().endsWith(".obo")) {
            fn += ".obo";
        }
        StringWriter target = new StringWriter();
        OBOFormatWriter oboWriter = new OBOFormatWriter();
        BufferedWriter bw = new BufferedWriter(target);
        oboWriter.write(obodoc, bw);
        bw.flush();
        return target.toString();
    }

    protected StringDocumentTarget writeOWL(OWLOntology ontology)
            throws OWLOntologyStorageException {
        return writeOWL(ontology, new OWLXMLOntologyFormat());
    }

    protected StringDocumentTarget writeOWL(OWLOntology ontology,
            OWLOntologyFormat format) throws OWLOntologyStorageException {
        StringDocumentTarget target = new StringDocumentTarget();
        OWLOntologyManager manager = ontology.getOWLOntologyManager();
        manager.saveOntology(ontology, format, target);
        return target;
    }

    protected static String renderOboToString(OBODoc oboDoc) throws IOException {
        OBOFormatWriter writer = new OBOFormatWriter();
        writer.setCheckStructure(true);
        StringWriter out = new StringWriter();
        BufferedWriter stream = new BufferedWriter(out);
        writer.write(oboDoc, stream);
        stream.close();
        return out.getBuffer().toString();
    }

    protected static OBODoc parseOboToString(String oboString)
            throws IOException, OBOFormatParserException {
        OBOFormatParser p = new OBOFormatParser();
        BufferedReader reader = new BufferedReader(new StringReader(oboString));
        OBODoc parsedOboDoc = p.parse(reader);
        reader.close();
        return parsedOboDoc;
    }

    protected static void renderOBO(OBODoc oboDoc) throws IOException {
        OBOFormatWriter writer = new OBOFormatWriter();
        writer.setCheckStructure(true);
        BufferedWriter stream = new BufferedWriter(new StringWriter());
        writer.write(oboDoc, stream);
        stream.close();
    }

    protected static void renderOWL(OWLOntology owlOntology)
            throws OWLOntologyStorageException {
        OWLOntologyManager manager = owlOntology.getOWLOntologyManager();
        manager.saveOntology(owlOntology, new OWLXMLOntologyFormat(),
                new StringDocumentTarget());
    }

    protected static String renderOWL(OWLOntology owlOntology,
            OWLOntologyFormat ontologyFormat)
            throws OWLOntologyStorageException, IOException {
        OWLOntologyManager manager = owlOntology.getOWLOntologyManager();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        manager.saveOntology(owlOntology, ontologyFormat, out);
        out.close();
        return out.toString();
    }

    protected IRI getIriByLabel(OWLOntology ontology, String label) {
        for (OWLAnnotationAssertionAxiom aa : ontology
                .getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
            OWLAnnotationValue v = aa.getValue();
            OWLAnnotationProperty property = aa.getProperty();
            if (property.isLabel() && v instanceof OWLLiteral) {
                if (label.equals(((OWLLiteral) v).getLiteral())) {
                    OWLAnnotationSubject subject = aa.getSubject();
                    if (subject instanceof IRI) {
                        return (IRI) subject;
                    }
                }
            }
        }
        return null;
    }

    protected String readResource(String resource) throws IOException {
        InputStream inputStream = getInputStream(resource);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append('\n');
        }
        reader.close();
        inputStream.close();
        return sb.toString();
    }
}
