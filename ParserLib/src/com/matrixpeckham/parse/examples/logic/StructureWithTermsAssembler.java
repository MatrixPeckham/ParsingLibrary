package com.matrixpeckham.parse.examples.logic;

import com.matrixpeckham.parse.engine.Axiom;
import com.matrixpeckham.parse.engine.Structure;
import com.matrixpeckham.parse.engine.Term;
import com.matrixpeckham.parse.examples.query.QueryBuilder;
import com.matrixpeckham.parse.parse.Assembler;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.utensil.TypeOrType;
import java.util.ArrayList;
import java.util.logging.Logger;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Pops the terms and functor of a structure from an assembly's stack, builds a
 * structure, and pushes it.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class StructureWithTermsAssembler extends Assembler<Token, TypeOrType<Axiom, Term>, QueryBuilder> {

    /**
     * Reverse a vector into an array of terms.
     *
     * @param v the vector to reverse
     *
     * @return Term[] the vector, reversed
     */
    public static Term[] vectorReversedIntoTerms(
            ArrayList<TypeOrType<Token, TypeOrType<Axiom, Term>>> v) {
        int size = v.size();
        Term[] terms = new Term[size];
        for (int i = 0; i < size; i++) {
            terms[size - 1 - i] = v.get(i).asV().asV();
        }
        return terms;
    }

    /**
     * Pops the terms and functor of a structure from an assembly's stack,
     * builds a structure, and pushes it.
     * <p>
     * This method expects a series of terms to lie on top of a stack, with an
     * open paren token lying underneath. If there is no '(' marker, this class
     * will throw an <code>
     * EmptyStackException</code>.
     * <p>
     * Beneath the terms of the structure, this method expects to find a token
     * whose value is the functor of the structure.
     *
     * @param a the assembly to work on
     */
    @Override
    public void workOn(Assembly<Token, TypeOrType<Axiom, Term>, QueryBuilder> a) {
        ArrayList<TypeOrType<Token, TypeOrType<Axiom, Term>>> termArrayList
                = elementsAbove(a, new Token('('));
        Term[] termArray = vectorReversedIntoTerms(termArrayList);
        Token t = a.popTok();
        a.pushVal(TypeOrType.fromV(new Structure(t.value(), termArray)));
    }

    private static final Logger LOG
            = Logger.getLogger(StructureWithTermsAssembler.class.getName());

}
