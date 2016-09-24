package com.matrixpeckham.parse.engine;

import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Signals that an ArithmeticOperator could not be evaluated.
 *
 * This happens, for example, when an evaluation refers to an uninstantiated
 * variable.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class EvaluationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs an EvaluationException with no detail message.
     *
     */
    public EvaluationException() {
        super();
    }

    /**
     * Constructs an EvaluationException with the specified detail message.
     *
     * @param detail the detail message
     */
    public EvaluationException(String detail) {
        super(detail);
    }

    private static final Logger LOG
            = Logger.getLogger(EvaluationException.class.getName());

}
