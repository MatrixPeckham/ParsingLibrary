package com.matrixpeckham.parse.examples.mechanics;

import com.matrixpeckham.parse.parse.Alternation;
import com.matrixpeckham.parse.parse.Repetition;
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
 * Show that <code>Parser.bestMatch()</code> matches a parser against an input
 * as far as possible.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowBestMatch {

    /**
     * Show that <code>Parser.bestMatch()</code> matches a parser against an
     * input as far as possible.
     *
     * @param args
     */
    public static void main(String[] args) {

        Alternation<Token, NullCloneable, NullCloneable> a = new Alternation<>();

        a.add(new Literal<>("steaming"));
        a.add(new Literal<>("hot"));

        Repetition<Token, NullCloneable, NullCloneable> adjectives
                = new Repetition<>(a);

        TokenAssembly<NullCloneable, NullCloneable> ta = new TokenAssembly<>(
                "hot hot steaming hot coffee");

        System.out.println(adjectives.bestMatch(ta));
    }

    private static final Logger LOG
            = Logger.getLogger(ShowBestMatch.class.getName());

}
