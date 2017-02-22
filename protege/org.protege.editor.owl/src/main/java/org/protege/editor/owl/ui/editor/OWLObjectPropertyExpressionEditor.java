package org.protege.editor.owl.ui.editor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.protege.editor.core.ui.util.InputVerificationStatusChangedListener;
import org.protege.editor.core.ui.util.VerifiedInputEditor;
import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.selector.OWLObjectPropertySelectorPanel;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 16-Feb-2007<br><br>
 */
public class OWLObjectPropertyExpressionEditor extends AbstractOWLObjectEditor<OWLObjectPropertyExpression> implements VerifiedInputEditor {

	private JPanel editor;
    private OWLObjectPropertySelectorPanel namedObjectPropertySelector;
    private JCheckBox inverseCheckBox;

    private Set<InputVerificationStatusChangedListener> listeners = new HashSet<InputVerificationStatusChangedListener>();
    
    private InputVerificationStatusChangedListener inputListener = new InputVerificationStatusChangedListener(){
        public void verifiedStatusChanged(boolean newState) {
            handleVerifyEditorContents();
        }
    };

    public OWLObjectPropertyExpressionEditor(OWLEditorKit owlEditorKit) {
    	editor = new JPanel();
    	editor.setLayout(new BorderLayout());
        namedObjectPropertySelector = new OWLObjectPropertySelectorPanel(owlEditorKit);
        namedObjectPropertySelector.addStatusChangedListener(inputListener);
        editor.add(namedObjectPropertySelector, BorderLayout.CENTER);
        inverseCheckBox = new JCheckBox("Inverse Property");
        inverseCheckBox.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				handleVerifyEditorContents();
			}
		});
        editor.add(inverseCheckBox, BorderLayout.SOUTH);
    }


    public OWLObjectPropertyExpression getEditedObject() {
        OWLObjectProperty p = namedObjectPropertySelector.getSelectedObject();
        return inverseCheckBox.isSelected() ? p.getInverseProperty() : p;
    }

    public boolean setEditedObject(OWLObjectPropertyExpression p) {
    	inverseCheckBox.setSelected(p != null ? p.getSimplified().isAnonymous() : false);
        namedObjectPropertySelector.setSelection(p != null ? p.getNamedProperty() : null);
        return true;
    }


    public String getEditorTypeName() {
        return "Object property";
    }


    public boolean canEdit(Object object) {
        return object instanceof OWLObjectPropertyExpression;
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
        namedObjectPropertySelector.dispose();
    }



}
