package com.matrixpeckham.parse.examples.mechanics;

import static com.matrixpeckham.parse.examples.arithmetic.ArithmeticParser.start;
import com.matrixpeckham.parse.parse.Parser;
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
 * This class shows that Parser.completeMatch() returns a complete match, or
 * null.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowCompleteMatch {

    /**
     * Show that </code>Parser.completeMatch()</code> returns a complete match,
     * or null.
     *
     * @param args
     */
    public static void main(String[] args)
            throws ArithmeticException {

        Parser<Token, Double, NullCloneable> p = start();

        TokenAssembly<Double, NullCloneable> ta = new TokenAssembly<>(
                "3 * 4 + 5 and more");

        System.out.println(p.bestMatch(ta));
        System.out.println(p.completeMatch(ta));
    }

    private static final Logger LOG
            = Logger.getLogger(ShowCompleteMatch.class.getName());

}
