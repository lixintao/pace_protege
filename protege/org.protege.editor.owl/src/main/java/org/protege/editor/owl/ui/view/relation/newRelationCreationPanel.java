package org.protege.editor.owl.ui.view.relation;

import org.protege.editor.core.ui.util.AugmentedJTextField;
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



import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
public class newRelationCreationPanel extends JPanel implements VerifiedInputEditor {
	
    public enum RelationCreationMode {
		PREVIEW, CREATE;
	}
	
    private static final long serialVersionUID = -2790553738912229896L;

    public static final int FIELD_WIDTH = 40;

    private OWLEditorKit owlEditorKit;

    private JTextField userSuppliedNameField;

    private java.util.List<InputVerificationStatusChangedListener> listeners = new ArrayList<InputVerificationStatusChangedListener>();

    private boolean currentlyValid = true;

    private final AugmentedJTextField entityIRIField = new AugmentedJTextField(FIELD_WIDTH, "IRI (auto-generated)");

    private final JTextArea messageArea = new JTextArea(1, FIELD_WIDTH);


    public newRelationCreationPanel(OWLEditorKit owlEditorKit, String message) {
        this.owlEditorKit = owlEditorKit;
        createUI(message);
    }


    public void setEnabled(boolean b) {
        userSuppliedNameField.setEnabled(b);
        super.setEnabled(b);
    }


    public void setName(String name) {
        userSuppliedNameField.setText(name);
    }


    private void createUI(String message) {
        setLayout(new BorderLayout());
        JPanel holder = new JPanel(new GridBagLayout());
        add(holder);
        Insets insets = new Insets(0, 0, 2, 2);

        int rowIndex = 0;

        holder.add(new JLabel("Name:"), new GridBagConstraints(0, rowIndex, 1, 1, 0.0, 0.0, GridBagConstraints.BASELINE_TRAILING, GridBagConstraints.NONE, insets, 0, 0));


        userSuppliedNameField = new AugmentedJTextField(30, "Short name or full IRI or Prefix-Name");
        userSuppliedNameField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            public void removeUpdate(DocumentEvent e) {
                update();
            }

            public void changedUpdate(DocumentEvent e) {
            }
        });


        holder.add(userSuppliedNameField, new GridBagConstraints(1, rowIndex, 1, 1, 100.0, 0.0, GridBagConstraints.BASELINE_LEADING, GridBagConstraints.HORIZONTAL, insets, 0, 0));


        rowIndex++;
        holder.add(new JSeparator(), new GridBagConstraints(0, rowIndex, 2, 1, 100.0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 2, 10, 2), 0, 0));

        rowIndex++;
        holder.add(new JLabel("IRI:"), new GridBagConstraints(0, rowIndex, 1, 1, 0.0, 0.0, GridBagConstraints.BASELINE_TRAILING, GridBagConstraints.NONE, insets, 0, 0));
        entityIRIField.setForeground(Color.GRAY);
        entityIRIField.setEditable(false);
        holder.add(entityIRIField, new GridBagConstraints(1, rowIndex, 1, 1, 100.0, 0.0, GridBagConstraints.BASELINE_TRAILING, GridBagConstraints.HORIZONTAL, insets, 0, 0));


        /*rowIndex++;
        holder.add(new JButton(new AbstractAction("New entity options...") {
            public void actionPerformed(ActionEvent e) {
                showEntityCreationPreferences();
            }
        }), new GridBagConstraints(1, rowIndex, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(7, 0, 0, 0), 0, 0));
	*/

        rowIndex++;
        holder.add(new JSeparator(), new GridBagConstraints(0, rowIndex, 2, 1, 100.0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 2, 5, 2), 0, 0));

        rowIndex++;
        messageArea.setBackground(null);
        messageArea.setBorder(null);
        messageArea.setEditable(false);
        messageArea.setWrapStyleWord(true);
        messageArea.setLineWrap(true);
        messageArea.setFont(messageArea.getFont().deriveFont(12.0f));
        messageArea.setForeground(Color.RED);
        holder.add(messageArea, new GridBagConstraints(0, rowIndex, 2, 1, 0, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 2, 0, 2), 0, 0));

        
        update();
    }

