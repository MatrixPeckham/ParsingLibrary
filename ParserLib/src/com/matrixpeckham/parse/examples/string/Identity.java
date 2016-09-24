package com.matrixpeckham.parse.examples.string;

import java.util.logging.Logger;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * An Identity function returns the input string unchanged, in response to the
 * <code>f()</code> method. This allows other classes to always expect a source
 * function to wrap their functions around.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class Identity extends StringFunction {

    /**
     * Construct an identity function.
     */
    public Identity() {
        super(null);
    }

    /**
     * Return the given string.
     * @return 
     */
    @Override
    public String f(String s) {
        return s;
    }

    private static final Logger LOG = Logger.getLogger(Identity.class.getName());
}
