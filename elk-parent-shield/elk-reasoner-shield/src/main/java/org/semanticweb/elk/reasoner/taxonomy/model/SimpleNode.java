package org.semanticweb.elk.reasoner.taxonomy.model;
/*
 * #%L
 * ELK Reasoner
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2013 Department of Computer Science, University of Oxford
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

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.semanticweb.elk.owl.interfaces.ElkObject;
import org.semanticweb.elk.owl.printers.OwlFunctionalStylePrinter;

public class SimpleNode<T extends ElkObject> implements Node<T> {

	final SortedSet<T> members;

	public SimpleNode(Collection<T> members, Comparator<T> cmp) {
		this.members = new TreeSet<T>(cmp);
		this.members.addAll(members);
	}

	@Override
	public Set<T> getMembers() {
		return members;
	}

	@Override
	public T getCanonicalMember() {
		return members.isEmpty() ? null : members.iterator().next();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (T member : members) {
			builder.append(OwlFunctionalStylePrinter.toString(member) + ",");
		}

		return builder.toString();
	}
}
