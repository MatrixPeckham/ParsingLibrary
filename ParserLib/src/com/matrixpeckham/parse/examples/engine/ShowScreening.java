package com.matrixpeckham.parse.examples.engine;

import com.matrixpeckham.parse.engine.Fact;
import com.matrixpeckham.parse.engine.Program;
import com.matrixpeckham.parse.engine.Query;
import com.matrixpeckham.parse.engine.Structure;
import com.matrixpeckham.parse.engine.Term;
import com.matrixpeckham.parse.engine.Variable;
import static com.matrixpeckham.parse.examples.engine.ShowProgram.altitudes;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose, 
 * including the implied warranty of merchantability.
 */
/**
 * Show how a query can screen for certain facts.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowScreening {

    /**
     * Show the construction and use of a simple program.
     *
     * @param args
     */
    public static void main(String[] args) {
        Program p = altitudes();

        Variable name = new Variable("Name");
        Variable height = new Variable("Height");

        Query q1 = new Query(p,
                new Structure("city", new Term[]{
                    new Fact("denver"), height}));

        if (q1.canFindNextProof()) {
            System.out.println(
                    "Denver is " + height + " feet high.");
        }

        Query q2 = new Query(p,
                new Structure("city", new Term[]{
                    name, new Fact(Integer.valueOf(10_200))}));

        if (q2.canFindNextProof()) {
            System.out.println(
                    name + " is 10200 feet high.");
        }

    }

    private static final Logger LOG
            = Logger.getLogger(ShowScreening.class.getName());

}
