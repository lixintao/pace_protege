package org.protege.editor.owl.ui.view.relation;

import org.protege.editor.core.ui.util.VerifyingOptionPane;

import org.protege.editor.owl.ui.selector.OWLClassSelectorPanel;
import org.protege.editor.core.ui.util.Icons;
import org.protege.editor.core.ui.util.InputVerificationStatusChangedListener;
import org.protege.editor.core.ui.util.VerifiedInputEditor;
import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.model.entity.CustomOWLEntityFactory;
import org.protege.editor.owl.model.entity.OWLEntityCreationException;
import org.protege.editor.owl.model.entity.OWLEntityCreationSet;
import org.protege.editor.owl.ui.clsdescriptioneditor.ExpressionEditorPreferences;
import org.protege.editor.owl.ui.preferences.NewEntitiesPreferencesPanel;
import org.protege.editor.owl.ui.UIHelper;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;
import org.protege.editor.core.ui.util.ComponentFactory;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: 23-Sep-2006<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class newRelatedCreationPanel extends JPanel implements VerifiedInputEditor {
	
    public enum RelatedCreationMode {
		PREVIEW, CREATE;
	}
	
	private OWLClass A;
	
    private static final long serialVersionUID = -2790553738912229896L;

    private OWLEditorKit owlEditorKit;

    private java.util.List<InputVerificationStatusChangedListener> listeners = new ArrayList<InputVerificationStatusChangedListener>();

    private boolean currentlyValid = true;

	private OWLRelationList listRelation;
	
	private OWLClassSelectorPanel panelOWLClass;
	
	private OWLRelationViewComponent panelOWLRelation;
	
    private ChangeListener panelOWLClassListener = new ChangeListener(){
										public void stateChanged(ChangeEvent e){
											manageSelectionChange();
										}
									};
									
	private ListSelectionListener relSelListener = new ListSelectionListener(){

        public void valueChanged(ListSelectionEvent e) {
            manageSelectionChange();
        }
    };
	
	public newRelatedCreationPanel(OWLEditorKit owlEditorKit,OWLClass A) {
        this.owlEditorKit = owlEditorKit;
		this.A = A;
        createUI("Create new relations");
    }

	public void manageSelectionChange(){
		//System.out.println("--> Selection called on owlclasss:");
		//for(OWLClass o: panelOWLClass.getSelectedObjects())
		//	System.out.println(o);
		//System.out.println("--> Selection called on owlrelation:");
		//System.out.println(listRelation.getSelectedValue());
		OWLOntology actOnt = owlEditorKit.getOWLModelManager().getActiveOntology();
		OWLRelation currRel = listRelation.getSelectedValue();
		if(currRel == null){
			setValid(false);
			return;
		}
		if(A == null || A.isOWLThing()){
			setValid(false);
			return;}
		if(panelOWLClass.getSelectedObjects().isEmpty()){
			setValid(false);
			return;
		}	
		for(OWLClass o: panelOWLClass.getSelectedObjects())
			if(actOnt.isRelated(A, currRel.toString(), o) || o.isOWLThing()){
				setValid(false);
				return;
			}
		setValid(true);
	}
	
	
    private void createUI(String message) {
		panelOWLClass = new OWLClassSelectorPanel(owlEditorKit);
		
		setLayout(new BorderLayout());
		listRelation = new OWLRelationList(owlEditorKit);
		listRelation.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		List<OWLRelation> relationListTmp = owlEditorKit.getOWLModelManager().getActiveOntology().getAllRelations();
		listRelation.setListData(relationListTmp.toArray(new OWLRelation[relationListTmp.size()]));
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,ComponentFactory.createScrollPane(listRelation), panelOWLClass);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(150);
		add(splitPane);
		panelOWLClass.addSelectionListener(panelOWLClassListener);
		listRelation.addListSelectionListener(relSelListener);
		setValid(false);
    }

	public void dispose(){
		panelOWLClass.removeSelectionListener(panelOWLClassListener);
	}
    
 /*   public boolean getOWLRelationCreation(RelatedCreationMode mode) throws RuntimeException {
        try {
            }
            else {
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
*/

    public void addStatusChangedListener(InputVerificationStatusChangedListener listener) {
        listeners.add(listener);
        listener.verifiedStatusChanged(currentlyValid);
    }


    public void removeStatusChangedListener(InputVerificationStatusChangedListener listener) {
        listeners.remove(listener);
    }

	public void addRel(){
		OWLOntology actOnt = owlEditorKit.getOWLModelManager().getActiveOntology();
		OWLRelation currRel = listRelation.getSelectedValue();
		for(OWLClass o: panelOWLClass.getSelectedObjects())
			actOnt.addRelated(A, currRel.toString(), o);
	}

    public static boolean showDialog(OWLEditorKit owlEditorKit, String message, OWLClass A) {
	
            newRelatedCreationPanel panel = new newRelatedCreationPanel(owlEditorKit,A);
            int ret = new UIHelper(owlEditorKit).showValidatingDialog("Create a new relation", panel,null);
            if (ret == JOptionPane.OK_OPTION) {
				panel.addRel();
				panel.dispose();
                return true;
            }
			else if(ret == JOptionPane.CANCEL_OPTION){
				panel.dispose();
				return false;
			}
            else {
				panel.dispose();
                return false;
            }
    }



    private void setValid(boolean valid) {
        currentlyValid = valid;
        fireVerificationStatusChanged();
    }

    private void fireVerificationStatusChanged() {
        for (InputVerificationStatusChangedListener l : listeners){
            l.verifiedStatusChanged(currentlyValid);
        }
    }

	/*
	public static int showValidatingConfirmDialog(String title, JComponent component) {
    
		Component parent = owlEditorKit.getWorkspace();
		int messageType = JOptionPane.PLAIN_MESSAGE;
		int optionType  = JOptionPane.OK_CANCEL_OPTION;
		JComponent defaultFocusedComponent = null;
		
        final VerifyingOptionPane optionPane = new VerifyingOptionPane(component, messageType, optionType) {
			private static final long serialVersionUID = 7128847118051849761L;

			public void selectInitialValue() {
				if (defaultFocusedComponent != null) {
					defaultFocusedComponent.requestFocusInWindow();
                }
            }
        };

		final InputVerificationStatusChangedListener verificationListener = new InputVerificationStatusChangedListener() {
            public void verifiedStatusChanged(boolean verified) {
                optionPane.setOKEnabled(verified);
            }
        };
		
        ((VerifiedInputEditor) component).addStatusChangedListener(verificationListener);

        final JDialog dlg = createDialog(parent, title, optionPane, defaultFocusedComponent);
        dlg.setModal(true);
        dlg.setVisible(true);
        return getReturnValue(optionPane);
    
    }*/
	
}
