package com.matrixpeckham.parse.examples.minimath;

import com.matrixpeckham.parse.parse.Repetition;
import com.matrixpeckham.parse.parse.Sequence;
import com.matrixpeckham.parse.parse.tokens.Num;
import com.matrixpeckham.parse.parse.tokens.Symbol;
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
 * Show how to build a parser to recognize elements of the language "Minimath",
 * whose rules are:
 *
 * <blockquote><pre>
 *     e = Num m*;
 *     m = '-' Num;
 * </pre></blockquote>
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class MinimathRecognize {

    /**
     * Just a little demo.
     *
     * @param args
     */
    public static void main(String args[]) {
        Sequence<Token, NullCloneable, NullCloneable> e = new Sequence<>();

        e.add(new Num<>());

        Sequence<Token, NullCloneable, NullCloneable> m = new Sequence<>();
        m.add(new Symbol<>('-'));
        m.add(new Num<>());

        e.add(new Repetition<>(m));

        System.out.println(
                e.completeMatch(
                        new TokenAssembly<>("25 - 16 - 9")));
    }

    private static final Logger LOG
            = Logger.getLogger(MinimathRecognize.class.getName());

}
