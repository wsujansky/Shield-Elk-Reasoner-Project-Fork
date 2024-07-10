package org.semanticweb.elk.reasoner.saturation.conclusions;

import org.apache.log4j.Logger;

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

public class ConclusionStatistics {
	
	/**
	 * Number of conclusions put to the todo queue
	 */
	private final ConclusionCounter producedConclusionCounts_;
	/**
	 * Number of conclusions taken from the todo queue and processed
	 */
	private final ConclusionCounter processedConclusionCounts_;
	/**
	 * Number of unique conclusions saved to contexts
	 */
	private final ConclusionCounter usedConclusionCounts_;
	
	private final ConclusionTimer conclusionProcessingTimer_;
	/**
	 * The number of times measurements were taken in different threads. Used to
	 * average the wall time results.
	 */
	private int numOfMeasurements_ = 0;

	public ConclusionStatistics(ConclusionCounter producedConclusionCounter, ConclusionCounter processedConclusionCounts,
			ConclusionCounter usedConclusionCounts,
			ConclusionTimer conclusionTimers) {
		this.producedConclusionCounts_ = producedConclusionCounter;
		this.processedConclusionCounts_ = processedConclusionCounts;
		this.usedConclusionCounts_ = usedConclusionCounts;
		this.conclusionProcessingTimer_ = conclusionTimers;
	}

	public ConclusionStatistics() {
		this(new ConclusionCounter(), new ConclusionCounter(),
				new ConclusionCounter(), new ConclusionTimer());
	}

	public ConclusionCounter getProducedConclusionCounts() {
		return producedConclusionCounts_;
	}
	
	public ConclusionCounter getProcessedConclusionCounts() {
		return processedConclusionCounts_;
	}

	public ConclusionCounter getUsedConclusionCounts() {
		return usedConclusionCounts_;
	}

	public ConclusionTimer getConclusionTimers() {
		return conclusionProcessingTimer_;
	}

	/**
	 * Reset all timers to zero.
	 */
	public void reset() {
		producedConclusionCounts_.reset();
		processedConclusionCounts_.reset();
		usedConclusionCounts_.reset();
		conclusionProcessingTimer_.reset();
		numOfMeasurements_ = 0;
	}

	public synchronized void add(ConclusionStatistics stats) {
		if (stats.measurementsTaken()) {
			this.numOfMeasurements_ += stats.numOfMeasurements_;
			this.producedConclusionCounts_.add(stats.producedConclusionCounts_);
			this.processedConclusionCounts_
					.add(stats.processedConclusionCounts_);
			this.usedConclusionCounts_.add(stats.usedConclusionCounts_);
			this.conclusionProcessingTimer_
					.add(stats.conclusionProcessingTimer_);
		}
	}

	private static String ERR_MSG_MORE_USED = ": more used that processed!";

	public void check(Logger logger) {
		if (usedConclusionCounts_.countPositiveSubsumers > processedConclusionCounts_.countPositiveSubsumers)
			logger.error("Positive Subsumers" + ERR_MSG_MORE_USED);
		if (usedConclusionCounts_.countNegativeSubsumers > processedConclusionCounts_.countNegativeSubsumers)
			logger.error("Negative Subsumers" + ERR_MSG_MORE_USED);
		if (usedConclusionCounts_.countBackwardLinks > processedConclusionCounts_.countBackwardLinks)
			logger.error("Backward Links" + ERR_MSG_MORE_USED);
		if (usedConclusionCounts_.countForwardLinks > processedConclusionCounts_.countForwardLinks)
			logger.error("Forward Links" + ERR_MSG_MORE_USED);
		if (usedConclusionCounts_.countPropagations > processedConclusionCounts_.countPropagations)
			logger.error("Propagations" + ERR_MSG_MORE_USED);
		if (usedConclusionCounts_.countContradictions > processedConclusionCounts_.countContradictions)
			logger.error("Contradictions" + ERR_MSG_MORE_USED);
		if (usedConclusionCounts_.countDisjointnessAxioms > processedConclusionCounts_.countDisjointnessAxioms)
			logger.error("Disjointness Axioms" + ERR_MSG_MORE_USED);
	}

