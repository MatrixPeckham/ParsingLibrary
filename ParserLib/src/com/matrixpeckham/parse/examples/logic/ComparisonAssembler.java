package com.matrixpeckham.parse.examples.logic;

import com.matrixpeckham.parse.engine.Axiom;
import com.matrixpeckham.parse.engine.Comparison;
import com.matrixpeckham.parse.engine.ComparisonTerm;
import com.matrixpeckham.parse.engine.Term;
import com.matrixpeckham.parse.examples.query.QueryBuilder;
import com.matrixpeckham.parse.parse.Assembler;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.utensil.TypeOrType;
import java.util.logging.Logger;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Pops two comparison terms and an operator, builds the comparison, and pushes
 * it.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ComparisonAssembler extends Assembler<Token, TypeOrType<Axiom, Term>, QueryBuilder> {

    /**
     * Pops two comparison terms and an operator, builds the comparison, and
     * pushes it.
     *
     * @param a the assembly to work on
     */
    @Override
    public void workOn(Assembly<Token, TypeOrType<Axiom, Term>, QueryBuilder> a) {
        Term secondTerm = a.popVal().asV();
        Term firstTerm = a.popVal().asV();
        if (firstTerm instanceof ComparisonTerm
                && secondTerm instanceof ComparisonTerm) {
            ComparisonTerm second = (ComparisonTerm) secondTerm;
            ComparisonTerm first = (ComparisonTerm) firstTerm;
            Token t = a.popTok();
            a.pushVal(TypeOrType.fromV(new Comparison(t.sval(), first, second)));
        } else {//This shouldn't happen
            throw new LogikusException(
                    "Comparison requires two comparison terms didn't find them");
        }
    }

    private static final Logger LOG
            = Logger.getLogger(ComparisonAssembler.class.getName());

}
