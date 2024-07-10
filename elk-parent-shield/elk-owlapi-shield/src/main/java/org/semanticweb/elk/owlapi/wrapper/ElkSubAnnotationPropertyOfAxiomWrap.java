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

import org.semanticweb.elk.owl.interfaces.ElkAnnotationProperty;
import org.semanticweb.elk.owl.interfaces.ElkSubAnnotationPropertyOfAxiom;
import org.semanticweb.elk.owl.visitors.ElkAnnotationAxiomVisitor;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;

/**
 * Implements the {@link ElkSubAnnotationPropertyOfAxiom} interface by wrapping
 * instances of {@link OWLSubAnnotationPropertyOfAxiom}
 *
 *  @author Frantisek Simancik
 *  
 * @param <T>
 *            the type of the wrapped object
 *
 */
public class ElkSubAnnotationPropertyOfAxiomWrap<T extends OWLSubAnnotationPropertyOfAxiom>
		extends ElkAnnotationAxiomWrap<T> implements
		ElkSubAnnotationPropertyOfAxiom {

	public ElkSubAnnotationPropertyOfAxiomWrap(T owlSubAnnotationPropertyOfAxiom) {
		super(owlSubAnnotationPropertyOfAxiom);
	}

	@Override
	public ElkAnnotationProperty getSubAnnotationProperty() {
		return converter.convert(this.owlObject.getSubProperty());
	}

	@Override
	public ElkAnnotationProperty getSuperAnnotationProperty() {
		return converter.convert(this.owlObject.getSuperProperty());
	}

	@Override
	public <O> O accept(ElkAnnotationAxiomVisitor<O> visitor) {
		return visitor.visit(this);
	}
}