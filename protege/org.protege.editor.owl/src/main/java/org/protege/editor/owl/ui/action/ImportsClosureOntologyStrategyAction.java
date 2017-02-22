package org.protege.editor.owl.ui.action;

import org.protege.editor.owl.model.selection.ontologies.ImportsClosureOntologySelectionStrategy;
import org.protege.editor.owl.model.selection.ontologies.OntologySelectionStrategy;

/**
 * Author: drummond<br>
 * http://www.cs.man.ac.uk/~drummond/<br><br>
 * <p/>
 * The University Of Manchester<br>
 * Bio Health Informatics Group<br>
 * Date: Jun 6, 2008<br><br>
 */
public class ImportsClosureOntologyStrategyAction extends AbstractOntologySelectionStrategyAction {

    private OntologySelectionStrategy strategy;

    protected OntologySelectionStrategy getStrategy() {
        if (strategy == null){
            strategy = new ImportsClosureOntologySelectionStrategy(getOWLModelManager());
        }
        return strategy;
    }
    
    @Override
    public void initialise() throws Exception {    	
        super.initialise();
        getOWLModelManager().setActiveOntologiesStrategy(getStrategy());
        setSelected(isCurrent());
    }
}