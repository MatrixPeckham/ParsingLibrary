package com.matrixpeckham.parse.examples.query;

import com.matrixpeckham.parse.engine.Axiom;
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
 * This assembler pops a term and passes it to a query builder.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class SelectTermAssembler extends Assembler<Token, TypeOrType<Axiom, Term>, QueryBuilder> {

    /**
     * Pop a term and pass it to a query builder.
     *
     * @param a
     */
    @Override
    public void workOn(Assembly<Token, TypeOrType<Axiom, Term>, QueryBuilder> a) {
        QueryBuilder b = a.getTarget();
        Term t = a.popVal().asV();
        b.addTerm(t);
    }

    private static final Logger LOG
            = Logger.getLogger(SelectTermAssembler.class.getName());

}
