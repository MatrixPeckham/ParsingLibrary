package com.matrixpeckham.parse.examples.introduction;

import com.matrixpeckham.parse.parse.Alternation;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Repetition;
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
 * Show how to create a composite parser.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowComposite {

    /**
     * Just a little demo.
     *
     * @param args
     */
    public static void main(String[] args) {

        Alternation<Token, NullCloneable, NullCloneable> adjective
                = new Alternation<>();
        adjective.add(new Literal<>("steaming"));
        adjective.add(new Literal<>("hot"));

        Sequence<Token, NullCloneable, NullCloneable> good = new Sequence<>();
        good.add(new Repetition<>(adjective));
        good.add(new Literal<>("coffee"));

        String s = "hot hot steaming hot coffee";
        Assembly<Token, NullCloneable, NullCloneable> a = new TokenAssembly<>(s);
        System.out.println(good.bestMatch(a));

    }

    private static final Logger LOG
            = Logger.getLogger(ShowComposite.class.getName());

}
