package com.matrixpeckham.parse.examples.introduction;

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
 * Show what counts as a number.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowNums {

    /**
     * Just a little demo.
     *
     * @param args
     */
    public static void main(String[] args) {
        String s = "12 12.34 .1234 1234e-2";
        TokenAssembly<NullCloneable, NullCloneable> a = new TokenAssembly<>(s);
        while (a.hasNext()) {
            System.out.println(a.next());
        }
    }

    private static final Logger LOG = Logger.getLogger(ShowNums.class.getName());

}
