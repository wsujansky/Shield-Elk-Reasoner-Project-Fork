/*
 * #%L
 * ELK Reasoner
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
package org.semanticweb.elk.reasoner.indexing.hierarchy;

import org.semanticweb.elk.owl.interfaces.ElkSubObjectPropertyExpression;
import org.semanticweb.elk.reasoner.indexing.visitors.IndexedBinaryPropertyChainVisitor;
import org.semanticweb.elk.reasoner.indexing.visitors.IndexedPropertyChainVisitor;
import org.semanticweb.elk.reasoner.indexing.visitors.IndexedPropertyChainVisitorEx;

/**
 * Represents a complex {@link ElkSubObjectPropertyExpression}s. The chain
 * consists of two components: an {@link IndexedObjectProperty} on the left and
 * an {@link IndexedPropertyChain} on the right. This reflects the fact that
 * property inclusions are binarized during index constructions. The auxiliary
 * {@link IndexedBinaryPropertyChain}s may not represent any ElkObject in the
 * ontology.
 * 
 * @author Frantisek Simancik
 * @author "Yevgeny Kazakov"
 * 
 */

public class IndexedBinaryPropertyChain extends IndexedPropertyChain {

	private final IndexedObjectProperty leftProperty_;
	private final IndexedPropertyChain rightProperty_;

	/**
	 * Used for creating auxiliary inclusions during binarization.
	 * 
	 * @param leftProperty
	 * @param rightProperty
	 */
	IndexedBinaryPropertyChain(IndexedObjectProperty leftProperty,
			IndexedPropertyChain rightProperty) {

		this.leftProperty_ = leftProperty;
		this.rightProperty_ = rightProperty;
	}

	/**
	 * @return The left component of this (binary) complex property inclusion
	 *         axiom.
	 */
	public IndexedObjectProperty getLeftProperty() {
		return leftProperty_;
	}

	/**
	 * @return The right component of this (binary) complex property inclusion
	 *         axiom.
	 */
	public IndexedPropertyChain getRightProperty() {
		return rightProperty_;
	}

	@Override
	void updateOccurrenceNumber(int increment) {

		if (occurrenceNo == 0 && increment > 0) {
			// first occurrence of this expression
			rightProperty_.addRightChain(this);
			leftProperty_.addLeftChain(this);
		}

		occurrenceNo += increment;

		if (occurrenceNo == 0 && increment < 0) {
			// no occurrences of this conjunction left
			rightProperty_.removeRightChain(this);
			leftProperty_.removeLeftChain(this);
		}

	}

	@Override
	public <O> O accept(IndexedPropertyChainVisitor<O> visitor) {
		return visitor.visit(this);
	}

	public <O> O accept(IndexedBinaryPropertyChainVisitor<O> visitor) {
		return visitor.visit(this);
	}

	@Override
	public <O, P> O accept(IndexedPropertyChainVisitorEx<O, P> visitor,
			P parameter) {
		return visitor.visit(this, parameter);
	}

	/**
	 * @param ipc
	 * @return the property chain which is composable with the given property in this
	 * chain or null
	 */
	public IndexedPropertyChain getComposable(IndexedPropertyChain ipc) {
		return ipc == leftProperty_ ? rightProperty_
				: (ipc == rightProperty_ ? leftProperty_ : null);
	}

	@Override
	public String toStringStructural() {
		return "ObjectPropertyChain(" + this.leftProperty_ + ' '
				+ this.rightProperty_ + ')';
	}
}
