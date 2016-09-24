package com.matrixpeckham.parse.examples.engine;

import com.matrixpeckham.parse.engine.Atom;
import com.matrixpeckham.parse.engine.Comparison;
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
 * Show a two-structure query.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowTwoStructureQuery {

    /**
     * Show a two-structure query.
     *
     * @param args
     */
    public static void main(String[] args) {
        Program p = altitudes(); // from above

        Variable name = new Variable("Name");
        Variable alt = new Variable("Alt");
        Atom fiveThou = new Atom(Integer.valueOf(5_000));

        Query q = new Query(p, new Structure[]{
            new Structure("city", new Term[]{name, alt}),
            new Comparison(">", alt, fiveThou)});

        while (q.canFindNextProof()) {
            System.out.println(name + " " + alt);
        }
    }

    private static final Logger LOG
            = Logger.getLogger(ShowTwoStructureQuery.class.getName());

}
