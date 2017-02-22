package org.protege.editor.owl.ui.editor;

import org.protege.editor.core.ui.util.InputVerificationStatusChangedListener;
import org.protege.editor.core.ui.util.VerifiedInputEditor;
import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.selector.OWLDataPropertySelectorPanel;
import org.semanticweb.owlapi.model.OWLDataProperty;

import javax.swing.*;
import java.util.Collections;
import java.util.HashSet;
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
 * Date: Apr 6, 2009<br><br>
 */
public class OWLDataPropertySetEditor extends AbstractOWLObjectEditor<Set<OWLDataProperty>> implements VerifiedInputEditor {


    private OWLDataPropertySelectorPanel editor;
    
    private Set<InputVerificationStatusChangedListener> listeners = new HashSet<InputVerificationStatusChangedListener>();
    
    private InputVerificationStatusChangedListener inputListener = new InputVerificationStatusChangedListener(){
        public void verifiedStatusChanged(boolean newState) {
            handleVerifyEditorContents();
        }
    };

    public OWLDataPropertySetEditor(OWLEditorKit owlEditorKit) {
        editor = new OWLDataPropertySelectorPanel(owlEditorKit);
        editor.addStatusChangedListener(inputListener);
    }


    public Set<OWLDataProperty> getEditedObject() {
        return editor.getSelectedObjects();
    }

    public boolean setEditedObject(Set<OWLDataProperty> p){
        editor.setSelection(p != null ? p : Collections.EMPTY_SET);
        return true;
    }


    public String getEditorTypeName() {
        return "Set of Data Properties";
    }


    public boolean canEdit(Object object) {
        return checkSet(object, OWLDataProperty.class);
    }


    public JComponent getEditorComponent() {
        return editor;
    }
    
    private void handleVerifyEditorContents() {
    	for (InputVerificationStatusChangedListener l : listeners){
    		l.verifiedStatusChanged(true);
    	}
    }
    
    public void addStatusChangedListener(InputVerificationStatusChangedListener l) {
        listeners.add(l);
        l.verifiedStatusChanged(true);
    }


    public void removeStatusChangedListener(InputVerificationStatusChangedListener l) {
        listeners.remove(l);
    }


    public void dispose() {
        editor.dispose();
    }
}
