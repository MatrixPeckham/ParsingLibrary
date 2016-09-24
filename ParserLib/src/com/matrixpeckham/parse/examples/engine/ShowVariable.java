package com.matrixpeckham.parse.examples.engine;

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
 * This class shows some variables.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowVariable {

    /**
     * Show some variables.
     *
     * @param args
     */
    public static void main(String args[]) {

        Variable name = new Variable("Name");
        Variable alt = new Variable("Altitude");
        Structure vCity = new Structure(
                "city",
                new Term[]{name, alt});
        System.out.println(vCity);
    }

    private static final Logger LOG
            = Logger.getLogger(ShowVariable.class.getName());

}
