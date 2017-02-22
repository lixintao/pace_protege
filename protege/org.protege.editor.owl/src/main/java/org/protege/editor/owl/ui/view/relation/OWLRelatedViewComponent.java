package org.protege.editor.owl.ui.view.relation;


import org.protege.editor.owl.ui.list.OWLAxiomList;
import org.protege.editor.owl.ui.list.OWLObjectList;
import org.protege.editor.core.ui.util.ComponentFactory;
import org.semanticweb.owlapi.model.*;
import javax.swing.*;
import java.awt.*;
import org.protege.editor.owl.model.util.OWLDataTypeUtils;
import org.protege.editor.owl.model.util.OWLEntityDeleter;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.awt.event.ActionEvent;
import org.protege.editor.core.ui.view.DisposableAction;
import org.protege.editor.owl.ui.view.OWLSelectionViewAction;
import org.protege.editor.owl.ui.OWLIcons;
import org.semanticweb.owlapi.model.OWLOntology;

import org.protege.editor.owl.model.selection.OWLSelectionModelListener;

import org.semanticweb.owlapi.model.OWLRelationChangeListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.BorderLayout;
import org.apache.log4j.Logger;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;

public class OWLRelatedViewComponent extends AbstractOWLViewComponent {
    private static final long serialVersionUID = -4515710047558710080L;
    private static final Logger log = Logger.getLogger(OWLRelationViewComponent.class);
	private OWLRelationList listRelation;
	private OWLObjectList<OWLClass> listRelated;
	private OWLOntology actOnt;
	
	private OWLRelationChangeListener relListner = new OWLRelationChangeListener(){
		public void relationChanged(String changeType, OWLRelation rel){
			//Maybe inefficient
			reload();
		}
	};
	
	private OWLSelectionModelListener selListner = new OWLSelectionModelListener(){
		public void selectionChanged() throws Exception{
			reload();
		}
	};
	
	private ListSelectionListener relSelListener = new ListSelectionListener(){

        public void valueChanged(ListSelectionEvent e) {
            reloadClasses();
        }
    };
	
    @Override
    protected void initialiseOWLView() throws Exception {
        setLayout(new BorderLayout());
		listRelation = new OWLRelationList(getOWLEditorKit());
		listRelation.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listRelated = new OWLObjectList<OWLClass>(getOWLEditorKit());
		actOnt = getOWLEditorKit().getOWLModelManager().getActiveOntology();
		actOnt.addRelationChangeListner(relListner);
		reload();
		setupActions();
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,ComponentFactory.createScrollPane(listRelation), ComponentFactory.createScrollPane(listRelated));
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(150);
		add(splitPane);
		getOWLWorkspace().getOWLSelectionModel().addListener(selListner);
		listRelation.addListSelectionListener(relSelListener);
		//add(ComponentFactory.createScrollPane(list));
    }

	@Override
	protected void disposeOWLView() {
		getOWLModelManager().getActiveOntology().removeRelationChangeListner(relListner);
		getOWLWorkspace().getOWLSelectionModel().removeListener(selListner);
	}
	
	private void reload(){
        actOnt = getOWLModelManager().getActiveOntology();
		OWLClass curr = getOWLWorkspace().getOWLSelectionModel().getLastSelectedClass();
		if(curr == null)
			return;
		Map<OWLRelation, List<OWLClass>> RelClassMap = actOnt.getRelationToClassMap(curr);
        java.util.Set<OWLRelation> relationListTmp = RelClassMap.keySet();
        //Collections.sort(datatypeList, getOWLModelManager().getOWLObjectComparator());
		listRelation.setListData(relationListTmp.toArray(new OWLRelation[relationListTmp.size()]));
		if(relationListTmp.size()>0){
			listRelation.setSelectedIndex(0);
			List<OWLClass> relatedListTmp = RelClassMap.get(listRelation.getSelectedValue());
			listRelated.setListData(relatedListTmp.toArray(new OWLClass[relatedListTmp.size()]));
			listRelated.setSelectedIndex(0);
		}
		else{
			List<OWLClass> relatedListTmp = new ArrayList<OWLClass>();
			listRelated.setListData(relatedListTmp.toArray(new OWLClass[relatedListTmp.size()]));
		}
    }
	
	private void reloadClasses(){
        actOnt = getOWLModelManager().getActiveOntology();
		OWLClass curr = getOWLWorkspace().getOWLSelectionModel().getLastSelectedClass();
		if(curr == null)
			return;
		Map<OWLRelation, List<OWLClass>> RelClassMap = actOnt.getRelationToClassMap(curr);
		if(RelClassMap == null)
			return;
        java.util.Set<OWLRelation> relationListTmp = RelClassMap.keySet();
		//if(relationListTmp == null)
		//	return;
		if(relationListTmp.size()>0){
			List<OWLClass> relatedListTmp = RelClassMap.get(listRelation.getSelectedValue());
			if(relatedListTmp == null){
				listRelation.setListData(relationListTmp.toArray(new OWLRelation[relationListTmp.size()]));
				if(relationListTmp.size()>0){
					listRelation.setSelectedIndex(0);
					relatedListTmp = RelClassMap.get(listRelation.getSelectedValue());
				}
				else{
					relatedListTmp = new ArrayList<OWLClass>();
				}
				
			}
			
			listRelated.setListData(relatedListTmp.toArray(new OWLClass[relatedListTmp.size()]));
			listRelated.setSelectedIndex(0);
		}
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
                setEnabled(listRelated.getSelectedIndex() != -1);
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
		OWLClass currCls = getOWLWorkspace().getOWLSelectionModel().getLastSelectedClass();
		if(currCls == null || currCls.isOWLThing())
			return;
		newRelatedCreationPanel.showDialog(getOWLEditorKit(),"Create new relations",currCls);
		reload();
	}
	
	public void deleteRelation(){
		actOnt = getOWLModelManager().getActiveOntology();
		OWLClass A = getOWLWorkspace().getOWLSelectionModel().getLastSelectedClass();
		if(A == null)
			return;
		for(OWLClass B : listRelated.getSelectedOWLObjects())
			actOnt.removeRelated(A,listRelation.getSelectedValue().toString(),B);
		reload();
	}
}
