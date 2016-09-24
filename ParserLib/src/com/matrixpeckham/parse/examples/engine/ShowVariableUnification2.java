package com.matrixpeckham.parse.examples.engine;

import com.matrixpeckham.parse.engine.Structure;
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
 * Show a variable unifying with a structure and then another variable.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowVariableUnification2 {

    /**
     * Show a variable unifying with a structure and then another variable.
     *
     * @param args
     */
    public static void main(String args[]) {

        Variable x = new Variable("X");
        Variable y = new Variable("Y");
        Structure denver = new Structure("denver");

        x.unify(denver);
        x.unify(y);

        System.out.println("X = " + x);
        System.out.println("Y = " + y);
    }

    private static final Logger LOG
            = Logger.getLogger(ShowVariableUnification2.class.getName());

}
