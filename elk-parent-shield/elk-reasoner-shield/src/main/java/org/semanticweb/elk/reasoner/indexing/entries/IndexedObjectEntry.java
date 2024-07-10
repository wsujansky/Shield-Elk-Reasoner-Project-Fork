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

import java.util.List;

import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedObject;
import org.semanticweb.elk.util.collections.entryset.KeyEntryHashSet;
import org.semanticweb.elk.util.collections.entryset.StrongKeyEntry;
import org.semanticweb.elk.util.hashing.HashGenerator;

/**
 * The wrapper class to define custom equality and hash functions for
 * {@link IndexedObject}s to be used in {@link KeyEntryHashSet}. It is based on
 * the extension of the {@link StrongKeyEntry} class.
 * 
 * @author "Yevgeny Kazakov"
 * 
 * @param <T>
 *            The type of the elements in the set where this entry is used
 * 
 * @param <K>
 *            the type of the wrapped indexed object used as the key of the
 *            entry
 */
public abstract class IndexedObjectEntry<T, K extends IndexedObject> extends
		StrongKeyEntry<T, K> {

	IndexedObjectEntry(K representative) {
		super(representative);
	}

	static int combinedHashCode(Object... objects) {
		return HashGenerator.combinedHashCode(objects);
	}

	static int combinedHashCode(List<?> objects) {
		return HashGenerator.combinedHashCode(objects);
	}

}
