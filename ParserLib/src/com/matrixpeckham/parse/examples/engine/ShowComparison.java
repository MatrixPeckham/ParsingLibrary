package com.matrixpeckham.parse.examples.engine;

import com.matrixpeckham.parse.engine.Atom;
import com.matrixpeckham.parse.engine.Comparison;
import com.matrixpeckham.parse.engine.Query;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Show a couple of comparisons.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowComparison {

    /**
     * Show a couple of comparisons.
     *
     * @param args
     */
    public static void main(String[] args) {
        Atom alt1 = new Atom(Integer.valueOf(5_280));
        Atom alt2 = new Atom(Integer.valueOf(19));

        Query q1 = new Query(
                null, // no axiom source
                new Comparison(">", alt1, alt2));

        System.out.println(q1 + " : " + q1.canFindNextProof());

        Query q2 = new Query(
                null,
                new Comparison(">",
                        new Atom("denver"),
                        new Atom("richmond")));

        System.out.println(q2 + " : " + q2.canFindNextProof());
    }

    private static final Logger LOG
            = Logger.getLogger(ShowComparison.class.getName());

}
