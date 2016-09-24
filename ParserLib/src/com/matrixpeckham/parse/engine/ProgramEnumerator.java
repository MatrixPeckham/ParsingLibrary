package com.matrixpeckham.parse.engine;

import java.util.Iterator;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * A ProgramEnumerator returns the axioms of a program, one at a time.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ProgramEnumerator implements AxiomIterator {

    /**
     *
     */
    protected Iterator<Axiom> e;

    /**
     * Construct an enumeration of the given program.
     *
     * @param p the program to enumerate over
     *
     */
    public ProgramEnumerator(Program p) {
        e = p.axioms.iterator();
    }

    /**
     * Tests if this enumeration contains more axioms.
     *
     * @return  <code>true</code> if the program this enumeration is constructed
     * for contains more axioms, and <code>false</code> otherwise.
     */
    @Override
    public boolean hasMoreAxioms() {
        return e.hasNext();
    }

    /**
     * Returns the next axiom of this enumeration.
     *
     * @return the next axiom of this enumeration.
     */
    @Override
    public Axiom nextAxiom() {
        return e.next();
    }

    private static final Logger LOG
            = Logger.getLogger(ProgramEnumerator.class.getName());

}
