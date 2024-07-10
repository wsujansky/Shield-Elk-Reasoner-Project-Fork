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
/**
 * 
 */
package org.semanticweb.elk.reasoner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.semanticweb.elk.loading.AxiomLoader;
import org.semanticweb.elk.reasoner.config.ReasonerConfiguration;
import org.semanticweb.elk.reasoner.stages.ReasonerStageExecutor;
import org.semanticweb.elk.reasoner.stages.SimpleStageExecutor;

/**
 * The main factory to instantiate {@link Reasoner}
 * 
 * @author Pavel Klinov
 * 
 *         pavel.klinov@uni-ulm.de
 * 
 * @author Yevgeny Kazakov
 * 
 */
public class ReasonerFactory {

	final static Logger LOGGER_ = Logger.getLogger(ReasonerFactory.class);
	final static ReasonerStageExecutor DEFAULT_STAGE_EXECUTOR = new SimpleStageExecutor();

	public Reasoner createReasoner(AxiomLoader axiomLoader) {
		return createReasoner(axiomLoader, DEFAULT_STAGE_EXECUTOR,
				ReasonerConfiguration.getConfiguration());
	}

	/**
	 * Creates {@link Reasoner} with the configuration loaded from
	 * elk.properties (if exists in the classpath) or the default configuration
	 * 
	 * @param stageExecutor
	 * @return ELK reasoner
	 */
	public Reasoner createReasoner(AxiomLoader axiomLoader,
			ReasonerStageExecutor stageExecutor) {
		return createReasoner(axiomLoader, stageExecutor,
				ReasonerConfiguration.getConfiguration());
	}

	/**
	 * Creates {@link Reasoner} with the provided configuration
	 * 
	 * @param stageExecutor
	 * @param config
	 * @return ELK reasoner
	 */
	public Reasoner createReasoner(AxiomLoader axiomLoader,
			ReasonerStageExecutor stageExecutor, ReasonerConfiguration config) {
		return createReasoner(axiomLoader, stageExecutor,
				Executors.newCachedThreadPool(), config);
	}

	@SuppressWarnings("static-method")
	public Reasoner createReasoner(AxiomLoader axiomLoader,
			ReasonerStageExecutor stageExecutor, ExecutorService executor,
			ReasonerConfiguration config) {
		Reasoner reasoner = new Reasoner(axiomLoader, stageExecutor, executor);

		reasoner.setConfigurationOptions(config);

		return reasoner;
	}
}