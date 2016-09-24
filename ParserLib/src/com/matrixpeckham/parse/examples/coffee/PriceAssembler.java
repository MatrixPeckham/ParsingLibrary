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
 * Pops a number and sets the target coffee's price to this number.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class PriceAssembler extends Assembler<Token, NullCloneable, Coffee> {

    /**
     * Pop a number, and set the target coffee's price to this string.
     *
     * @param a the assembly to work on
     */
    @Override
    public void workOn(Assembly<Token, NullCloneable, Coffee> a) {
        Token t = a.popTok();
        Coffee c = a.getTarget();
        c.setPrice(t.nval());
    }

    private static final Logger LOG
            = Logger.getLogger(PriceAssembler.class.getName());

}
