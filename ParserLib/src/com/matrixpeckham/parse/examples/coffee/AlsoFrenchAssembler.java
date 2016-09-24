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
 * This assembler sets a target coffee object boolean that indicates the type of
 * coffee also comes in a french roast.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class AlsoFrenchAssembler extends Assembler<Token, NullCloneable, Coffee> {

    /**
     * Set a target coffee object's boolean to indicate that this type of coffee
     * also comes in a french roast.
     *
     * @param a the assembly to work on
     */
    @Override
    public void workOn(Assembly<Token, NullCloneable, Coffee> a) {
        Coffee c = a.getTarget();
        c.setAlsoOfferFrench(true);
    }

    private static final Logger LOG
            = Logger.getLogger(AlsoFrenchAssembler.class.getName());

}
