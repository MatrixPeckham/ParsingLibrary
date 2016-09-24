package com.matrixpeckham.parse.engine;

import java.util.ArrayList;
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
 * A Program is a collection of rules and facts that together form a logical
 * model.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class Program implements AxiomSource {

    /**
     *
     */
    protected ArrayList<Axiom> axioms = new ArrayList<>();

    /**
     * Create a new program with no axioms.
     */
    public Program() {
    }

    /**
     * Create a new program with the given axioms.
     *
     * @param axioms
     */
    public Program(Axiom[] axioms) {
        for (Axiom axiom : axioms) {
            addAxiom(axiom);
        }
    }

    /**
     * Adds an axiom to this program.
     *
     * @param a the axiom to add.
     */
    public final void addAxiom(Axiom a) {
        axioms.add(a);
    }

    /**
     * Appends all the axioms of another source to this one.
     *
     * @param as the source of the new axioms
     */
    public void append(AxiomSource as) {
        AxiomIterator e = as.axioms();
        while (e.hasMoreAxioms()) {
            addAxiom(e.nextAxiom());
        }
    }

    /**
     * Returns an enumeration of the axioms in this program.
     *
     * @return an enumeration of the axioms in this program.
     */
    @Override
    public AxiomIterator axioms() {
        return new ProgramEnumerator(this);
    }

    /**
     * Returns an enumeration of the axioms in this program.
     *
     * @param ignored
     * @return an enumeration of the axioms in this program.
     */
    @Override
    public AxiomIterator axioms(Structure ignored) {
        return axioms();
    }

    /**
     * Returns a string representation of this program.
     *
     * @return a string representation of this program.
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        boolean haveShownALine = false;
        Iterator<Axiom> e = axioms.iterator();
        while (e.hasNext()) {
            if (haveShownALine) {
                buf.append("\n");
            }
            buf.append(e.next().toString());
            buf.append(";");
            haveShownALine = true;
        }
        return buf.toString();
    }

    private static final Logger LOG = Logger.getLogger(Program.class.getName());

}
