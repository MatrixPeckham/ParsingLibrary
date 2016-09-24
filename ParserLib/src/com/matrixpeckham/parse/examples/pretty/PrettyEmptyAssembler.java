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
 * Push a <code>TerminalNode</code> that contains the word "empty" on the
 * assembly's stack.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class PrettyEmptyAssembler extends Assembler<Token, ComponentNode, NullCloneable> {

    /**
     * Push a <code>TerminalNode</code> that contains the word "empty" on the
     * assembly's stack.
     *
     * @param a the assembly to work on
     */
    @Override
    public void workOn(Assembly<Token, ComponentNode, NullCloneable> a) {
        a.pushVal(new TerminalNode("empty"));
    }

    private static final Logger LOG
            = Logger.getLogger(PrettyEmptyAssembler.class.getName());

}
