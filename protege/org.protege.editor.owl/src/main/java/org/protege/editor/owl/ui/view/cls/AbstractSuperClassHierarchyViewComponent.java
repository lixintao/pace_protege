package org.protege.editor.owl.ui.view.cls;

import org.protege.editor.owl.model.hierarchy.AbstractSuperClassHierarchyProvider;
import org.protege.editor.owl.model.hierarchy.OWLObjectHierarchyProvider;
import org.semanticweb.owlapi.model.OWLClass;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: 14-Sep-2006<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public abstract class AbstractSuperClassHierarchyViewComponent extends AbstractOWLClassHierarchyViewComponent {


    /**
     * 
     */
    private static final long serialVersionUID = 4012388467228453755L;


    protected OWLClass updateView(OWLClass selectedClass) {
        getOWLClassHierarchyProvider().setRoot(selectedClass);
        OWLClass cls = super.updateView(selectedClass);
        // Expand
//        getTree().expandAll();
        getTree().expandRow(0);
        return cls;
    }


    protected final OWLObjectHierarchyProvider<OWLClass> getHierarchyProvider() {
        return getOWLClassHierarchyProvider();
    }


    protected abstract AbstractSuperClassHierarchyProvider getOWLClassHierarchyProvider();
}
