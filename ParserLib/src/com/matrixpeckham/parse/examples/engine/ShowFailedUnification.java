package com.matrixpeckham.parse.examples.engine;

import com.matrixpeckham.parse.engine.Structure;
import com.matrixpeckham.parse.engine.Term;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose, 
 * including the implied warranty of merchantability.
 */
/**
 * This class shows two structures that fail to unify.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowFailedUnification {

    /**
     * Show two structures that fail to unify.
     *
     * @param args
     */
    public static void main(String args[]) {
        Structure city1 = new Structure("city", new Term[]{
            new Structure("denver"),
            new Structure(Integer.valueOf(5280))});

        Structure city2 = new Structure("city", new Term[]{
            new Structure("jacksonville"),
            new Structure(Integer.valueOf(8))});

        System.out.println(city1);
        System.out.println(city2);
        System.out.println(city1.unify(city2));

    }

    private static final Logger LOG
            = Logger.getLogger(ShowFailedUnification.class.getName());

}
