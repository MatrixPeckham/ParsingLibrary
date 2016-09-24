package com.matrixpeckham.parse.examples.logic;

import com.matrixpeckham.parse.engine.Axiom;
import static com.matrixpeckham.parse.engine.Structure.emptyList;
import static com.matrixpeckham.parse.engine.Structure.list;
import com.matrixpeckham.parse.engine.Term;
import static com.matrixpeckham.parse.examples.logic.StructureWithTermsAssembler.vectorReversedIntoTerms;
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
 * Pops the terms of a list from an assembly's stack, builds the list, and
 * pushes it.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ListAssembler extends Assembler<Token, TypeOrType<Axiom, Term>, QueryBuilder> {

    /**
     * Pops the terms of a list from an assembly's stack, builds the list, and
     * pushes it.
     * <p>
     * This method expects a series of terms to lie on top of a stack, with an
     * open bracket token lying beneath. If there is no '[' marker, this class
     * will throw an <code>
     * EmptistackException</code>.
     *
     * @param a the assembly to work on
     */
    @Override
    public void workOn(Assembly<Token, TypeOrType<Axiom, Term>, QueryBuilder> a) {
        Token fence = new Token('[');
        ArrayList<TypeOrType<Token, TypeOrType<Axiom, Term>>> termArrayList
                = elementsAbove(a, fence);
        Term[] termArray = vectorReversedIntoTerms(
                termArrayList);

        if (termArray.length == 0) {
            a.pushVal(TypeOrType.fromBoth(emptyList, emptyList));
        } else {
            a.pushVal(TypeOrType.fromV(list(termArray)));
        }
    }

    private static final Logger LOG
            = Logger.getLogger(ListAssembler.class.getName());

}
