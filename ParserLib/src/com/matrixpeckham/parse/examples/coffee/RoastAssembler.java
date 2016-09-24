package com.matrixpeckham.parse.examples.coffee;

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
 * This assembler pops a string, and sets the target coffee's roast to this
 * string.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class RoastAssembler extends Assembler<Token, NullCloneable, Coffee> {

    /**
     * Pop a string, and set the target coffee's roast to this string.
     *
     * @param a the assembly to work on
     */
    @Override
    public void workOn(Assembly<Token, NullCloneable, Coffee> a) {
        Token t = a.popTok();
        Coffee c = a.getTarget();
        c.setRoast(t.sval().trim());
    }

    private static final Logger LOG
            = Logger.getLogger(RoastAssembler.class.getName());

}
