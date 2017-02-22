package org.protege.editor.core.platform.apple;

import java.io.File;

import org.protege.editor.core.ProtegeApplication;
import org.protege.editor.core.ProtegeManager;
import org.protege.editor.core.editorkit.EditorKit;
import org.protege.editor.core.ui.about.AboutPanel;
import org.protege.editor.core.ui.preferences.PreferencesDialogPanel;
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
 * Date: Sep 19, 2008<br><br>
 */
public class ProtegeAppleApplication extends AbstractAppleApplicationWrapper {

    private EditorKit eKit;

    private static ProtegeAppleApplication instance;


    public static ProtegeAppleApplication getInstance(){
        if (instance == null){
            instance = new ProtegeAppleApplication();
        }
        return instance;
    }


    private ProtegeAppleApplication() {
    }


    public void setEditorKit(EditorKit eKit){
        this.eKit = eKit;
        setEnabledPreferencesMenu(eKit != null);
    }
    
    @Override
    protected void editFile(String fileName) throws Exception {
        ProtegeManager.getInstance().getApplication().editURI(new File(fileName).toURI());
    }


    protected boolean handlePreferencesRequest() {
        if (eKit != null){
            PreferencesDialogPanel.showPreferencesDialog(null, eKit);
            return true;
        }
        return false;
    }


    protected boolean handleAboutRequest() {
        AboutPanel.showDialog();
        return true;
    }


    protected boolean handleQuitRequest() {
        return ProtegeApplication.handleQuit();
    }
}
