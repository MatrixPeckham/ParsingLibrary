package com.matrixpeckham.parse.examples.introduction;

import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.TokenAssembly;
import com.matrixpeckham.parse.parse.tokens.Word;
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
 * Show how to recognize terminals in a string.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowTerminal {

    /**
     * Just a little demo.
     *
     * @param args
     */
    public static void main(String[] args) {
        String s = "steaming hot coffee";
        Assembly<Token, NullCloneable, NullCloneable> a = new TokenAssembly<>(s);
        Parser<Token, NullCloneable, NullCloneable> p = new Word<>();

        while (true) {
            a = p.bestMatch(a);
            if (a == null) {
                break;
            }
            System.out.println(a);
        }

    }

    private static final Logger LOG
            = Logger.getLogger(ShowTerminal.class.getName());

}
