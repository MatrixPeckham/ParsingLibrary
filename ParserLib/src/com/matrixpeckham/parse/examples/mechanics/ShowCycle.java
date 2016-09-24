package com.matrixpeckham.parse.examples.mechanics;

import com.matrixpeckham.parse.parse.Alternation;
import com.matrixpeckham.parse.parse.Sequence;
import com.matrixpeckham.parse.parse.tokens.Literal;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.TokenAssembly;
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
 * Show that a parser that contains a cycle prints itself without looping.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowCycle {

    /**
     * Show that a parser that contains a cycle prints itself without looping.
     *
     * @param args
     */
    public static void main(String args[]) {

        // ticks = "tick" | "tick" ticks;
        Alternation<Token, NullCloneable, NullCloneable> ticks
                = new Alternation<>();
        Literal<NullCloneable, NullCloneable> tick = new Literal<>("tick");

        ticks
                .add(tick)
                .add(new Sequence<Token, NullCloneable, NullCloneable>().add(
                                tick).add(ticks));

        System.out.println(
                ticks.bestMatch(
                        new TokenAssembly<>("tick tick tick tick")));

        System.out.println(ticks);

    }

    private static final Logger LOG
            = Logger.getLogger(ShowCycle.class.getName());

}
