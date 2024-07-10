/*
 * #%L
 * ELK OWL API Binding
 * 
 * $Id$
 * $HeadURL$
 * %%
 * Copyright (C) 2011 Department of Computer Science, University of Oxford
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.semanticweb.elk.owlapi.wrapper;

import org.semanticweb.elk.owl.interfaces.ElkAnnotationAxiom;
import org.semanticweb.elk.owl.visitors.ElkAnnotationAxiomVisitor;
import org.semanticweb.elk.owl.visitors.ElkAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAnnotationAxiom;

/**
 * Implements the {@link ElkAnnotationAxiom} interface by wrapping instances
 * of {@link OWLAnnotationAxiom}
 * 
 * @author Frantisek Simancik
 * 
 * @param <T>
 *            the type of the wrapped object
 */
public abstract class ElkAnnotationAxiomWrap<T extends OWLAnnotationAxiom>
		extends ElkAxiomWrap<T> implements ElkAnnotationAxiom {

	public ElkAnnotationAxiomWrap(T owlAnnotationAxiom) {
		super(owlAnnotationAxiom);
	}

	@Override
	abstract public <O> O accept(ElkAnnotationAxiomVisitor<O> visitor);

	@Override
	public <O> O accept(ElkAxiomVisitor<O> visitor) {
		return accept((ElkAnnotationAxiomVisitor<O>) visitor);
	}
}