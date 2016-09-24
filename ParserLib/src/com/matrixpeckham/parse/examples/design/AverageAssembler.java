package com.matrixpeckham.parse.examples.design;

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
 * This assembler updates a running average.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class AverageAssembler extends Assembler<Token, NullCloneable, RunningAverage> {

    /**
     * Increases a running average, by the length of the string on the stack.
     *
     * @param a
     */
    @Override
    public void workOn(Assembly<Token, NullCloneable, RunningAverage> a) {
        Token t = a.popTok();
        String s = t.sval();
        RunningAverage avg = a.getTarget();
        avg.add(s.length());
    }

    private static final Logger LOG
            = Logger.getLogger(AverageAssembler.class.getName());

}
