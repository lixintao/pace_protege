package org.protege.editor.owl.ui;

import org.protege.editor.core.ui.util.JOptionPaneEx;
import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.ontology.location.PhysicalLocationPanel;
import org.semanticweb.owlapi.model.OWLOntology;

import javax.swing.*;
import java.awt.*;
import java.util.Set;
/*
* Copyright (C) 2007, University of Manchester
*
*
*/

/**
 * Author: drummond<br>
 * http://www.cs.man.ac.uk/~drummond/<br><br>
 * <p/>
 * The University Of Manchester<br>
 * Bio Health Informatics Group<br>
 * Date: May 1, 2009<br><br>
 */
public class SaveConfirmationPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = -3494075622397325662L;
    private PhysicalLocationPanel savedOntologiesPanel;

//    private PhysicalLocationPanel dirtyOntologiesPanel;


    public SaveConfirmationPanel(OWLEditorKit editorKit, Set<OWLOntology> ontologies) {
        setLayout(new BorderLayout(12, 12));
        savedOntologiesPanel = new PhysicalLocationPanel(editorKit, ontologies){
            /**
             * 
             */
            private static final long serialVersionUID = 4961022502290441620L;

            public Dimension getPreferredSize() {
                return new Dimension(600, 200);
            }
        };
        add(new JLabel("Saved the following ontologies:"), BorderLayout.NORTH);
        add(savedOntologiesPanel, BorderLayout.CENTER);

//        final Set<OWLOntology> dirtyOntologies = editorKit.getOWLModelManager().getDirtyOntologies();
//        if (dirtyOntologies.size() > 0){
//            dirtyOntologiesPanel = new PhysicalLocationPanel(editorKit, dirtyOntologies){
//                public Dimension getPreferredSize() {
//                    return new Dimension(600, 200);
//                }
//            };
////            add(new JLabel("The following ontologies are still not saved:"), BorderLayout.SOUTH);
//            add(dirtyOntologiesPanel, BorderLayout.SOUTH);
//        }
    }


    public static void showDialog(OWLEditorKit editorKit, Set<OWLOntology> ontologies) {
        SaveConfirmationPanel panel = new SaveConfirmationPanel(editorKit, ontologies);
        JOptionPaneEx.showConfirmDialog(editorKit.getWorkspace(),
                                        "Saved ontologies",
                                        panel,
                                        JOptionPane.PLAIN_MESSAGE,
                                        JOptionPane.DEFAULT_OPTION,
                                        null);
    }

}
