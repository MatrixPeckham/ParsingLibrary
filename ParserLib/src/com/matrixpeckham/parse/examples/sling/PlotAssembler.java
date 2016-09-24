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
 * Pops a function, and pushes an <code>AddFunctionCommand
 * </code> object. This command, when executed, will create a renderable
 * function. The renderable function includes the function that we pop now and
 * the value of the "nLine" variable.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class PlotAssembler extends Assembler<Token, TypeOrType<SlingFunction, Command>, SlingTarget> {

    /**
     * Pop a function, and push a command that will, at execution time, create a
     * renderable function. The renderable function includes the popped function
     * and the value of the "nLine" variable.
     *
     * @param a the assembly to work on
     */
    @Override
    public void workOn(
            Assembly<Token, TypeOrType<SlingFunction, Command>, SlingTarget> a) {
        SlingTarget target = a.getTarget();
        SlingFunction f = a.popVal().asT();
        a.pushVal(TypeOrType.fromV(new AddFunctionCommand(
                target.getRenderables(), f, target.nLine())));
    }

    private static final Logger LOG
            = Logger.getLogger(PlotAssembler.class.getName());

}
