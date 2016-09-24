package com.matrixpeckham.parse.examples.mechanics;

import static com.matrixpeckham.parse.examples.robot.RobotParser.start;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Show how a moderately complex composite parser prints itself.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowToString2 {

    /**
     * Show how a moderately complex composite parser prints itself.
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(start());
    }

    private static final Logger LOG
            = Logger.getLogger(ShowToString2.class.getName());

}
