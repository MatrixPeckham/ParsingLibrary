package com.matrixpeckham.parse.examples.design;

import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Repetition;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.TokenAssembly;
import com.matrixpeckham.parse.parse.tokens.Word;
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
 * Show how to use an assembler. The example shows how to calculate the average
 * length of words in a string.
 *
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowAssembler {

    /**
     * Show how to use an assembler to calculate the average length of words in
     * a string.
     *
     * @param args
     */
    public static void main(String args[]) {

        // As Polonius says, in "Hamlet"...
        String quote = "Brevity is the soul of wit";

        Assembly<Token, NullCloneable, RunningAverage> in = new TokenAssembly<>(
                quote);
        in.setTarget(new RunningAverage());

        Word<NullCloneable, RunningAverage> w = new Word<>();
        w.setAssembler(new AverageAssembler());
        Parser<Token, NullCloneable, RunningAverage> p = new Repetition<>(w);

        Assembly<Token, NullCloneable, RunningAverage> out = p.completeMatch(in);

        RunningAverage avg = out.getTarget();
        System.out.println(
                "Average word length: " + avg.average());
    }

    private static final Logger LOG
            = Logger.getLogger(ShowAssembler.class.getName());

}
