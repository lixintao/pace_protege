package org.protege.editor.owl.ui.view.relation;

import org.protege.editor.owl.ui.list.OWLAxiomList;
import org.protege.editor.owl.ui.list.OWLObjectList;
import org.protege.editor.core.ui.util.ComponentFactory;
import org.semanticweb.owlapi.model.*;
import javax.swing.JList;
import org.protege.editor.owl.model.util.OWLDataTypeUtils;
import org.protege.editor.owl.model.util.OWLEntityDeleter;
import java.util.Collections;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import org.protege.editor.core.ui.view.DisposableAction;
import org.protege.editor.owl.ui.view.OWLSelectionViewAction;
import org.protege.editor.owl.ui.OWLIcons;
import org.semanticweb.owlapi.model.OWLOntology;

import org.semanticweb.owlapi.model.OWLRelationChangeListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.BorderLayout;
import org.apache.log4j.Logger;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;

public class OWLRelationViewComponent extends AbstractOWLViewComponent {
    private static final long serialVersionUID = -4515710047558710080L;
    private static final Logger log = Logger.getLogger(OWLRelationViewComponent.class);
	private OWLRelationList list;
	private OWLOntology actOnt;
	
	private OWLRelationChangeListener relListner = new OWLRelationChangeListener(){
		public void relationChanged(String changeType, OWLRelation rel){
			//Maybe inefficient
			reload();
		}
	};
	
	private ListSelectionListener relSelListener = new ListSelectionListener(){

        public void valueChanged(ListSelectionEvent e) {
            getOWLWorkspace().getOWLSelectionModel().setSelectedRelation(list.getSelectedValue());
        }
    };
	
	
    @Override
    protected void initialiseOWLView() throws Exception {
        setLayout(new BorderLayout());
		list = new OWLRelationList(getOWLEditorKit());
		actOnt = getOWLEditorKit().getOWLModelManager().getActiveOntology();
		actOnt.addRelationChangeListner(relListner);
		reload();
		setupActions();
		add(ComponentFactory.createScrollPane(list));
		list.addListSelectionListener(relSelListener);
    }

	@Override
	protected void disposeOWLView() {
		list.removeListSelectionListener(relSelListener);
		actOnt.removeRelationChangeListner(relListner);
	}
	
	private void reload(){
        actOnt = getOWLModelManager().getActiveOntology();
        java.util.List<OWLRelation> relationList = actOnt.getAllRelations();
        //Collections.sort(datatypeList, getOWLModelManager().getOWLObjectComparator());

        list.setListData(relationList.toArray(new OWLRelation[relationList.size()]));
        //final OWLDatatype sel = getOWLWorkspace().getOWLSelectionModel().getLastSelectedDatatype();
        //if (datatypeList.contains(sel)){
          //  list.setSelectedValue(sel, true);
        //}
    }
	
	    private void setupActions() {
        final DisposableAction addDatatypeAction = new DisposableAction("Add relation", OWLIcons.getIcon("datarange.add.png")) {
            /**
             * 
             */
            private static final long serialVersionUID = 7152977701137488187L;

            public void actionPerformed(ActionEvent event) {
                createNewRelation();
            }

            public void dispose() {
                // do nothing
            }
        };

        final OWLSelectionViewAction deleteDatatypeAction = new OWLSelectionViewAction("Delete relation", OWLIcons.getIcon("datarange.remove.png")) {

            /**
             * 
             */
            private static final long serialVersionUID = 5359788681251086828L;


            public void actionPerformed(ActionEvent event) {
                deleteRelation();
            }


            public void updateState() {
                // @@TODO should check if this is a built in datatype
                setEnabled(list.getSelectedIndex() != -1);
            }


            public void dispose() {
                // do nothing
            }
        };

        addAction(addDatatypeAction, "A", "A");
        addAction(deleteDatatypeAction, "B", "A");
    }

	public void createNewRelation(){
		System.out.println("Create relation called");
		newRelationCreationPanel.showDialog(getOWLEditorKit(), "Please enter a datatype name");
	}
	
	public void deleteRelation(){
		for(OWLRelation rel : list.getSelectedOWLObjects())
			actOnt.removeRelation(rel.toString());
	}
}
