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
package org.semanticweb.elk.reasoner;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import org.semanticweb.elk.io.IOUtils;
import org.semanticweb.elk.owl.implementation.ElkObjectFactoryImpl;
import org.semanticweb.elk.owl.interfaces.ElkClass;
import org.semanticweb.elk.owl.interfaces.ElkObjectFactory;
import org.semanticweb.elk.owl.managers.ElkEntityRecycler;
import org.semanticweb.elk.owl.parsing.Owl2ParseException;
import org.semanticweb.elk.owl.parsing.javacc.Owl2FunctionalStyleParserFactory;
import org.semanticweb.elk.reasoner.taxonomy.MockTaxonomyLoader;
import org.semanticweb.elk.reasoner.taxonomy.model.Taxonomy;
import org.semanticweb.elk.testing.ConfigurationUtils;
import org.semanticweb.elk.testing.ConfigurationUtils.TestManifestCreator;
import org.semanticweb.elk.testing.PolySuite.Config;
import org.semanticweb.elk.testing.PolySuite.Configuration;
import org.semanticweb.elk.testing.TestManifest;
import org.semanticweb.elk.testing.io.URLTestIO;

/**
 * 
 * @author Pavel Klinov
 * 
 *         pavel.klinov@uni-ulm.de
 */
public abstract class DiffClassificationCorrectnessTest extends
		BaseClassificationCorrectnessTest<ClassTaxonomyTestOutput> {

	public DiffClassificationCorrectnessTest(
			ReasoningTestManifest<ClassTaxonomyTestOutput, ClassTaxonomyTestOutput> testManifest) {
		super(testManifest);
	}

	/*
	 * Configuration: loading all inputs and expected outputs
	 */
	@Config
	public static Configuration getConfig() throws URISyntaxException,
			IOException {
		return ConfigurationUtils
				.loadFileBasedTestConfiguration(
						INPUT_DATA_LOCATION,
						DiffClassificationCorrectnessTest.class,
						"owl",
						"expected",
						new TestManifestCreator<URLTestIO, ClassTaxonomyTestOutput, ClassTaxonomyTestOutput>() {
							@Override
							public TestManifest<URLTestIO, ClassTaxonomyTestOutput, ClassTaxonomyTestOutput> create(
									URL input, URL output) throws IOException {
								// input and expected output are OWL ontologies
								InputStream stream = null;

								try {
									ElkObjectFactory objectFactory = new ElkObjectFactoryImpl(
											new ElkEntityRecycler());
									Taxonomy<ElkClass> expectedTaxonomy = MockTaxonomyLoader
											.load(objectFactory,
													new Owl2FunctionalStyleParserFactory(
															objectFactory).getParser(stream = output
															.openStream()));

									return new TaxonomyDiffManifest<ClassTaxonomyTestOutput, ClassTaxonomyTestOutput>(
											input, new ClassTaxonomyTestOutput(
													expectedTaxonomy));

								} catch (Owl2ParseException e) {
									throw new IOException(e);
								} finally {
									IOUtils.closeQuietly(stream);
								}
							}
						});
	}
}
