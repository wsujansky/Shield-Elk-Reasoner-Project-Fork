/*
 * #%L
 * ELK Reasoner
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
package org.semanticweb.elk.reasoner.saturation.conclusions;

import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedClassExpression;
import org.semanticweb.elk.reasoner.saturation.BasicSaturationStateWriter;
import org.semanticweb.elk.reasoner.saturation.context.Context;
import org.semanticweb.elk.reasoner.saturation.rules.DecompositionRuleApplicationVisitor;
import org.semanticweb.elk.reasoner.saturation.rules.RuleApplicationVisitor;

/**
 * A {@link Subsumer}, for which the structure of the enclosed
 * {@link IndexedClassExpression} should be taken into account. That is, in
 * addition to composition rules stored with this {@link IndexedClassExpression}
 * , the so-called decomposition rule, which takes into account the topmost
 * constructor of this {@link IndexedClassExpression}, should be applied.
 * 
 * @author Frantisek Simancik
 * @author "Yevgeny Kazakov"
 * 
 */
public class PositiveSubsumer extends Subsumer {

	public PositiveSubsumer(IndexedClassExpression superClassExpression) {
		super(superClassExpression);
	}

	public void apply(BasicSaturationStateWriter writer, Context context,
			RuleApplicationVisitor ruleAppVisitor,
			DecompositionRuleApplicationVisitor decompVisitor) {
		// apply decomposition rules
		expression.accept(decompVisitor, context);
		// applying all composition rules
		applyCompositionRules(writer, context, ruleAppVisitor);
	}

	@Override
	public <R> R accept(ConclusionVisitor<R> visitor, Context context) {
		return visitor.visit(this, context);
	}

}