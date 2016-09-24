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
 * This class looks up a variable by name, using the word on an assembly's
 * stack, and pushes a <code>Variable</code> of that name.
 * <p>
 * This class expects an assembly's target to be a <code>
 * SlingPlot</code> object. The target has a "scope", which is a collection of
 * variables organized by name. When this assembler works on an assembly, it
 * pops a name from the stack, looks up a variable in the scope using the name,
 * and pushes the variable onto the stack. This lookup creates the variable in
 * the scope if the scope does not already contain a variable of that name.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class VariableAssembler extends Assembler<Token, TypeOrType<SlingFunction, Command>, SlingTarget> {

    /**
     * Pop the name of a variable, lookup the variable in the target's scope,
     * and push the variable.
     *
     * @param a
     */
    @Override
    public void workOn(
            Assembly<Token, TypeOrType<SlingFunction, Command>, SlingTarget> a) {
        SlingTarget target = a.getTarget();
        Token t = a.popTok();
        a.pushVal(TypeOrType.fromT(target.lookup(t.sval())));
    }

    private static final Logger LOG
            = Logger.getLogger(VariableAssembler.class.getName());

}
