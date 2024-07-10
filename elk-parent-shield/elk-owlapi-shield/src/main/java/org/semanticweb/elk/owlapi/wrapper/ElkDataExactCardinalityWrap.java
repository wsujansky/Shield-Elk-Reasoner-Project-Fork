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

import org.semanticweb.elk.owl.interfaces.ElkDataExactCardinality;
import org.semanticweb.elk.owl.interfaces.ElkDataPropertyExpression;
import org.semanticweb.elk.owl.visitors.ElkClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;

/**
 * Implements the {@link ElkDataExactCardinality} interface by wrapping
 * instances of {@link OWLDataExactCardinality}
 * 
 * @author "Yevgeny Kazakov"
 * 
 * @param <T>
 *            the type of the wrapped object
 */
public class ElkDataExactCardinalityWrap<T extends OWLDataExactCardinality>
		extends ElkClassExpressionWrap<T> implements ElkDataExactCardinality {

	public ElkDataExactCardinalityWrap(T owlDataExactCardinality) {
		super(owlDataExactCardinality);
	}

	@Override
	public int getCardinality() {
		return this.owlObject.getCardinality();
	}

	@Override
	public ElkDataPropertyExpression getProperty() {
		return converter.convert(this.owlObject.getProperty());
	}

	@Override
	public <O> O accept(ElkClassExpressionVisitor<O> visitor) {
		return visitor.visit(this);
	}
}