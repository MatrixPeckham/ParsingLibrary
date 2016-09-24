package com.matrixpeckham.parse.examples.engine;

import com.matrixpeckham.parse.engine.Fact;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose, 
 * including the implied warranty of merchantability.
 */
/**
 * This class shows the construction of a couple of facts.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowFacts {

    /**
     * Shows the construction of a couple of facts.
     *
     * @param args
     */
    public static void main(String[] args) {
        Fact d = new Fact(
                "city",
                new Fact[]{
                    new Fact("denver"),
                    new Fact(Integer.valueOf(5_280))});

        Fact j = new Fact(
                "city", "jacksonville", Integer.valueOf(8));

        System.out.println(d + "\n" + j);
    }

    private static final Logger LOG
            = Logger.getLogger(ShowFacts.class.getName());

}
