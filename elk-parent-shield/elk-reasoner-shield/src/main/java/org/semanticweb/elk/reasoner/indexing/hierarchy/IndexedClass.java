/*
 * #%L
 * elk-reasoner
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

import org.apache.log4j.Logger;
import org.semanticweb.elk.owl.interfaces.ElkClass;
import org.semanticweb.elk.owl.predefined.PredefinedElkClass;
import org.semanticweb.elk.reasoner.indexing.visitors.IndexedClassEntityVisitor;
import org.semanticweb.elk.reasoner.indexing.visitors.IndexedClassVisitor;
import org.semanticweb.elk.reasoner.saturation.BasicSaturationStateWriter;
import org.semanticweb.elk.reasoner.saturation.conclusions.PositiveSubsumer;
import org.semanticweb.elk.reasoner.saturation.context.Context;
import org.semanticweb.elk.reasoner.saturation.rules.ChainableRule;
import org.semanticweb.elk.reasoner.saturation.rules.DecompositionRuleApplicationVisitor;
import org.semanticweb.elk.reasoner.saturation.rules.RuleApplicationVisitor;
import org.semanticweb.elk.util.collections.chains.Chain;
import org.semanticweb.elk.util.collections.chains.Matcher;
import org.semanticweb.elk.util.collections.chains.ModifiableLinkImpl;
import org.semanticweb.elk.util.collections.chains.ReferenceFactory;
import org.semanticweb.elk.util.collections.chains.SimpleTypeBasedMatcher;

/**
 * Represents all occurrences of an {@link ElkClass} in an ontology.
 * 
 * @author Frantisek Simancik
 * @author "Yevgeny Kazakov"
 * 
 */
public class IndexedClass extends IndexedClassEntity {

	protected static final Logger LOGGER_ = Logger
			.getLogger(IndexedClass.class);

	/**
	 * The indexed ElkClass
	 */
	protected final ElkClass elkClass;

	/**
	 * This counts how many times this object occurred in the ontology. Because
	 * of declaration axioms, this number might differ from the sum of the
	 * negative and the positive occurrences counts
	 */
	protected int occurrenceNo = 0;

	/**
	 * Creates an object representing the given ElkClass.
	 */
	protected IndexedClass(ElkClass elkClass) {
		this.elkClass = elkClass;
	}

	/**
	 * @return The represented ElkClass.
	 */
	public ElkClass getElkClass() {
		return elkClass;
	}

	public <O> O accept(IndexedClassVisitor<O> visitor) {
		return visitor.visit(this);
	}

	@Override
	public <O> O accept(IndexedClassEntityVisitor<O> visitor) {
		return visitor.visit(this);
	}

	@Override
	protected void updateOccurrenceNumbers(final ModifiableOntologyIndex index,
			int increment, int positiveIncrement, int negativeIncrement) {

		if (occurrenceNo == 0 && increment > 0) {
			index.addClass(elkClass);
		}

		if (negativeOccurrenceNo == 0 && negativeIncrement > 0
				&& elkClass == PredefinedElkClass.OWL_THING) {
			index.addContextInitRule(new OwlThingContextInitializationRule());
		}

		occurrenceNo += increment;
		positiveOccurrenceNo += positiveIncrement;
		negativeOccurrenceNo += negativeIncrement;

		if (occurrenceNo == 0 && increment < 0) {
			index.removeClass(elkClass);
		}

		if (negativeOccurrenceNo == 0 && negativeIncrement < 0
				&& elkClass == PredefinedElkClass.OWL_THING) {
			index.removeContextInitRule(new OwlThingContextInitializationRule());
		}
	}

	@Override
	public String printOccurrenceNumbers() {
		return "[all=" + occurrenceNo + "; pos=" + positiveOccurrenceNo
				+ "; neg=" + +negativeOccurrenceNo + "]";
	}

	@Override
	public void checkOccurrenceNumbers() {
		if (LOGGER_.isTraceEnabled())
			LOGGER_.trace(toString() + " occurences: "
					+ printOccurrenceNumbers());
		if (occurrenceNo < 0 || positiveOccurrenceNo < 0
				|| negativeOccurrenceNo < 0)
			throw new ElkUnexpectedIndexingException(toString()
					+ " has a negative occurrence: " + printOccurrenceNumbers());
	}

	@Override
	public boolean occurs() {
		return occurrenceNo > 0;
	}

	@Override
	public void accept(DecompositionRuleApplicationVisitor visitor,
			Context context) {
		visitor.visit(this, context);
	}

	@Override
	public String toStringStructural() {
		return '<' + getElkClass().getIri().getFullIriAsString() + '>';
	}

	/**
	 * Adds {@code owl:Thing} to the context. It should be registered as a
	 * context initialization rule iff {@code owl:Thing} occurs negatively in
	 * the ontology.
	 */
	public static class OwlThingContextInitializationRule extends
			ModifiableLinkImpl<ChainableRule<Context>> implements
			ChainableRule<Context> {

		public static final String NAME = "owl:Thing Introduction";

		private OwlThingContextInitializationRule(ChainableRule<Context> tail) {
			super(tail);
		}

		public OwlThingContextInitializationRule() {
			super(null);
		}

		@Override
		public String getName() {
			return NAME;
		}

		@Override
		public void apply(BasicSaturationStateWriter writer, Context context) {
			if (LOGGER_.isTraceEnabled()) {
				LOGGER_.trace("Applying " + NAME + " to " + context);
			}
			writer.produce(context, new PositiveSubsumer(writer.getOwlThing()));
		}

		private static final Matcher<ChainableRule<Context>, OwlThingContextInitializationRule> MATCHER_ = new SimpleTypeBasedMatcher<ChainableRule<Context>, OwlThingContextInitializationRule>(
				OwlThingContextInitializationRule.class);

		private static final ReferenceFactory<ChainableRule<Context>, OwlThingContextInitializationRule> FACTORY_ = new ReferenceFactory<ChainableRule<Context>, OwlThingContextInitializationRule>() {
			@Override
			public OwlThingContextInitializationRule create(
					ChainableRule<Context> tail) {
				return new OwlThingContextInitializationRule(tail);
			}
		};

		@Override
		public boolean addTo(Chain<ChainableRule<Context>> ruleChain) {
			OwlThingContextInitializationRule rule = ruleChain.find(MATCHER_);

			if (rule == null) {
				ruleChain.getCreate(MATCHER_, FACTORY_);
				return true;
			} else {
				return false;
			}
		}

		@Override
		public boolean removeFrom(Chain<ChainableRule<Context>> ruleChain) {
			return ruleChain.remove(MATCHER_) != null;
		}

		@Override
		public void accept(RuleApplicationVisitor visitor,
				BasicSaturationStateWriter writer, Context context) {
			visitor.visit(this, writer, context);
		}

	}
}