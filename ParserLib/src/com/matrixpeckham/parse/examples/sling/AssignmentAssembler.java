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
 * Pops a function and a variable, constructs an <code>
 * AssignFunctionCommand</code> from these terms, and pushes the command. For
 * example, after parsing:
 *
 * <blockquote><pre>
 *     y = sin(x);
 * </pre></blockquote>
 *
 * this assembler expects the variable <code>y</code> and the function
 * <code>sin(x)</code> to be on the stack, and constructs a command from these
 * objects.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class AssignmentAssembler extends Assembler<Token, TypeOrType<SlingFunction, Command>, SlingTarget> {

    /**
     * Pops a function and a variable, constructs an <code>
     * AssignFunctionCommand</code> from these terms, and pushes the command.
     *
     * @param a the assembly to work on
     */
    @Override
    public void workOn(
            Assembly<Token, TypeOrType<SlingFunction, Command>, SlingTarget> a) {
        SlingFunction f = a.popVal().asT();
        Variable v = (Variable) a.popVal().asT();
        a.pushVal(TypeOrType.fromV(new AssignFunctionCommand(v, f)));
    }

    private static final Logger LOG
            = Logger.getLogger(AssignmentAssembler.class.getName());

}
