package com.matrixpeckham.parse.examples.arithmetic;

import com.matrixpeckham.parse.parse.Assembler;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.tokens.Token;

import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Replace the top token in the stack with the token's Double value.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class NumAssembler extends Assembler<Token, Double, NullCloneable> {

    /**
     * Replace the top token in the stack with the token's Double
     * value.
     *
     * @param a the assembly whose stack to use
     */
    @Override
    public void workOn(Assembly<Token, Double, NullCloneable> a) {
        Token t = a.popTok();
        a.pushVal(new Double(t.nval()));
    }

    private static final Logger LOG
            = Logger.getLogger(NumAssembler.class.getName());

}
