package com.matrixpeckham.parse.examples.logic;

import com.matrixpeckham.parse.engine.Axiom;
import com.matrixpeckham.parse.engine.Evaluation;
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
 * Pops two terms, constructs an Evaluation from these terms, and pushes it.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class EvaluationAssembler extends Assembler<Token, TypeOrType<Axiom, Term>, QueryBuilder> {

    /**
     * Pops two terms, constructs an Evaluation from these terms, and pushes it.
     *
     * @param a the assembly to work on
     */
    @Override
    public void workOn(Assembly<Token, TypeOrType<Axiom, Term>, QueryBuilder> a) {
        Term second = a.popVal().asV();
        Term first = a.popVal().asV();
        a.pushVal(TypeOrType.fromV(new Evaluation(first, second)));
    }

    private static final Logger LOG
            = Logger.getLogger(EvaluationAssembler.class.getName());

}
