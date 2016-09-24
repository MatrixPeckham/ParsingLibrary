package com.matrixpeckham.parse.examples.mechanics;

import com.matrixpeckham.parse.parse.Alternation;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Sequence;
import com.matrixpeckham.parse.parse.tokens.Symbol;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.TokenAssembly;
import com.matrixpeckham.parse.parse.tokens.Word;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.ArrayList;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * This class shows that an alternation can, by itself, create a collection of
 * possible matches.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowAlternationSet {

    /**
     * Show that an alternation can, by itself, create a collection of possible
     * matches.
     *
     * @param args
     * @throws java.lang.Exception
     */
    public static void main(String args[]) throws Exception {

        // assignment = Word '=' assignment | Word;
        Alternation<Token, NullCloneable, NullCloneable> assignment
                = new Alternation<>();

        assignment
                .add(new Sequence<Token, NullCloneable, NullCloneable>()
                        .add(new Word<>())
                        .add(new Symbol<NullCloneable, NullCloneable>('=').
                                discard())
                        .add(assignment));
        assignment
                .add(new Word<>());

        String s = "i = j = k = l = m";

        ArrayList<Assembly<Token, NullCloneable, NullCloneable>> v
                = new ArrayList<>();
        v.add(new TokenAssembly<>(s));

        System.out.println(assignment.match(v));
    }

    private static final Logger LOG
            = Logger.getLogger(ShowAlternationSet.class.getName());

}
