/**
 * 
 */
package org.semanticweb.elk.reasoner.saturation.conclusions;

/*
 * #%L
 * ELK Reasoner
 * $Id:$
 * $HeadURL:$
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

import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedDisjointnessAxiom;
import org.semanticweb.elk.reasoner.saturation.BasicSaturationStateWriter;
import org.semanticweb.elk.reasoner.saturation.context.Context;

/**
 * @author Pavel Klinov
 * 
 *         pavel.klinov@uni-ulm.de
 */
public class DisjointnessAxiom extends AbstractConclusion {

	private final IndexedDisjointnessAxiom axiom_;

	public DisjointnessAxiom(IndexedDisjointnessAxiom axiom) {
		axiom_ = axiom;
	}

	public IndexedDisjointnessAxiom getAxiom() {
		return axiom_;
	}

	public void apply(BasicSaturationStateWriter engine, Context context) {
		if (context.inconsistencyDisjointnessAxiom(axiom_)) {
			engine.produce(context, Contradiction.getInstance());
		}
	}

	@Override
	public <R> R accept(ConclusionVisitor<R> visitor, Context context) {
		return visitor.visit(this, context);
	}

	@Override
	public String toString() {
		return axiom_.toString();
	}
}