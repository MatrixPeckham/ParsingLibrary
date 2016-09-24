package com.matrixpeckham.parse.examples.sling;

import com.matrixpeckham.parse.engine.BooleanTerm;
import com.matrixpeckham.parse.imperative.Command;
import com.matrixpeckham.parse.imperative.CommandSequence;
import com.matrixpeckham.parse.imperative.ForCommand;
import com.matrixpeckham.parse.parse.Assembler;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.utensil.TypeOrType;
import java.util.ArrayList;
import java.util.logging.Logger;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Builds a "for" command from elements on the stack, which should be: a
 * variable, a "from" function, a "to" function, a '{' token, and a series of
 * commands.
 * <p>
 * This class uses the curly brace as a fence, popping commands above it and
 * creating a composite command from these commands. This composite command is
 * the body of the "for" loop. This assembler pops the "from" and "to" functions
 * and the variable, constructs a <code>ForCommand</code> object, and pushes it.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ForAssembler extends Assembler<Token, TypeOrType<SlingFunction, Command>, SlingTarget> {
    /*
     * Pop the elements on the stack above a '{' token, and
     * build a composite command from them.
     */

    /**
     *
     * @param a
     * @return
     */
    protected static CommandSequence popCommandSequence(
            Assembly<Token, TypeOrType<SlingFunction, Command>, SlingTarget> a) {

        Token fence = new Token('{');
        ArrayList<TypeOrType<Token, TypeOrType<SlingFunction, Command>>> statementArrayList
                = elementsAbove(a, fence);
        CommandSequence cs = new CommandSequence();
        int n = statementArrayList.size();
        for (int i = n - 1; i >= 0; i--) {
            Command c = statementArrayList.get(i).asV().asV();
            cs.addCommand(c);
        }
        return cs;
    }

    /**
     * Pop the elements of a "for" loop, construct a <code>
     * ForCommand</code>, and push it.
     *
     * @param a the assembly to work on
     */
    @Override
    public void workOn(
            Assembly<Token, TypeOrType<SlingFunction, Command>, SlingTarget> a) {
        // pop the elements of a "for" loop
        CommandSequence cs = popCommandSequence(a);
        SlingFunction to = a.popVal().asT();
        SlingFunction from = a.popVal().asT();
        Variable v = (Variable) a.popVal().asT();

        // create and push a ForCommand
        Command setup = new AssignFunctionCommand(v, from);
        BooleanTerm condition = new FunctionComparison("<=", v, to);
        SlingFunction step = new Point(1, 1);
        Arithmetic plus1 = new Arithmetic('+', v, step);
        Command endCommand = new AssignFunctionCommand(v, plus1);
        a.pushVal(TypeOrType.fromV(new ForCommand(setup, condition, endCommand,
                cs)));
    }

    private static final Logger LOG
            = Logger.getLogger(ForAssembler.class.getName());

}
