package com.matrixpeckham.parse.examples.arithmetic;

import com.matrixpeckham.parse.parse.Assembler;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.utensil.NullCloneable;
import static java.lang.Math.pow;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Pop two numbers from the stack and push the result of exponentiating the
 * lower number to the upper one.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ExpAssembler extends Assembler<Token, Double, NullCloneable> {

    /**
     * Pop two numbers from the stack and push the result of exponentiation the
     * lower number to the upper one.
     *
     * @param a the assembly whose stack to use
     */
    @Override
    public void workOn(Assembly<Token, Double, NullCloneable> a) {
        Double d1 = a.popVal();
        Double d2 = a.popVal();
        Double d3 = pow(d2, d1);
        a.pushVal(d3);
    }

    private static final Logger LOG
            = Logger.getLogger(ExpAssembler.class.getName());

}
