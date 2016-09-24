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
 * This assembler pops a coffee's name from an assembly's stack, and sets the
 * assembly's target to be a new Coffee object with this name.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class NameAssembler extends Assembler<Token, NullCloneable, Coffee> {

    /**
     * Pop a coffee's name from an assembly's stack, and set the assembly's
     * target to be a new Coffee object with this name.
     *
     * @param a the assembly to work on
     */
    @Override
    public void workOn(Assembly<Token, NullCloneable, Coffee> a) {
        Coffee c = new Coffee();
        Token t = a.popTok();
        c.setName(t.sval().trim());
        a.setTarget(c);
    }

    private static final Logger LOG
            = Logger.getLogger(NameAssembler.class.getName());

}
