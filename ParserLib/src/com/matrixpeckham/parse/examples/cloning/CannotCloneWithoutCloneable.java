package com.matrixpeckham.parse.examples.cloning;

import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 *
 * This class will compile but not run, since the class does not implement
 * Cloneable.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class CannotCloneWithoutCloneable {

    /**
     * Just a demo, this will compile but not run.
     *
     * @param args
     * @throws java.lang.Exception
     */
    public static void main(String args[]) throws Exception {
        CannotCloneWithoutCloneable ccwc = new CannotCloneWithoutCloneable();

        ccwc.clone(); // will throw an exception at run time
    }

    public void notACloneMethod() {
    }

    private static final Logger LOG
            = Logger.getLogger(CannotCloneWithoutCloneable.class.getName());

}
