package com.matrixpeckham.parse.examples.sling;

import com.matrixpeckham.parse.imperative.Command;
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
 * Pop the assembly, and push a new function that multiplies this function by
 * -1.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class NegativeAssembler extends Assembler<Token, TypeOrType<SlingFunction, Command>, SlingTarget> {

    /**
     * Push the point (-1, -1), and ask an <code>Arithmetic</code> "times"
     * object to work on the assembly. The arithmetic function will pop the
     * point and will pop whatever function was on top of the stack previously.
     * The arithmetic function will then form a multiplication function from
     * these elements and push this new function.
     *
     * @param a the assembly to work on
     */
    @Override
    public void workOn(
            Assembly<Token, TypeOrType<SlingFunction, Command>, SlingTarget> a) {
        // the term is already there, so push second term
        a.pushVal(TypeOrType.fromT(new Point(-1, -1)));
        new FunctionAssembler(new Arithmetic('*')).workOn(a);
    }

    private static final Logger LOG
            = Logger.getLogger(NegativeAssembler.class.getName());

}
