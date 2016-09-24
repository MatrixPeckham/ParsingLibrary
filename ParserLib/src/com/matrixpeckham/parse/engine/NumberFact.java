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
 * A NumberFact is a fact with a Number as its functor.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class NumberFact extends Atom
        implements ArithmeticTerm {

    /**
     * Contructs a number fact from the double.
     *
     * @param d a number to wrap as an atom
     */
    public NumberFact(double d) {
        this(Double.valueOf(d));
    }

    /**
     * Contructs a number fact from the Numer.
     *
     * @param functor a number to wrap as an atom
     */
    public NumberFact(Number functor) {
        super(functor);
    }

    private static final Logger LOG
            = Logger.getLogger(NumberFact.class.getName());

}
