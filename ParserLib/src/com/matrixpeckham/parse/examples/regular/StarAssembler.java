package com.matrixpeckham.parse.examples.regular;

import com.matrixpeckham.parse.parse.Assembler;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Repetition;
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
 * Pop a parser from the stack and push a new <code>
 * Repetition</code> of it.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class StarAssembler extends Assembler<Character, Parser<Character, NullCloneable, NullCloneable>, NullCloneable> {

    /**
     * Pop a parser from the stack and push a new <code>
     * Repetition</code> of it.
     *
     * @param a the assembly whose stack to use
     */
    @Override
    public void workOn(
            Assembly<Character, Parser<Character, NullCloneable, NullCloneable>, NullCloneable> a) {
        a.pushVal(new Repetition<>(a.popVal()));
    }

    private static final Logger LOG
            = Logger.getLogger(StarAssembler.class.getName());

}
