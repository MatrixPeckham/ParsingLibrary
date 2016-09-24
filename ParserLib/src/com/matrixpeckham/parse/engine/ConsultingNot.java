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
 * A ConsultingNot is a Not that has an axiom source to consult.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ConsultingNot extends Gateway {

    ConsultingStructure consultingStructure;
    /*
     * Contructs a ConsultingNot from the specified consulting
     * structure. This constructor is for use by Not.
     */

    /**
     *
     * @param consultingStructure
     */
    protected ConsultingNot(
            ConsultingStructure consultingStructure) {

        super(consultingStructure.functor, consultingStructure.terms);
        this.consultingStructure = consultingStructure;
    }

    /**
     * Returns <code>false</code> if there is any way to prove this structure.
     *
     * @return <code>false</code> if there is any way to prove this structure
     */
    @Override
    public boolean canProveOnce() {
        return !(consultingStructure.canUnify()
                && consultingStructure.resolvent.canEstablish());
    }

    /**
     * After succeeding once, unbind any variables bound during the successful
     * proof, and set the axioms to begin again at the beginning.
     */
    @Override
    protected void cleanup() {
        consultingStructure.unbind();
        consultingStructure.axioms = null;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + consultingStructure.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConsultingNot other = (ConsultingNot) obj;

        return super.equals(other) && consultingStructure.equals(
                other.consultingStructure);
    }

    /**
     * Returns a string description of this Not.
     *
     * @return a string description of this Not
     */
    @Override
    public String toString() {
        return "not " + consultingStructure;
    }

    private static final Logger LOG
            = Logger.getLogger(ConsultingNot.class.getName());

}
