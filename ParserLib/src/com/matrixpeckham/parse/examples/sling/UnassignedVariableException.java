package com.matrixpeckham.parse.examples.sling;

import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Signals that a given string is not the name of a known variable.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class UnassignedVariableException
        extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a <code>UnrecognizedVariableException</code> with no detail
     * message.
     */
    public UnassignedVariableException() {
        super();
    }

    /**
     * Constructs a <code>UnrecognizedVariableException</code> with the
     * specified detail message.
     *
     * @param s the detail message.
     */
    public UnassignedVariableException(String s) {
        super(s);
    }

    private static final Logger LOG
            = Logger.getLogger(UnassignedVariableException.class.getName());

}
