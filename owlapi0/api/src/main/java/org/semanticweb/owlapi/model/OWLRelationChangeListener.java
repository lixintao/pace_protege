package org.semanticweb.owlapi.model;

import java.util.List;

/**
 * @author Khagesh Patel
 *         Group, Date: 15-June-2015
 */
public interface OWLRelationChangeListener {

    /**
     * Called when some changes have been applied to various ontologies. These
     * may be an axiom added or an axiom removed changes.
     * 
     * @param changes
     *        A list of changes that have occurred. Each change may be examined
     *        to determine which ontology it was applied to.
     * @throws OWLException
     *         exception
     */
    void relationChanged(String changeType, OWLRelation rel);
}
