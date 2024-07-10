/*
 * #%L
 * ELK OWL API Binding
 * 
 * $Id$
 * $HeadURL$
 * %%
 * Copyright (C) 2011 - 2012 Department of Computer Science, University of Oxford
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
/**
 * 
 */
package org.semanticweb.elk.owlapi.wrapper;

import org.semanticweb.elk.owl.interfaces.ElkAnnotationAssertionAxiom;
import org.semanticweb.elk.owl.interfaces.ElkAnnotationProperty;
import org.semanticweb.elk.owl.interfaces.ElkAnnotationSubject;
import org.semanticweb.elk.owl.interfaces.ElkAnnotationValue;
import org.semanticweb.elk.owl.visitors.ElkAnnotationAxiomVisitor;
import org.semanticweb.elk.owl.visitors.ElkAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;

/**
 * @author Pavel Klinov
 * 
 *         pavel.klinov@uni-ulm.de
 * 
 * @author "Yevgeny Kazakov"
 * 
 * @param <T>
 *            the type of the wrapped {@link OWLAnnotationAssertionAxiom}
 */
public class ElkAnnotationAssertionAxiomWrap<T extends OWLAnnotationAssertionAxiom>
		extends ElkAxiomWrap<T> implements ElkAnnotationAssertionAxiom {

	public ElkAnnotationAssertionAxiomWrap(T owlAxiom) {
		super(owlAxiom);
	}

	@Override
	public <O> O accept(ElkAxiomVisitor<O> visitor) {
		return visitor.visit(this);
	}

	@Override
	public <O> O accept(ElkAnnotationAxiomVisitor<O> visitor) {
		return visitor.visit(this);
	}

	@Override
	public ElkAnnotationSubject getSubject() {
		return converter.convert(owlObject.getSubject());
	}

	@Override
	public ElkAnnotationProperty getProperty() {
		return converter.convert(owlObject.getProperty());
	}

	@Override
	public ElkAnnotationValue getValue() {
		return converter.convert(owlObject.getValue());
	}

}
