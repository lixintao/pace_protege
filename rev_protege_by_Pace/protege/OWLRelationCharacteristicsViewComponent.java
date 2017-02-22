package org.protege.editor.owl.ui.view.relation;


import org.semanticweb.owlapi.model.*;
import org.protege.editor.owl.model.selection.OWLRelationSelectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 29-Jan-2007<br><br>
 */
public class OWLRelationCharacteristicsViewComponent extends AbstractOWLViewComponent {

//    private static final Logger logger = Logger.getLogger(OWLObjectPropertyCharacteristicsViewComponent.class);


    /**
     * 
     */
    private static final long serialVersionUID = -1299595056337566960L;

    private JCheckBox functionalCB;

    private JCheckBox inverseFunctionalCB;

    private JCheckBox transitiveCB;

    private JCheckBox symmetricCB;

    private JCheckBox aSymmetricCB;

    private JCheckBox reflexiveCB;

    private JCheckBox irreflexiveCB;

    private List<JCheckBox> checkBoxes;

    private OWLRelationSelectionListener listener;

    private OWLRelation currRelation;


    public void initialiseOWLView() throws Exception {
        functionalCB = new JCheckBox("Functional");
        inverseFunctionalCB = new JCheckBox("Inverse functional");
        transitiveCB = new JCheckBox("Transitive");
        symmetricCB = new JCheckBox("Symmetric");
        aSymmetricCB = new JCheckBox("Asymmetric");
        reflexiveCB = new JCheckBox("Reflexive");
        irreflexiveCB = new JCheckBox("Irreflexive");

        checkBoxes = new ArrayList<JCheckBox>();
        checkBoxes.add(functionalCB);
        checkBoxes.add(inverseFunctionalCB);
        checkBoxes.add(transitiveCB);
        checkBoxes.add(symmetricCB);
        checkBoxes.add(aSymmetricCB);
        checkBoxes.add(reflexiveCB);
        checkBoxes.add(irreflexiveCB);


        setLayout(new BorderLayout());
        Box box = new Box(BoxLayout.Y_AXIS);
        box.setOpaque(false);
        box.add(functionalCB);
        box.add(Box.createVerticalStrut(7));
        box.add(inverseFunctionalCB);
        box.add(Box.createVerticalStrut(7));
        box.add(transitiveCB);
        box.add(Box.createVerticalStrut(7));
        box.add(symmetricCB);
        box.add(Box.createVerticalStrut(7));
        box.add(aSymmetricCB);
        box.add(Box.createVerticalStrut(7));
        box.add(reflexiveCB);
        box.add(Box.createVerticalStrut(7));
        box.add(irreflexiveCB);
        add(new JScrollPane(box));

        setupSetters();

        listener = new OWLRelationSelectionListener() {
				public void selectionChanged() throws Exception {
                updateView();
            }
		};
        getOWLWorkspace().getOWLSelectionModel().addListener(listener);
    }


    private void setupSetters() {
        functionalCB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if (getRelation() == null)
            		return;
                if (functionalCB.isSelected()) 
					currRelation.setFunctional(true);
                else 
					currRelation.setFunctional(false);
            }
        });
		
		inverseFunctionalCB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if (getRelation() == null)
            		return;
                if (inverseFunctionalCB.isSelected()) 
					currRelation.setInverseFunctional(true);
                else 
					currRelation.setInverseFunctional(false);
            }
        });
		
		transitiveCB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if (getRelation() == null)
            		return;
                if (transitiveCB.isSelected()) 
					currRelation.setTransitive(true);
                else 
					currRelation.setTransitive(false);
            }
        });
		
		symmetricCB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if (getRelation() == null)
            		return;
                if (symmetricCB.isSelected()) 
					currRelation.setSymmetric(true);
                else 
					currRelation.setSymmetric(false);
            }
        });
		
		aSymmetricCB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if (getRelation() == null)
            		return;
                if (aSymmetricCB.isSelected()) 
					currRelation.setAsymmetric(true);
                else 
					currRelation.setAsymmetric(false);
            }
        });
		
		reflexiveCB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if (getRelation() == null)
            		return;
                if (reflexiveCB.isSelected()) 
					currRelation.setReflexive(true);
                else 
					currRelation.setReflexive(false);
            }
        });
		
		irreflexiveCB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if (getRelation() == null)
            		return;
                if (irreflexiveCB.isSelected()) 
					currRelation.setIrreflexive(true);
                else 
					currRelation.setIrreflexive(false);
            }
        });
		
    }


    private OWLRelation getRelation() {
        return currRelation;
    }

    private void setCheckBoxesEnabled(boolean enable) {
        for (JCheckBox cb : checkBoxes) {
            cb.setEnabled(enable);
        }
    }


    private void clearAll() {
        for (JCheckBox cb : checkBoxes) {
            cb.setSelected(false);
        }
    }


    protected void updateView() {
        currRelation = getOWLWorkspace().getOWLSelectionModel().getLastSelectedRelation();
        clearAll();
        setCheckBoxesEnabled(currRelation != null);
        if (currRelation == null) {
        	return;
        }
		OWLOntology ont = getOWLModelManager().getActiveOntology();
        if (currRelation.isFunctional()) {
            functionalCB.setSelected(true);
            if (!getOWLModelManager().isMutable(ont)) {
                functionalCB.setEnabled(false);
            }
        }
        if (currRelation.isInverseFunctional()) {
            inverseFunctionalCB.setSelected(true);
            if (!getOWLModelManager().isMutable(ont)) {
                inverseFunctionalCB.setEnabled(false);
            }
        }
        if (currRelation.isTransitive()) {
            transitiveCB.setSelected(true);
            if (!getOWLModelManager().isMutable(ont)) {
                transitiveCB.setEnabled(false);
            }
        }
        if (currRelation.isSymmetric()) {
            symmetricCB.setSelected(true);
            if (!getOWLModelManager().isMutable(ont)) {
                symmetricCB.setEnabled(false);
            }
        }
        if (currRelation.isAsymmetric()) {
            aSymmetricCB.setSelected(true);
            if (!getOWLModelManager().isMutable(ont)) {
                aSymmetricCB.setEnabled(false);
            }
        }
        if (currRelation.isReflexive()) {
            reflexiveCB.setSelected(true);
            if (!getOWLModelManager().isMutable(ont)) {
                reflexiveCB.setEnabled(false);
            }
        }
        if (currRelation.isIrreflexive()) {
            irreflexiveCB.setSelected(true);
            if (!getOWLModelManager().isMutable(ont)) {
                irreflexiveCB.setEnabled(false);
            }
        }

	}

	
	@Override
	protected void disposeOWLView() {
		getOWLWorkspace().getOWLSelectionModel().removeListener(listener);
	}
}
