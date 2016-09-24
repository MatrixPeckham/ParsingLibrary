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
public class ShowVariableUnification3 {

    /**
     * Show a variable unifying with a structure and then another variable.
     *
     * @param args
     */
    public static void main(String args[]) {

        // city(denver, 5280)
        Structure denver = new Structure("denver");
        Structure altitude = new Structure(Integer.valueOf(5_280));
        Structure city = new Structure(
                "city", new Structure[]{denver, altitude});

        Variable a = new Variable("A");
        Variable b = new Variable("B");

        a.unify(b); // two uninstaniated variable unify
        //...
        a.unify(city); // one later unifies
        System.out.println("a = " + a);
        System.out.println("b = " + b);

        Variable p = new Variable("P");
        p.unify(city);
        Variable q = new Variable("Q");
        q.unify(p); // an uninstantiated variable unifies with
        // an instantiated variable

        System.out.println("p = " + p);
        System.out.println("q = " + q);

        // two instantiated variables unify
        Variable x = new Variable("X");
        Variable y = new Variable("Y");
        x.unify(denver);
        y.unify(denver);

        System.out.println("Result of x.unify(y): " + x.unify(y));
    }

    private static final Logger LOG
            = Logger.getLogger(ShowVariableUnification3.class.getName());

}
