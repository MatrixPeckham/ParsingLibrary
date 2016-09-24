package com.matrixpeckham.parse.examples.query;

import com.matrixpeckham.parse.engine.Axiom;
import com.matrixpeckham.parse.engine.Comparison;
import com.matrixpeckham.parse.engine.ComparisonTerm;
import com.matrixpeckham.parse.engine.Term;
import com.matrixpeckham.parse.parse.Assembler;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.utensil.TypeOrType;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * This assembler pops a comparison term, an operator, and another comparison
 * term. It builds the comparison and passes the comparison to the query builder
 * that this assembler expects to find in the assembly's target.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ComparisonAssembler extends Assembler<Token, TypeOrType<Axiom, Term>, QueryBuilder> {

    /**
     * Pops a comparison term, an operator, and another comparison term. Builds
     * the comparison and passes the comparison to the query builder that this
     * assembler expects to find in the assembly's target.
     *
     * @param a
     */
    @Override
    public void workOn(Assembly<Token, TypeOrType<Axiom, Term>, QueryBuilder> a) {
        ComparisonTerm second = (ComparisonTerm) a.popVal().asV();
        Token t = a.popTok();
        ComparisonTerm first = (ComparisonTerm) a.popVal().asV();
        QueryBuilder b = a.getTarget();
        b.addComparison(new Comparison(t.sval(), first, second));
    }

    private static final Logger LOG
            = Logger.getLogger(ComparisonAssembler.class.getName());

}
