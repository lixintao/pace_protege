package uk.ac.man.cs.mig.coode.owlviz.model;

//My changes
import org.apache.log4j.Logger;
import java.util.Set;

import org.semanticweb.owlapi.util.VersionInfo;

import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.model.event.EventType;
import org.protege.editor.owl.model.event.OWLModelManagerChangeEvent;
import org.protege.editor.owl.model.event.OWLModelManagerListener;
import org.protege.editor.owl.model.hierarchy.OWLObjectHierarchyProvider;
import org.protege.editor.owl.model.hierarchy.OWLObjectHierarchyProviderListener;
import org.semanticweb.owlapi.model.*;
import uk.ac.man.cs.mig.util.graph.model.GraphModel;
import uk.ac.man.cs.mig.util.graph.model.impl.AbstractGraphModel;

import java.util.*;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: 08-Jun-2006<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class AbstractOWLClassGraphModel extends AbstractGraphModel {

    private static final Logger logger = Logger.getLogger(AbstractOWLClassGraphModel.class);
	
	HashMap DirClassExpObjProp = new HashMap();

    private OWLModelManager owlModelManager;

    private OWLObjectHierarchyProvider provider;

    private OWLObjectHierarchyProviderListener listener;

    private OWLOntologyChangeListener changeListener;

    private OWLModelManagerListener owlModelManagerListener;

    public AbstractOWLClassGraphModel(OWLModelManager owlModelManager,
                                      OWLObjectHierarchyProvider provider) {
        this.owlModelManager = owlModelManager;
        listener = new OWLObjectHierarchyProviderListener() {

            public void nodeChanged(OWLObject node) {
                // TODO: Sync!
            }


            public void childParentAdded(OWLObject child, OWLObject parent) {
                fireChildAddedEvent(parent, child);
                fireParentAddedEvent(child, parent);
            }

            public void childParentRemoved(OWLObject child, OWLObject parent) {
                fireChildRemovedEvent(parent, child);
                fireParentRemovedEvent(child, parent);
            }

            public void rootAdded(OWLObject root) {

            }

            public void rootRemoved(OWLObject root) {
            }

            public void hierarchyChanged() {
            }
        };
        provider.addListener(listener);
        this.provider = provider;
        changeListener = new OWLOntologyChangeListener() {
            public void ontologiesChanged(List<? extends OWLOntologyChange> changes) {
            }
        };
        owlModelManager.addOntologyChangeListener(changeListener);
        owlModelManagerListener = new OWLModelManagerListener() {
            public void handleChange(OWLModelManagerChangeEvent event) {
                if(event.isType(EventType.ACTIVE_ONTOLOGY_CHANGED) || event.isType(EventType.ONTOLOGY_RELOADED)) {
                    // Clear
					DirClassExpObjProp.clear();
                    fireModelChangedEvent();
                }
            }
        };
        owlModelManager.addListener(owlModelManagerListener);
    }

    public void dispose() {
		DirClassExpObjProp.clear();
        provider.removeListener(listener);
        owlModelManager.removeOntologyChangeListener(changeListener);
        owlModelManager.removeListener(owlModelManagerListener);
    }

	public Set<OWLClass> getObjRelChildren(OWLObject obj){
		//logger.info("Classes:");
		//logger.info(obj.getClassesInSignature());
		//logger.info("Object Property:");
		//logger.info(obj.getObjectPropertiesInSignature());
		OWLClass t = (OWLClass)obj;
		Set<OWLClass> related = new HashSet<OWLClass>();
		//logger.info("Called On:");
		//logger.info(t);
		//logger.info("SubClasses On:");
		//logger.info(t.getSubClasses(owlModelManager.getActiveOntologies()));
		for(OWLOntology ont : owlModelManager.getActiveOntologies()){
			//logger.info("Object Property:");
			//logger.info(getObjectPropertiesInSignature())
			//ont.printAllRelations();
			Set<OWLClassAxiom> tempAx=ont.getAxioms(t);
            for(OWLClassAxiom ax: tempAx){
                for(OWLClassExpression nce:ax.getNestedClassExpressions())
                    if(nce.getClassExpressionType()!=ClassExpressionType.OWL_CLASS)
						if(ax.getClassesInSignature().size() == 2 && ax.getObjectPropertiesInSignature().size() == 1){
						  //logger.info("New Class Expression");
						  Iterator<OWLClass> ClsInSig = ax.getClassesInSignature().iterator();
						  OWLClass FirstObjExp   = ClsInSig.next();
                          OWLClass SecondObjExp	 = ClsInSig.next();
						  String labelObjProp = ax.getObjectPropertiesInSignature().iterator().next().getIRI().toString().split("#")[1];
						  String FStr = t.getIRI().toString().split("#")[1];
						  if(FirstObjExp.getIRI().toString().equals(t.getIRI().toString())){
							related.add(SecondObjExp);
							String MStr = FStr+"#"+SecondObjExp.getIRI().toString().split("#")[1];
							DirClassExpObjProp.put(MStr,labelObjProp);
						  }
						  else{
							related.add(FirstObjExp);
							String MStr = FStr+"#"+FirstObjExp.getIRI().toString().split("#")[1];
							DirClassExpObjProp.put(MStr,labelObjProp);
						  }
		     			  //System.out.println(t);
                          //System.out.println(FirstObjExp);
						  //System.out.println(ax.getObjectPropertiesInSignature());
						  //System.out.println(SecondObjExp);
						  //System.out.println(ax);
						}
                }
			}
			//System.out.println("--> OWLAPI called");
			//System.out.println(VersionInfo.getVersionInfo().getVersion());
			//logger.info("Signature:");
			//logger.info(obj.getSignature());
			return related;
	}
	
    protected Set<OWLObject> getChildren(OWLObject obj) {
        Set<OWLObject> children = new HashSet<OWLObject>();
            children.addAll(provider.getChildren(obj));
            children.addAll(provider.getEquivalents(obj));
			//children.addAll(getObjRelChildren(obj));
			for(OWLOntology ont : owlModelManager.getActiveOntologies()){
				children.addAll(ont.getAllOwlClasses((OWLClass)obj));
			}
        return children;
    }


    protected Set<OWLObject> getParents(OWLObject obj) {
        Set<OWLObject> parents = new HashSet<OWLObject>();
            parents.addAll(provider.getParents(obj));
            parents.addAll(provider.getEquivalents(obj));
        return parents;
    }

    public int getChildCount(Object obj) {
        return getChildren((OWLObject) obj).size();
    }

    public Iterator getChildren(Object obj) {
        return getChildren((OWLObject) obj).iterator();
    }

    public int getParentCount(Object obj) {
        return getParents((OWLObject) obj).size();
    }

    public Iterator getParents(Object obj) {
        return getParents((OWLObject) obj).iterator();
    }

    public boolean contains(Object obj) {
            if(obj instanceof OWLClass) {
                for(OWLOntology ont : owlModelManager.getActiveOntologies()) {
                    if(ont.containsClassInSignature(((OWLClass) obj).getIRI())) {
                        return true;
                    }
                }
            }
        return false;
    }

	//Add the case of is-a
	
    public Object getRelationshipType(Object parentObject, Object childObject) {
		OWLClass parent = (OWLClass)parentObject;
		OWLClass child  = (OWLClass)childObject;
		/**String KeyToSearch = parent.getIRI().toString().split("#")[1]+"#"+child.getIRI().toString().split("#")[1];
		*if(DirClassExpObjProp.containsKey(KeyToSearch))
		*	return DirClassExpObjProp.get(KeyToSearch);
		*/
		for(OWLOntology ont : owlModelManager.getActiveOntologies()) {
                if(ont.getEdgeLabelMap(parent).containsKey(child.toStringID())) {
                    return ont.getEdgeLabelMap(parent).get(child.toStringID());
                }
        }
        return " is-a ";
    }

    public int getRelationshipDirection(Object parentObject, Object childObject) {
		OWLClass parent = (OWLClass)parentObject;
		OWLClass child  = (OWLClass)childObject;
		/**String KeyToSearch = parent.getIRI().toString().split("#")[1]+"#"+child.getIRI().toString().split("#")[1];
		*if(DirClassExpObjProp.containsKey(KeyToSearch))
		*	return GraphModel.DIRECTION_FORWARD;
		*/
		for(OWLOntology ont : owlModelManager.getActiveOntologies()) {
                if(ont.getEdgeLabelMap(parent).containsKey(child.toStringID())) {
                    return GraphModel.DIRECTION_FORWARD;
                }
        }
        return GraphModel.DIRECTION_BACK;
    }

    public Iterator getRelatedObjectsToAdd(Object obj) {
        return Collections.EMPTY_LIST.iterator();
    }

    public Iterator getRelatedObjectsToRemove(Object obj) {
        return Collections.EMPTY_LIST.iterator();
    }

}