/*
    private void showEntityCreationPreferences() {
        try {
            NewEntitiesPreferencesPanel panel = new NewEntitiesPreferencesPanel();
            panel.setup("Entity creation preferences", owlEditorKit);

            panel.initialise();

            int ret = JOptionPane.showConfirmDialog(this, panel, "Entity Creation Preferences", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (ret == JOptionPane.OK_OPTION) {
                panel.applyChanges();
                update();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
*/
    /**
     * Determines if the entity name represents an IRI rather than a short name.
     * @return <code>true</code> if the entity name represents an IRI rather than a short name, otherwise
     * <code>false</code>.
     */
    public boolean isEntityIRI() {
        String entityName = getEntityName();
        for (Namespaces ns : Namespaces.values()) {
            if (entityName.startsWith(ns.name().toLowerCase() + ":")) {
                return true;
            }
        }
        OWLModelManager owlModelManager = owlEditorKit.getOWLModelManager();
        OWLOntologyManager owlOntologyManager = owlModelManager.getOWLOntologyManager();
        for(OWLOntology ont : owlModelManager.getActiveOntologies()) {
            OWLOntologyFormat format = owlOntologyManager.getOntologyFormat(ont);
            if(format.isPrefixOWLOntologyFormat()) {
                PrefixOWLOntologyFormat prefixFormat = format.asPrefixOWLOntologyFormat();
                for(String prefix : prefixFormat.getPrefixNames()) {
                    if(entityName.startsWith(prefix)) {
                        return true;
                    }
                }
            }
        }
        try {
            URI uri = new URI(entityName);
            return uri.isAbsolute() && uri.getPath() != null;
        }
        catch (URISyntaxException e) {
            return false;
        }
    }

    public String getEntityName() {
        return userSuppliedNameField.getText().trim();
    }

    
    public boolean getOWLRelationCreation(RelationCreationMode mode) throws RuntimeException {
        try {
            if (isEntityIRI()) {
				String relationName = getRawIRI().toString();
				OWLModelManager owlModelManager = owlEditorKit.getOWLModelManager();
				OWLOntologyManager owlOntologyManager = owlModelManager.getOWLOntologyManager();
				for(OWLOntology ont : owlModelManager.getActiveOntologies()) {
					if(ont.doesContainRelation(relationName))
						return false;
				}
				if(mode == RelationCreationMode.CREATE){
					OWLOntology ont = owlModelManager.getActiveOntology();
					ont.addRelation(relationName);
				}
				return true;
            }
            else {
            	String relationName = entityIRIField.getText().trim();
				OWLModelManager owlModelManager = owlEditorKit.getOWLModelManager();
				OWLOntologyManager owlOntologyManager = owlModelManager.getOWLOntologyManager();
				for(OWLOntology ont : owlModelManager.getActiveOntologies()) {
					if(ont.doesContainRelation(relationName))
						return false;
				}
				if(mode == RelationCreationMode.CREATE){
					OWLOntology ont = owlModelManager.getActiveOntology();
					ont.addRelation(relationName);
				}
				return true; 				
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void addStatusChangedListener(InputVerificationStatusChangedListener listener) {
        listeners.add(listener);
        listener.verifiedStatusChanged(currentlyValid);
    }


    public void removeStatusChangedListener(InputVerificationStatusChangedListener listener) {
        listeners.remove(listener);
    }


    public static boolean showDialog(OWLEditorKit owlEditorKit, String message) {

            newRelationCreationPanel panel = new newRelationCreationPanel(owlEditorKit, message);
            int ret = new UIHelper(owlEditorKit).showValidatingDialog("Create a new relation", panel, panel.userSuppliedNameField);
            if (ret == JOptionPane.OK_OPTION) {
                return panel.getOWLRelationCreation(RelationCreationMode.CREATE);
            }
            else {
                return false;
            }
    }


    public IRI getBaseIRI() {
        return null; // let this be managed by the EntityFactory for now - we could add a selector later
    }


    private void update() {
        try {

            entityIRIField.setText("");
            messageArea.setText("");
			
			if(isEntityIRI())
				entityIRIField.setText(getRawIRI().toString());
			else{
				String iriString  = owlEditorKit.getOWLModelManager().getActiveOntology().getOntologyID().getOntologyIRI().toString();
				if(!getEntityName().isEmpty())
					iriString = iriString + "#" + getEntityName();
				entityIRIField.setText(iriString);
			}
            if (userSuppliedNameField.getText().trim().isEmpty()) {
                setValid(false);
                return;
            }
            boolean canCreateRelation = getOWLRelationCreation(RelationCreationMode.PREVIEW);
            if(canCreateRelation == false) {
                setValid(false);
                return;
            }
            setValid(true);
        }
        catch (RuntimeException e) {
            setValid(false);
            messageArea.setText("Some error occurred");
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



    private IRI getRawIRI() {
        String text = getEntityName();
        OWLOntology activeOntology = owlEditorKit.getModelManager().getActiveOntology();
        OWLOntologyManager manager = owlEditorKit.getModelManager().getOWLOntologyManager();
        OWLOntologyFormat format = manager.getOntologyFormat(activeOntology);
        for (Namespaces ns : Namespaces.values()) {
            if (text.startsWith(ns.name().toLowerCase() + ":")) {
                return IRI.create(ns.toString() + text.substring(ns.name().length() + 1));
            }
        }
        int colonIndex = text.indexOf(':');
        if (colonIndex >= 0 && format.isPrefixOWLOntologyFormat()) {
            PrefixOWLOntologyFormat prefixes = format.asPrefixOWLOntologyFormat();
            String prefixName = text.substring(0, colonIndex + 1);
            String prefix = prefixes.getPrefix(prefixName);
            if (prefix != null) {
                return IRI.create(prefix + text.substring(colonIndex + 1));
            }
        }
        return IRI.create(text);
    }


    public JComponent getFocusComponent() {
        return userSuppliedNameField;
    }
}
