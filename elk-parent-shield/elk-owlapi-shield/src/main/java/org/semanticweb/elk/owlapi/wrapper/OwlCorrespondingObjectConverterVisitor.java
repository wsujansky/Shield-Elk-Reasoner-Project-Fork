/*
 * #%L
 * ELK OWL API Binding
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
package org.semanticweb.elk.owlapi.wrapper;

import org.semanticweb.elk.owl.interfaces.ElkAnnotationProperty;
import org.semanticweb.elk.owl.interfaces.ElkAnonymousIndividual;
import org.semanticweb.elk.owl.interfaces.ElkClass;
import org.semanticweb.elk.owl.interfaces.ElkClassExpression;
import org.semanticweb.elk.owl.interfaces.ElkDataAllValuesFrom;
import org.semanticweb.elk.owl.interfaces.ElkDataComplementOf;
import org.semanticweb.elk.owl.interfaces.ElkDataExactCardinality;
import org.semanticweb.elk.owl.interfaces.ElkDataHasValue;
import org.semanticweb.elk.owl.interfaces.ElkDataIntersectionOf;
import org.semanticweb.elk.owl.interfaces.ElkDataMaxCardinality;
import org.semanticweb.elk.owl.interfaces.ElkDataMinCardinality;
import org.semanticweb.elk.owl.interfaces.ElkDataOneOf;
import org.semanticweb.elk.owl.interfaces.ElkDataProperty;
import org.semanticweb.elk.owl.interfaces.ElkDataRange;
import org.semanticweb.elk.owl.interfaces.ElkDataSomeValuesFrom;
import org.semanticweb.elk.owl.interfaces.ElkDataUnionOf;
import org.semanticweb.elk.owl.interfaces.ElkDatatype;
import org.semanticweb.elk.owl.interfaces.ElkDatatypeRestriction;
import org.semanticweb.elk.owl.interfaces.ElkEntity;
import org.semanticweb.elk.owl.interfaces.ElkIndividual;
import org.semanticweb.elk.owl.interfaces.ElkNamedIndividual;
import org.semanticweb.elk.owl.interfaces.ElkObjectAllValuesFrom;
import org.semanticweb.elk.owl.interfaces.ElkObjectComplementOf;
import org.semanticweb.elk.owl.interfaces.ElkObjectExactCardinality;
import org.semanticweb.elk.owl.interfaces.ElkObjectHasSelf;
import org.semanticweb.elk.owl.interfaces.ElkObjectHasValue;
import org.semanticweb.elk.owl.interfaces.ElkObjectIntersectionOf;
import org.semanticweb.elk.owl.interfaces.ElkObjectMaxCardinality;
import org.semanticweb.elk.owl.interfaces.ElkObjectMinCardinality;
import org.semanticweb.elk.owl.interfaces.ElkObjectOneOf;
import org.semanticweb.elk.owl.interfaces.ElkObjectProperty;
import org.semanticweb.elk.owl.interfaces.ElkObjectSomeValuesFrom;
import org.semanticweb.elk.owl.interfaces.ElkObjectUnionOf;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRangeVisitorEx;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLEntityVisitorEx;
import org.semanticweb.owlapi.model.OWLIndividualVisitorEx;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;

/**
 * An implementation of several OWL API visitor patterns to convert OWL objects
 * into the corresponding ELK objects, when there is one-to-one correspondence.
 * 
 * @author "Yevgeny Kazakov"
 * 
 */
