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

public class ConclusionTimer {

	long timeNegativeSubsumers;

	long timePositiveSubsumers;

	long timeBackwardLinks;

	long timeForwardLinks;

	long timeContradictions;

	long timePropagations;

	long timeDisjointnessAxioms;

	public long getTimeNegativeSubsumers() {
		return timeNegativeSubsumers;
	}

	public long getTimePositiveSubsumers() {
		return timePositiveSubsumers;
	}

	public long getTimeBackwardLinks() {
		return timeBackwardLinks;
	}

	public long getTimeForwardLinks() {
		return timeForwardLinks;
	}

	public long getTimeBottoms() {
		return timeContradictions;
	}

	public long getTimePropagations() {
		return timePropagations;
	}

	public long getTimeDisjointnessAxioms() {
		return timeDisjointnessAxioms;
	}

	public long getTotalTime() {
		return timeNegativeSubsumers + timePositiveSubsumers
				+ timeBackwardLinks + timeForwardLinks + timePropagations
				+ timeContradictions + timeDisjointnessAxioms;
	}

	/**
	 * Reset all times to zero.
	 */
	public void reset() {
		timeNegativeSubsumers = 0;
		timePositiveSubsumers = 0;
		timeBackwardLinks = 0;
		timeForwardLinks = 0;
		timeContradictions = 0;
		timePropagations = 0;
		timeDisjointnessAxioms = 0;
	}

	/**
	 * Adds all timers of the argument to the corresponding counters of this
	 * object. The timers should not be directly modified other than using this
	 * method during this operation. The timers in the argument will be reseted
	 * after this operation.
	 * 
	 * @param timer
	 *            the object which counters should be added
	 */
	public synchronized void add(ConclusionTimer timer) {
		this.timeNegativeSubsumers += timer.timeNegativeSubsumers;
		this.timePositiveSubsumers += timer.timePositiveSubsumers;
		this.timeBackwardLinks += timer.timeBackwardLinks;
		this.timeForwardLinks += timer.timeForwardLinks;
		this.timeContradictions += timer.timeContradictions;
		this.timePropagations += timer.timePropagations;
		this.timeDisjointnessAxioms += timer.timeDisjointnessAxioms;
	}

}
