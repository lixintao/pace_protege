package uk.ac.manchester.cs.owl.owlapi;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLRelation;


public class OWLRelationInstance{

	OWLRelation relation;
	OWLClass relatedTo;
	
	public OWLRelationInstance(OWLRelation relation, OWLClass relatedTo){
		this.relation = relation;
		this.relatedTo = relatedTo;
	}


	public OWLRelation getRelation(){
		return relation;
	}
	
	public String getRelationIRI(){
		return getRelation().toString();
	}

	public OWLClass getRelatedTo(){
		return relatedTo;
	}
	
	public String toRelationName() {
		return getRelation().getName();
	}

	public String toString(){
		return getRelationIRI()+"~"+getRelatedTo().toStringID();
	}
	
}