package com.matrixpeckham.parse.examples.mechanics;

import com.matrixpeckham.parse.parse.Alternation;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Repetition;
import com.matrixpeckham.parse.parse.tokens.Literal;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.TokenAssembly;
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
 * This class shows the basics of using an alternation.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowAlternationBasic {

    /**
     * Just show the basics of alternation.
     *
     * @param args
     */
    public static void main(String args[]) {

        Alternation<Token, NullCloneable, NullCloneable> a = new Alternation<>();
        a.add(new Literal<>("steaming"));
        a.add(new Literal<>("hot"));

        ArrayList<Assembly<Token, NullCloneable, NullCloneable>> v
                = new ArrayList<>();
        v.add(
                new TokenAssembly<>("hot hot steaming hot coffee"));

        System.out.println(
                "a match: \n" + a.match(v));

        System.out.println(
                "a* match: \n" + new Repetition<>(a).match(v));
    }

    private static final Logger LOG
            = Logger.getLogger(ShowAlternationBasic.class.getName());

}
