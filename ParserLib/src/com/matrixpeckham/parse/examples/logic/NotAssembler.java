package com.matrixpeckham.parse.examples.logic;

import com.matrixpeckham.parse.engine.Axiom;
import com.matrixpeckham.parse.engine.Not;
import com.matrixpeckham.parse.engine.Structure;
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
 * Pops a structure from the top of the stack and pushes a Not version of it.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class NotAssembler extends Assembler<Token, TypeOrType<Axiom, Term>, QueryBuilder> {

    /**
     * Pops a structure from the top of the stack and pushes a Not version of
     * it.
     *
     * @param a the assembly to work on
     */
    @Override
    public void workOn(Assembly<Token, TypeOrType<Axiom, Term>, QueryBuilder> a) {
        Term t = a.popVal().asV();
        if (t instanceof Structure) {
            Structure s = (Structure) t;
            a.pushVal(TypeOrType.fromV(new Not(s)));
        } else {//Shouldn't happen cause structure succeeded to get here
            throw new LogikusException(
                    "Not requires a structure, did not have one");
        }
    }

    private static final Logger LOG
            = Logger.getLogger(NotAssembler.class.getName());

}
