package org.protege.editor.owl.model.entity;

import org.semanticweb.owlapi.model.IRI;
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
 * Date: Jul 28, 2008<br><br>
 */
public interface LabelDescriptor {

    String getLanguage();

    // @@TODO should be getAnnotationProperty() ?
    IRI getIRI();
}
