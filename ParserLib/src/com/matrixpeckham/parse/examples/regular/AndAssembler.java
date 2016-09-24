package com.matrixpeckham.parse.examples.regular;

import com.matrixpeckham.parse.parse.Assembler;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Sequence;
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
 * Pop two Parsers from the stack and push a new <code>
 * Sequence</code> of them.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class AndAssembler extends Assembler<Character, Parser<Character, NullCloneable, NullCloneable>, NullCloneable> {

    /**
     * Pop two parsers from the stack and push a new <code>Sequence</code> of
     * them.
     *
     * @param a the assembly whose stack to use
     */
    @Override
    public void workOn(
            Assembly<Character, Parser<Character, NullCloneable, NullCloneable>, NullCloneable> a) {
        Parser<Character, NullCloneable, NullCloneable> top = a.popVal();
        Sequence<Character, NullCloneable, NullCloneable> s = new Sequence<>();
        s.add(a.popVal());
        s.add(top);
        a.pushVal(s);
    }

    private static final Logger LOG
            = Logger.getLogger(AndAssembler.class.getName());

}
