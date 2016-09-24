package com.matrixpeckham.parse.examples.pretty;

import com.matrixpeckham.parse.parse.Assembler;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.logging.Logger;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Replace a <code>Token</code> object on the stack with a
 * <code>TerminalNode</code> that holds the token's value.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class PrettyTerminalAssembler extends Assembler<Token, ComponentNode, NullCloneable> {

    /**
     * Replace a <code>Token</code> object on the stack with a
     * <code>TerminalNode</code> that holds the token's value.
     *
     * @param a the assembly to work on
     */
    @Override
    public void workOn(Assembly<Token, ComponentNode, NullCloneable> a) {
        Token t = a.popTok();
        a.pushVal(new TerminalNode(t.value().toString()));
    }

    private static final Logger LOG
            = Logger.getLogger(PrettyTerminalAssembler.class.getName());

}
