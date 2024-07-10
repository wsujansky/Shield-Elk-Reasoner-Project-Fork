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

import org.semanticweb.elk.owl.interfaces.ElkIndividual;
import org.semanticweb.elk.owl.interfaces.ElkNamedIndividual;
import org.semanticweb.elk.reasoner.indexing.visitors.IndexedClassEntityVisitor;
import org.semanticweb.elk.reasoner.indexing.visitors.IndexedIndividualVisitor;
import org.semanticweb.elk.reasoner.saturation.context.Context;
import org.semanticweb.elk.reasoner.saturation.rules.DecompositionRuleApplicationVisitor;

/**
 * Represents all occurrences of an {@link ElkIndividual} in an ontology.
 * 
 * @author Frantisek Simancik
 * @author "Yevgeny Kazakov"
 * 
 */
public class IndexedIndividual extends IndexedClassEntity {
	/**
	 * The ElkNamedIndividual that is the sole instance of this nominal
	 */
	private final ElkNamedIndividual elkNamedIndividual_;

	private int occurrenceNo_ = 0;

	IndexedIndividual(ElkNamedIndividual elkNamedIndividual) {
		this.elkNamedIndividual_ = elkNamedIndividual;
	}

	/**
	 * @return The represented ElkNamedIndividual.
	 */
	public ElkNamedIndividual getElkNamedIndividual() {
		return elkNamedIndividual_;
	}

	public <O> O accept(IndexedIndividualVisitor<O> visitor) {
		return visitor.visit(this);
	}

	@Override
	public <O> O accept(IndexedClassEntityVisitor<O> visitor) {
		return visitor.visit(this);
	}

	@Override
	protected void updateOccurrenceNumbers(final ModifiableOntologyIndex index,
			int increment, int positiveIncrement, int negativeIncrement) {

		if (occurrenceNo_ == 0 && increment > 0) {
			index.addNamedIndividual(elkNamedIndividual_);
		}

		occurrenceNo_ += increment;
		positiveOccurrenceNo += positiveIncrement;
		negativeOccurrenceNo += negativeIncrement;

		if (occurrenceNo_ == 0 && increment < 0) {
			index.removeNamedIndividual(elkNamedIndividual_);
		}
	}

	@Override
	public boolean occurs() {
		return occurrenceNo_ > 0;
	}

	@Override
	public String toStringStructural() {
		return "ObjectOneOf(<"
				+ elkNamedIndividual_.getIri().getFullIriAsString() + ">)";
	}

	@Override
	public void accept(DecompositionRuleApplicationVisitor visitor,
			Context context) {
		// TODO Auto-generated method stub
	}
}
