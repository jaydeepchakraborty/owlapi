/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.util.StructuralTransformation;

@SuppressWarnings({"javadoc"})
@RunWith(Parameterized.class)
public class StructuralTransformationTestCase {

    private final OWLAxiom object;
    private final String expected;

    public StructuralTransformationTestCase(OWLAxiom object, String expected) {
        this.object = object;
        this.expected = expected;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> getData() {
        Builder b = new Builder();
        Map<OWLAxiom, String> map = new LinkedHashMap<>();
        map.put(b.dRange(),
            "[SubClassOf(owl:Thing DataAllValuesFrom(<urn:test:test#dp> <urn:test:test#datatype>))]");
        map.put(b.dDef(),
            "[DatatypeDefinition(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#datatype> xsd:double)]");
        map.put(b.decC(),
            "[Declaration(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) Class(<urn:test:test#c>))]");
        map.put(b.decOp(),
            "[Declaration(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) ObjectProperty(<urn:test:test#op>))]");
        map.put(b.decDp(),
            "[Declaration(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) DataProperty(<urn:test:test#dp>))]");
        map.put(b.decDt(),
            "[Declaration(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) Datatype(<urn:test:test#datatype>))]");
        map.put(b.decAp(),
            "[Declaration(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) AnnotationProperty(<urn:test:test#ann>))]");
        map.put(b.decI(),
            "[Declaration(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) NamedIndividual(<urn:test:test#i>))]");
        map.put(b.dDp(), "[DisjointDataProperties(<urn:test:test#dp> <urn:test:test#iri> )]");
        map.put(b.dOp(), "[DisjointObjectProperties(<urn:test:test#iri> <urn:test:test#op> )]");
        map.put(b.eDp(), "[EquivalentDataProperties(<urn:test:test#dp> <urn:test:test#iri> )]");
        map.put(b.eOp(), "[EquivalentObjectProperties(<urn:test:test#iri> <urn:test:test#op> )]");
        map.put(b.fdp(),
            "[SubClassOf(owl:Thing DataMaxCardinality(1 <urn:test:test#dp> rdfs:Literal))]");
        map.put(b.fop(),
            "[SubClassOf(owl:Thing ObjectMaxCardinality(1 <urn:test:test#op> owl:Thing))]");
        map.put(b.ifp(),
            "[SubClassOf(owl:Thing ObjectMaxCardinality(1 ObjectInverseOf(<urn:test:test#op>) owl:Thing))]");
        map.put(b.iop(),
            "[SubObjectPropertyOf(<urn:test:test#op> ObjectInverseOf(<urn:test:test#op>))]");
        map.put(b.irr(),
            "[IrreflexiveObjectProperty(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#op>)]");
        map.put(b.opa(),
            "[ObjectPropertyAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#op> <urn:test:test#i> <urn:test:test#i>)]");
        map.put(b.opaInv(),
            "[ObjectPropertyAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) ObjectInverseOf(<urn:test:test#op>) <urn:test:test#i> <urn:test:test#i>)]");
        map.put(b.opaInvj(),
            "[ObjectPropertyAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) ObjectInverseOf(<urn:test:test#op>) <urn:test:test#i> <urn:test:test#j>)]");
        map.put(b.oDom(),
            "[SubClassOf(<http://www.semanticweb.org/ontology#X0> <urn:test:test#c>), SubClassOf(<http://www.semanticweb.org/ontology#X1> "
                + "ObjectAllValuesFrom(<urn:test:test#op> owl:Nothing)), "
                + "SubClassOf(owl:Thing ObjectUnionOf(<http://www.semanticweb.org/ontology#X0> <http://www.semanticweb.org/ontology#X1>))]");
        map.put(b.oRange(),
            "[SubClassOf(<http://www.semanticweb.org/ontology#X0> <urn:test:test#c>), "
                + "SubClassOf(owl:Thing ObjectAllValuesFrom(<urn:test:test#op> <http://www.semanticweb.org/ontology#X0>))]");
        map.put(b.chain(),
            "[SubObjectPropertyOf(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) ObjectPropertyChain( <urn:test:test#iri> <urn:test:test#op> ) <urn:test:test#op>)]");
        map.put(b.ref(),
            "[ReflexiveObjectProperty(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#op>)]");
        map.put(b.same(), "[]");
        map.put(b.subAnn(),
            "[SubAnnotationPropertyOf(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#ann> rdfs:label)]");
        map.put(b.subClass(), "[SubClassOf(<http://www.semanticweb.org/ontology#X0> owl:Thing), "
            + "SubClassOf(<http://www.semanticweb.org/ontology#X1> ObjectComplementOf(<urn:test:test#c>)), "
            + "SubClassOf(owl:Thing ObjectUnionOf(<http://www.semanticweb.org/ontology#X0> <http://www.semanticweb.org/ontology#X1>))]");
        map.put(b.subData(), "[SubDataPropertyOf(<urn:test:test#dp> owl:topDataProperty)]");
        map.put(b.subObject(),
            "[SubObjectPropertyOf(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#op> owl:topObjectProperty)]");
        map.put(b.rule(),
            "[DLSafeRule( Body(BuiltInAtom(<urn:swrl:var#v1> Variable(<urn:swrl:var#var3>) Variable(<urn:swrl:var#var4>) )) "
                + "Head(BuiltInAtom(<urn:swrl:var#v2> Variable(<urn:swrl:var#var5>) Variable(<urn:swrl:var#var6>) )) )]");
        map.put(b.symm(),
            "[SymmetricObjectProperty(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#op>)]");
        map.put(b.trans(),
            "[TransitiveObjectProperty(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#op>)]");
        map.put(b.hasKey(),
            "[HasKey(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#c> (<urn:test:test#iri> <urn:test:test#op> ) (<urn:test:test#dp> ))]");
        map.put(b.bigRule(),
            "[DLSafeRule(Annotation(<urn:test:test#ann> \"test\"^^xsd:string)  Body(BuiltInAtom(<urn:swrl:var#v1> Variable(<urn:swrl:var#var3>) Variable(<urn:swrl:var#var4>) ) "
                + "ClassAtom(<urn:test:test#c> Variable(<urn:swrl:var#var2>)) DataRangeAtom(<urn:test:test#datatype> Variable(<urn:swrl:var#var1>)) "
                + "BuiltInAtom(<urn:test:test#iri> Variable(<urn:swrl:var#var1>) ) DifferentFromAtom(Variable(<urn:swrl:var#var2>) <urn:test:test#i>) "
                + "SameAsAtom(Variable(<urn:swrl:var#var2>) <urn:test:test#iri>)) Head(BuiltInAtom(<urn:swrl:var#v2> Variable(<urn:swrl:var#var5>) "
                + "Variable(<urn:swrl:var#var6>) ) DataPropertyAtom(<urn:test:test#dp> Variable(<urn:swrl:var#var2>) \"false\"^^xsd:boolean) "
                + "ObjectPropertyAtom(<urn:test:test#op> Variable(<urn:swrl:var#var2>) Variable(<urn:swrl:var#var2>))) )]");
        map.put(b.ann(),
            "[AnnotationAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#ann> <urn:test:test#iri> \"false\"^^xsd:boolean)]");
        map.put(b.asymm(),
            "[AsymmetricObjectProperty(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#op>)]");
        map.put(b.annDom(), "[AnnotationPropertyDomain(<urn:test:test#ann> <urn:test:test#iri>)]");
        map.put(b.annRange(), "[AnnotationPropertyRange(<urn:test:test#ann> <urn:test:test#iri>)]");
        map.put(b.dRangeAnd(),
            "[SubClassOf(owl:Thing DataAllValuesFrom(<urn:test:test#dp> DataIntersectionOf(<urn:test:test#datatype> DataOneOf(\"false\"^^xsd:boolean ) )))]");
        map.put(b.dRangeOr(),
            "[SubClassOf(owl:Thing DataAllValuesFrom(<urn:test:test#dp> DataUnionOf(<urn:test:test#datatype> DataOneOf(\"false\"^^xsd:boolean ) )))]");
        map.put(b.dOneOf(),
            "[SubClassOf(owl:Thing DataAllValuesFrom(<urn:test:test#dp> DataOneOf(\"false\"^^xsd:boolean )))]");
        map.put(b.dNot(),
            "[SubClassOf(owl:Thing DataAllValuesFrom(<urn:test:test#dp> DataComplementOf(DataOneOf(\"false\"^^xsd:boolean ))))]");
        map.put(b.dRangeRestrict(),
            "[SubClassOf(owl:Thing DataAllValuesFrom(<urn:test:test#dp> DataRangeRestriction(xsd:double "
                + "facetRestriction(minExclusive \"5.0\"^^xsd:double) facetRestriction(maxExclusive \"6.0\"^^xsd:double))))]");
        map.put(b.assD(),
            "[DataPropertyAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#dp> <urn:test:test#i> \"false\"^^xsd:boolean)]");
        map.put(b.assDPlain(),
            "[DataPropertyAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#dp> <urn:test:test#i> \"string\"@en)]");
        map.put(b.dDom(),
            "[SubClassOf(<http://www.semanticweb.org/ontology#X0> <urn:test:test#c>), "
                + "SubClassOf(<http://www.semanticweb.org/ontology#X1> DataAllValuesFrom(<urn:test:test#dp> DataComplementOf(rdfs:Literal))), "
                + "SubClassOf(owl:Thing ObjectUnionOf(<http://www.semanticweb.org/ontology#X0> <http://www.semanticweb.org/ontology#X1>))]");
        map.put(b.dc(),
            "[SubClassOf(owl:Thing ObjectComplementOf(<urn:test:test#c>)), SubClassOf(owl:Thing ObjectComplementOf(<urn:test:test#iri>))]");
        map.put(b.du(), "[SubClassOf(<http://www.semanticweb.org/ontology#X0> <urn:test:test#c>), "
            + "SubClassOf(<http://www.semanticweb.org/ontology#X1> <urn:test:test#iri>), "
            + "SubClassOf(owl:Thing <urn:test:test#c>), "
            + "SubClassOf(owl:Thing ObjectUnionOf(<http://www.semanticweb.org/ontology#X0> <http://www.semanticweb.org/ontology#X1>)), "
            + "SubClassOf(owl:Thing ObjectComplementOf(<urn:test:test#c>)), "
            + "SubClassOf(owl:Thing ObjectComplementOf(<urn:test:test#iri>))]");
        map.put(b.ec(),
            "[SubClassOf(owl:Thing <urn:test:test#c>), SubClassOf(owl:Thing <urn:test:test#iri>)]");
        Collection<Object[]> toReturn = new ArrayList<>();
        map.forEach((k, v) -> toReturn.add(new Object[] {k, v}));
        return toReturn;
    }

    @Test
    public void testAssertion() {
        StructuralTransformation testsubject =
            new StructuralTransformation(OWLManager.getOWLDataFactory());
        Set<OWLAxiom> singleton = Collections.singleton(object);
        String result = new TreeSet<>(testsubject.getTransformedAxioms(singleton)).toString();
        assertEquals(expected.replace(",", ",\n"), result.replace(",", ",\n"));
    }
}
