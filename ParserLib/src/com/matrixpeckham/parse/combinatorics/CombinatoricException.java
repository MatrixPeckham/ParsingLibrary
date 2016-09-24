package com.matrixpeckham.parse.combinatorics;

import java.util.logging.Logger;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Signals that a requested combinatoric quantity or enumeration is undefined.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class CombinatoricException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a <code>CombinatoricException</code> with no detail message.
     *
     */
    public CombinatoricException() {
        super();
    }

    /**
     * Constructs a <code>CombinatoricException</code> with the specified detail
     * message.
     *
     * @param s the detail message.
     */
    public CombinatoricException(String s) {
        super(s);
    }

    private static final Logger LOG
            = Logger.getLogger(CombinatoricException.class.getName());

}
