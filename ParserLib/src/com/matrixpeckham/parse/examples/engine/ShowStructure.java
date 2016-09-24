package com.matrixpeckham.parse.examples.engine;

import com.matrixpeckham.parse.engine.Structure;
import java.util.logging.Logger;

/* 
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * This class shows a simple structure.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowStructure {

    /**
     * Show a simple structure.
     *
     * @param args
     */
    public static void main(String args[]) {
        Structure denver = new Structure("denver");
        Structure altitude = new Structure(Integer.valueOf(5_280));
        Structure city = new Structure(
                "city", new Structure[]{denver, altitude});
        System.out.println(city);
    }

    private static final Logger LOG
            = Logger.getLogger(ShowStructure.class.getName());

}
