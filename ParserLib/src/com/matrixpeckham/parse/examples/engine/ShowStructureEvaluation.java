package com.matrixpeckham.parse.examples.engine;

import com.matrixpeckham.parse.engine.Atom;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose, 
 * including the implied warranty of merchantability.
 */
/**
 * Show the evaluation of a structure.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowStructureEvaluation {

    /**
     * Show the evaluation of a structure.
     *
     * @param args
     */
    public static void main(String args[]) {
        Atom a = new Atom("maine");
        Object o = a.eval();
        System.out.println("" + a + o);
    }

    private static final Logger LOG
            = Logger.getLogger(ShowStructureEvaluation.class.getName());

}
