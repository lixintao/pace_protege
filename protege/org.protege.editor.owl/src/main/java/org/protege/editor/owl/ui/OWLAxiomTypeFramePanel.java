package org.protege.editor.owl.ui;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.frame.AxiomListFrame;
import org.protege.editor.owl.ui.framelist.OWLFrameList;
import org.semanticweb.owlapi.model.OWLAxiom;

import javax.swing.*;
import java.awt.*;
import java.util.Set;
/*
 * Copyright (C) 2007, University of Manchester
 *
 *
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 19-Dec-2007<br><br>
 */
public class OWLAxiomTypeFramePanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = -8388108511652522381L;

    private OWLEditorKit owlEditorKit;

    private OWLFrameList<Set<OWLAxiom>> frameList;

    public OWLAxiomTypeFramePanel(OWLEditorKit owlEditorKit) {
        this.owlEditorKit = owlEditorKit;
        createUI();
    }

    private void createUI() {
        setLayout(new BorderLayout());
        AxiomListFrame frame = new AxiomListFrame(owlEditorKit);
        frameList = new OWLFrameList<Set<OWLAxiom>>(owlEditorKit, frame);
        add(new JScrollPane(frameList));
    }

    public void setRoot(Set<OWLAxiom> axioms) {
        frameList.setRootObject(axioms);
    }

    public void dispose() {
        frameList.dispose();
    }
}
