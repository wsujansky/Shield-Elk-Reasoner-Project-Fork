/**
 * 
 */
package org.semanticweb.elk.reasoner.indexing.hierarchy;
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

import org.semanticweb.elk.owl.interfaces.ElkObjectProperty;

/**
 * A utility class to create indexed objects for other tests
 * 
 * @author Pavel Klinov
 * 
 *         pavel.klinov@uni-ulm.de
 */
public class IndexedObjectsCreator {

	public static IndexedObjectProperty createIndexedObjectProperty(
			ElkObjectProperty prop, IndexedPropertyChain[] toldSubs,
			IndexedObjectProperty[] toldSupers, boolean reflexive) {

		IndexedObjectProperty property = new IndexedObjectProperty(prop);

		for (IndexedPropertyChain sub : toldSubs) {
			property.addToldSubPropertyChain(sub);
			sub.addToldSuperObjectProperty(property);
		}

		for (IndexedObjectProperty sup : toldSupers) {
			property.addToldSuperObjectProperty(sup);
			sup.addToldSubPropertyChain(property);
		}
		
		if (reflexive) {
			property.reflexiveAxiomOccurrenceNo = 1;
		}

		return property;
	}
	
	public static IndexedPropertyChain createIndexedChain(
			IndexedObjectProperty left, IndexedPropertyChain right,
			IndexedObjectProperty[] toldSupers) {

		IndexedBinaryPropertyChain chain = new IndexedBinaryPropertyChain(left, right);

		for (IndexedObjectProperty sup : toldSupers) {
			chain.addToldSuperObjectProperty(sup);
			sup.addToldSubPropertyChain(chain);
		}
		
		left.addLeftChain(chain);
		right.addRightChain(chain);

		return chain;
	}

}