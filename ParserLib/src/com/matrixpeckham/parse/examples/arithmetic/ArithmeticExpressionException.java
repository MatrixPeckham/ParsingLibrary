package com.matrixpeckham.parse.examples.arithmetic;

import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Signals that a given string is not recognizable as an arthmetic expression.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ArithmeticExpressionException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a <code>ArithmeticExpressionException</code> with no detail
     * message.
     */
    public ArithmeticExpressionException() {
        super();
    }

    /**
     * Constructs a <code>ArithmeticExpressionException</code> with the
     * specified detail message.
     *
     * @param s the detail message.
     */
    public ArithmeticExpressionException(String s) {
        super(s);
    }

    private static final Logger LOG
            = Logger.getLogger(ArithmeticExpressionException.class.getName());

}
