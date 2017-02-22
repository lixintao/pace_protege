package org.protege.editor.owl.ui.library;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Collections;

import org.protege.editor.core.ProtegeApplication;
import org.protege.editor.core.ui.util.UIUtil;
import org.protege.editor.owl.ui.action.ProtegeOWLAction;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: 24-Aug-2006<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class EditOntologyLibraryAction extends ProtegeOWLAction {
    private static final long serialVersionUID = -4297673435512878237L;


    public void actionPerformed(ActionEvent e) {
    	try {
    		File catalogFile = UIUtil.openFile(getOWLWorkspace(), "Choose catalog file containing ontology repository information", "Choose XML Catalog", Collections.singleton("xml"));
    		if (catalogFile != null) {
    			OntologyLibraryPanel.showDialog(getOWLEditorKit(), catalogFile);
    		}
    	}
    	catch (Exception ex) {
    		ProtegeApplication.getErrorLog().logError(ex);
    	}
    }


    public void initialise() throws Exception {
    }


    public void dispose() {
    }
}
