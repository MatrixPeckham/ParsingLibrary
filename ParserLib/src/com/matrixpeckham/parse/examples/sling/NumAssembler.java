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
 * Pops a number token, and pushes a number function.
 * <p>
 * A design principle for this package is that "everything" is a two-dimensional
 * function of time. Thus if the stack contains, say, a token that is just the
 * number 3, this assembler will push a new <code>Cartesian</code> function (t,
 * 3). This allows, for example, a Sling statement like <code>plot 3;</code> to
 * be meaningful. In this case, the Sling design principles will use 3 as the y
 * component of a function, and will use time as the x component. The plot will
 * be of a line segment from (0, 3) to (1, 3), since time varies from 0 to 1
 * during the course of a plot.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class NumAssembler extends Assembler<Token, TypeOrType<SlingFunction, Command>, SlingTarget> {

    /**
     * Pop a number n, and push the function (t, n).
     *
     * @param a the assembly to work on
     */
    @Override
    public void workOn(
            Assembly<Token, TypeOrType<SlingFunction, Command>, SlingTarget> a) {
        Token t = a.popTok();
        a.pushVal(TypeOrType.fromT(
                new Cartesian(new T(), new Point(0, t.nval()))));
    }

    private static final Logger LOG
            = Logger.getLogger(NumAssembler.class.getName());

}