	public void print(Logger logger) {
		if (!logger.isDebugEnabled()) {
			return;
		}
		
		if (!measurementsTaken()) {
			return;
		}
		
		if (processedConclusionCounts_.countPositiveSubsumers > 0
				|| conclusionProcessingTimer_.timePositiveSubsumers > 0)
			logger.debug("Positive Subsumers produced/used: "
					+ processedConclusionCounts_.countPositiveSubsumers + "/"
					+ usedConclusionCounts_.countPositiveSubsumers + " ("
					+ conclusionProcessingTimer_.timePositiveSubsumers
					/ numOfMeasurements_ + " ms)");
		if (processedConclusionCounts_.countNegativeSubsumers > 0
				|| conclusionProcessingTimer_.timeNegativeSubsumers > 0)
			logger.debug("Negative Subsumers produced/used: "
					+ processedConclusionCounts_.countNegativeSubsumers + "/"
					+ usedConclusionCounts_.countNegativeSubsumers + " ("
					+ conclusionProcessingTimer_.timeNegativeSubsumers
					/ numOfMeasurements_ + " ms)");
		if (processedConclusionCounts_.countBackwardLinks > 0
				|| conclusionProcessingTimer_.timeBackwardLinks > 0)
			logger.debug("Backward Links produced/used: "
					+ processedConclusionCounts_.countBackwardLinks + "/"
					+ usedConclusionCounts_.countBackwardLinks + " ("
					+ conclusionProcessingTimer_.timeBackwardLinks / numOfMeasurements_
					+ " ms)");
		if (processedConclusionCounts_.countForwardLinks > 0
				|| conclusionProcessingTimer_.timeForwardLinks > 0)
			logger.debug("Forward Links produced/used: "
					+ processedConclusionCounts_.countForwardLinks + "/"
					+ usedConclusionCounts_.countForwardLinks + " ("
					+ conclusionProcessingTimer_.timeForwardLinks / numOfMeasurements_
					+ " ms)");
		if (processedConclusionCounts_.countPropagations > 0
				|| conclusionProcessingTimer_.timePropagations > 0)
			logger.debug("Propagations produced/used: "
					+ processedConclusionCounts_.countPropagations + "/"
					+ usedConclusionCounts_.countPropagations + " ("
					+ conclusionProcessingTimer_.timePropagations / numOfMeasurements_
					+ " ms)");
		if (processedConclusionCounts_.countContradictions > 0
				|| conclusionProcessingTimer_.timeContradictions > 0)
			logger.debug("Contradictions produced/used: "
					+ processedConclusionCounts_.countContradictions + "/"
					+ usedConclusionCounts_.countContradictions + " ("
					+ conclusionProcessingTimer_.timeContradictions
					/ numOfMeasurements_ + " ms)");
		if (processedConclusionCounts_.countDisjointnessAxioms > 0
				|| conclusionProcessingTimer_.timeDisjointnessAxioms > 0)
			logger.debug("Disjointness Axioms produced/used: "
					+ processedConclusionCounts_.countDisjointnessAxioms + "/"
					+ usedConclusionCounts_.countDisjointnessAxioms + " ("
					+ conclusionProcessingTimer_.timeDisjointnessAxioms
					/ numOfMeasurements_ + " ms)");
		long totalTime = conclusionProcessingTimer_.getTotalTime();
		if (totalTime > 0)
			logger.debug("Total conclusion processing time: " + totalTime
					/ numOfMeasurements_ + " ms");
	}
	
	public void startMeasurements() {
		if (numOfMeasurements_ < 1) {
			numOfMeasurements_ = 1;
		}
	}
	
	private boolean measurementsTaken() {
		return numOfMeasurements_ > 0;
	}

}
