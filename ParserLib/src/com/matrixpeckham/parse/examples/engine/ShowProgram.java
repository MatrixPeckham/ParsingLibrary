package com.matrixpeckham.parse.examples.engine;

import com.matrixpeckham.parse.engine.Fact;
import com.matrixpeckham.parse.engine.Program;
import com.matrixpeckham.parse.engine.Query;
import com.matrixpeckham.parse.engine.Structure;
import com.matrixpeckham.parse.engine.Term;
import com.matrixpeckham.parse.engine.Variable;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability. 
 */
/**
 * Show the construction and use of a simple program.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowProgram {

    /**
     * Return a small database of cities and their altitudes.
     *
     * @return
     */
    public static Program altitudes() {
        Fact[] facts = new Fact[]{
            new Fact("city", "abilene", Integer.valueOf(1_718)),
            new Fact("city", "addis ababa", Integer.valueOf(8_000)),
            new Fact("city", "denver", Integer.valueOf(5_280)),
            new Fact("city", "flagstaff", Integer.valueOf(6_970)),
            new Fact("city", "jacksonville", Integer.valueOf(8)),
            new Fact("city", "leadville", Integer.valueOf(10_200)),
            new Fact("city", "madrid", Integer.valueOf(1_305)),
            new Fact("city", "richmond", Integer.valueOf(19)),
            new Fact("city", "spokane", Integer.valueOf(1_909)),
            new Fact("city", "wichita", Integer.valueOf(1_305))
        };

        Program p = new Program();
        for (Fact fact : facts) {
            p.addAxiom(fact);
        }
        return p;

    }

    /**
     * Show the construction and use of a simple program.
     *
     * @param args
     */
    public static void main(String[] args) {
        Program p = altitudes();

        Variable name = new Variable("Name");
        Variable height = new Variable("Height");
        Structure s = new Structure(
                "city", new Term[]{name, height});
        Query q = new Query(p, s);

        while (q.canFindNextProof()) {
            System.out.println(
                    name + " is about " + height + " feet above sea level.");
        }

    }

    private static final Logger LOG
            = Logger.getLogger(ShowProgram.class.getName());

}
