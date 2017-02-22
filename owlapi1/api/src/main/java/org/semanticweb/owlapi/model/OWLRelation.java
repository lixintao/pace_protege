package org.semanticweb.owlapi.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class OWLRelation{

	String relationName;
	String nsName;
	
	boolean Asymmetric = false;
    boolean Functional = false;
    boolean InverseFunctional = false;
    boolean Irreflexive = false;
    boolean Reflexive = false;
    boolean Symmetric = false;
    boolean Transitive = false;
	
	public OWLRelation(String ns, String name){
		setName(ns, name);
	}

	public void setName(String ns, String name){
		this.relationName = name;
		this.nsName       = ns;
	}

	public String getName(){
		return relationName;
	}

	public String getNS(){
		return nsName;
	}
	
	public String toString() {
		if(getNS().equals(""))
			return nsName + relationName;
		else
			return nsName + "#" + relationName;
	}

	public boolean isAsymmetric(){
		return Asymmetric;
	}
	
    public boolean isFunctional(){
		return Functional;
	}

    public boolean isInverseFunctional(){
		return InverseFunctional;
	}
	
    public boolean isIrreflexive(){
		return Irreflexive;
	}
	
    public boolean isReflexive(){
		return Reflexive;
	}
	
    public boolean isSymmetric(){
		return Symmetric;
	}

	
    public boolean isTransitive(){
		return Transitive;
	}

	public void setAsymmetric(boolean b){
		this.Asymmetric = b;
	}
	
    public void setFunctional(boolean b){
		this.Functional = b;
	}

    public void setInverseFunctional(boolean b){
		this.InverseFunctional = b;
	}
	
    public void setIrreflexive(boolean b){
		this.Irreflexive = b;
	}
	
    public void setReflexive(boolean b){
		this.Reflexive = b;
	}
	
    public void setSymmetric(boolean b){
		this.Symmetric = b;
	}
	
    public void setTransitive(boolean b){
		this.Transitive = b;
	}
	
}