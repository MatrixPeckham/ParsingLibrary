package com.matrixpeckham.parse.examples.introduction;

import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Repetition;
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
 * Show that a <code>Repetition</code> object creates multiple interpretations.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowRepetition {

    /**
     * Just a little demo.
     *
     * @param args
     */
    public static void main(String[] args) {
        String s = "steaming hot coffee";
        Assembly<Token, NullCloneable, NullCloneable> a = new TokenAssembly<>(s);
        Parser<Token, NullCloneable, NullCloneable> p
                = new Repetition<Token, NullCloneable, NullCloneable>(
                        new Word<>());

        ArrayList<Assembly<Token, NullCloneable, NullCloneable>> v
                = new ArrayList<>();
        v.add(a);

        System.out.println(p.match(v));
    }

    private static final Logger LOG
            = Logger.getLogger(ShowRepetition.class.getName());

}
