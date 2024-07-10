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
package org.semanticweb.elk.reasoner.indexing.entries;

import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedAxiom;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedBinaryPropertyChain;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedClass;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedClassExpression;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedDataHasValue;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedDisjointnessAxiom;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedIndividual;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedObjectComplementOf;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedObjectIntersectionOf;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedObjectProperty;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedObjectSomeValuesFrom;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedObjectUnionOf;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedPropertyChain;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedSubClassOfAxiom;
import org.semanticweb.elk.reasoner.indexing.visitors.IndexedAxiomVisitor;
import org.semanticweb.elk.reasoner.indexing.visitors.IndexedClassExpressionVisitor;
import org.semanticweb.elk.reasoner.indexing.visitors.IndexedPropertyChainVisitor;
import org.semanticweb.elk.util.collections.entryset.KeyEntry;
import org.semanticweb.elk.util.collections.entryset.KeyEntryHashSet;

/**
 * A visitor for {@link IndexedClassExpression}s and
 * {@link IndexedPropertyChain}s that wraps the visited objects in the
 * corresponding Entry wrapper to redefine equality.
 * 
 * @author Frantisek Simancik
 * @author "Yevgeny Kazakov"
 * 
 * @param <T>
 *            the type of the elements for which the wrapped entries can be used
 * @see KeyEntryHashSet
 */
public class IndexedEntryConverter<T>
		implements
		IndexedAxiomVisitor<KeyEntry<T, ? extends IndexedAxiom>>,
		IndexedClassExpressionVisitor<KeyEntry<T, ? extends IndexedClassExpression>>,
		IndexedPropertyChainVisitor<IndexedPropertyChainEntry<T, ? extends IndexedPropertyChain>> {

	@Override
	public IndexedClassExpressionEntry<T, IndexedClass> visit(
			IndexedClass element) {
		return new IndexedClassEntry<T, IndexedClass>(element);
	}

	@Override
	public IndexedClassExpressionEntry<T, IndexedObjectComplementOf> visit(
			IndexedObjectComplementOf element) {
		return new IndexedObjectComplementOfEntry<T, IndexedObjectComplementOf>(
				element);
	}

	@Override
	public IndexedClassExpressionEntry<T, IndexedObjectIntersectionOf> visit(
			IndexedObjectIntersectionOf element) {
		return new IndexedObjectIntersectionOfEntry<T, IndexedObjectIntersectionOf>(
				element);
	}

	@Override
	public IndexedClassExpressionEntry<T, IndexedObjectSomeValuesFrom> visit(
			IndexedObjectSomeValuesFrom element) {
		return new IndexedObjectSomeValuesFromEntry<T, IndexedObjectSomeValuesFrom>(
				element);
	}

	@Override
	public KeyEntry<T, ? extends IndexedClassExpression> visit(
			IndexedObjectUnionOf element) {
		return new IndexedObjectUnionOfEntry<T, IndexedObjectUnionOf>(element);
	}

	@Override
	public IndexedClassExpressionEntry<T, IndexedDataHasValue> visit(
			IndexedDataHasValue element) {
		return new IndexedDataHasValueEntry<T, IndexedDataHasValue>(element);
	}

	@Override
	public IndexedPropertyChainEntry<T, IndexedObjectProperty> visit(
			IndexedObjectProperty element) {
		return new IndexedObjectPropertyEntry<T, IndexedObjectProperty>(element);
	}

	@Override
	public IndexedPropertyChainEntry<T, IndexedBinaryPropertyChain> visit(
			IndexedBinaryPropertyChain element) {
		return new IndexedBinaryPropertyChainEntry<T, IndexedBinaryPropertyChain>(
				element);
	}

	@Override
	public IndexedIndividualEntry<T, IndexedIndividual> visit(
			IndexedIndividual element) {
		return new IndexedIndividualEntry<T, IndexedIndividual>(element);
	}

	@Override
	public KeyEntry<T, ? extends IndexedSubClassOfAxiom> visit(
			IndexedSubClassOfAxiom axiom) {
		return new IndexedSubClassOfAxiomEntry<T, IndexedSubClassOfAxiom>(axiom);
	}

	@Override
	public KeyEntry<T, ? extends IndexedAxiom> visit(
			IndexedDisjointnessAxiom axiom) {
		return new IndexedDisjointnessAxiomEntry<T, IndexedDisjointnessAxiom>(
				axiom);
	}

}