public class OwlCorrespondingObjectConverterVisitor implements
		OWLClassExpressionVisitorEx<ElkClassExpression>,
		OWLEntityVisitorEx<ElkEntity>, OWLIndividualVisitorEx<ElkIndividual>,
		OWLDataRangeVisitorEx<ElkDataRange> {

	private static OwlCorrespondingObjectConverterVisitor INSTANCE_ = new OwlCorrespondingObjectConverterVisitor();

	private OwlCorrespondingObjectConverterVisitor() {
	}

	public static OwlCorrespondingObjectConverterVisitor getInstance() {
		return INSTANCE_;
	}

	protected static OwlConverter CONVERTER = OwlConverter.getInstance();

	@Override
	public ElkClass visit(OWLClass owlClass) {
		return CONVERTER.convert(owlClass);
	}

	@Override
	public ElkObjectIntersectionOf visit(
			OWLObjectIntersectionOf owlObjectIntersectionOf) {
		return CONVERTER.convert(owlObjectIntersectionOf);
	}

	@Override
	public ElkObjectUnionOf visit(OWLObjectUnionOf owlObjectUnionOf) {
		return CONVERTER.convert(owlObjectUnionOf);
	}

	@Override
	public ElkObjectComplementOf visit(
			OWLObjectComplementOf owlObjectComplementOf) {
		return CONVERTER.convert(owlObjectComplementOf);
	}

	@Override
	public ElkObjectSomeValuesFrom visit(
			OWLObjectSomeValuesFrom owlObjectSomeValuesFrom) {
		return CONVERTER.convert(owlObjectSomeValuesFrom);
	}

	@Override
	public ElkObjectAllValuesFrom visit(
			OWLObjectAllValuesFrom owlObjectAllValuesFrom) {
		return CONVERTER.convert(owlObjectAllValuesFrom);
	}

	@Override
	public ElkObjectHasValue visit(OWLObjectHasValue owlObjectHasValue) {
		return CONVERTER.convert(owlObjectHasValue);
	}

	@Override
	public ElkObjectMinCardinality visit(
			OWLObjectMinCardinality owlObjectMaxCardinality) {
		return CONVERTER.convert(owlObjectMaxCardinality);
	}

	@Override
	public ElkObjectExactCardinality visit(
			OWLObjectExactCardinality owlObjectExactCardinality) {
		return CONVERTER.convert(owlObjectExactCardinality);
	}

	@Override
	public ElkObjectMaxCardinality visit(
			OWLObjectMaxCardinality owlObjectMaxCardinality) {
		return CONVERTER.convert(owlObjectMaxCardinality);
	}

	@Override
	public ElkObjectHasSelf visit(OWLObjectHasSelf owlObjectHasSelf) {
		return CONVERTER.convert(owlObjectHasSelf);
	}

	@Override
	public ElkObjectOneOf visit(OWLObjectOneOf owlObjectOneOf) {
		return CONVERTER.convert(owlObjectOneOf);
	}

	@Override
	public ElkDataSomeValuesFrom visit(
			OWLDataSomeValuesFrom owlDataSomeValuesFrom) {
		return CONVERTER.convert(owlDataSomeValuesFrom);
	}

	@Override
	public ElkDataAllValuesFrom visit(OWLDataAllValuesFrom owlDataAllValuesFrom) {
		return CONVERTER.convert(owlDataAllValuesFrom);
	}

	@Override
	public ElkDataHasValue visit(OWLDataHasValue owlDataHasValue) {
		return CONVERTER.convert(owlDataHasValue);
	}

	@Override
	public ElkDataMinCardinality visit(
			OWLDataMinCardinality owlDataMinCardinality) {
		return CONVERTER.convert(owlDataMinCardinality);
	}

	@Override
	public ElkDataExactCardinality visit(
			OWLDataExactCardinality owlDataExactCardinality) {
		return CONVERTER.convert(owlDataExactCardinality);
	}

	@Override
	public ElkDataMaxCardinality visit(
			OWLDataMaxCardinality owlDataMaxCardinality) {
		return CONVERTER.convert(owlDataMaxCardinality);
	}

	@Override
	public ElkDatatype visit(OWLDatatype owlDatatype) {
		return CONVERTER.convert(owlDatatype);
	}

	@Override
	public ElkAnnotationProperty visit(
			OWLAnnotationProperty owlAnnotationproperty) {
		return CONVERTER.convert(owlAnnotationproperty);
	}

	@Override
	public ElkAnonymousIndividual visit(
			OWLAnonymousIndividual owlAnonymousIndividual) {
		return CONVERTER.convert(owlAnonymousIndividual);
	}

	@Override
	public ElkNamedIndividual visit(OWLNamedIndividual owlNamedIndividual) {
		return CONVERTER.convert(owlNamedIndividual);
	}

	@Override
	public ElkObjectProperty visit(OWLObjectProperty owlObjectProperty) {
		return CONVERTER.convert(owlObjectProperty);
	}

	@Override
	public ElkDataProperty visit(OWLDataProperty owlDataProperty) {
		return CONVERTER.convert(owlDataProperty);
	}

	@Override
	public ElkDataOneOf visit(OWLDataOneOf owlDataOneOf) {
		return CONVERTER.convert(owlDataOneOf);
	}

	@Override
	public ElkDataComplementOf visit(OWLDataComplementOf owlDataComplementOf) {
		return CONVERTER.convert(owlDataComplementOf);
	}

	@Override
	public ElkDataIntersectionOf visit(
			OWLDataIntersectionOf owlDataIntersectionOf) {
		return CONVERTER.convert(owlDataIntersectionOf);
	}

	@Override
	public ElkDataUnionOf visit(OWLDataUnionOf owlDataUnionOf) {
		return CONVERTER.convert(owlDataUnionOf);
	}

	@Override
	public ElkDatatypeRestriction visit(
			OWLDatatypeRestriction owlDatatypeRestriction) {
		return CONVERTER.convert(owlDatatypeRestriction);
	}

}
