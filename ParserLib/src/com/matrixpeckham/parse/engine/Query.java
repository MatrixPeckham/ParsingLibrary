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
 * A Query is a dynamic rule that stands outside of a program and proves itself
 * by referring to a program.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class Query extends DynamicRule {

    /**
     * Create a query from the given structures, to prove itself against the
     * given axiom source.
     *
     * @param as the source to prove against
     * @param structures the structures to prove
     */
    public Query(AxiomSource as, Structure[] structures) {
        this(as, new Scope(structures), structures);
    }

    /**
     * Create a query from the given rule's structures, to prove itself against
     * the given axiom source.
     *
     * @param as the source to prove against
     * @param rule the rule that contains structures to prove
     */
    public Query(AxiomSource as, Rule rule) {
        this(as, rule.structures);
    }
    /*
     * This constructor ensures that the structures in the query
     * are all "provable", meaning that they are capable of
     * proving themselves. Structures cannot achieve this, but
     * they can produce consulting versions of themselves, given
     * an axiom source. Evalutations and comparisons are
     * provable in themselves, and will ignore the axiom
     * source.
     */

    /**
     *
     * @param as
     * @param scope
     * @param structures
     */
    protected Query(
            AxiomSource as, Scope scope, Structure[] structures) {

        super(
                as, scope, provableStructures(as, scope, structures));
    }

    /**
     * Create a query from the given structure, to prove itself against the
     * given axiom source.
     *
     * @param as the source to prove against
     * @param structure the structure to prove
     */
    public Query(AxiomSource as, Structure structure) {
        this(as, new Structure[]{structure});
    }

    /**
     * Create a query from the given structure, to prove itself without any
     * axiom source.
     *
     * For example new Query(new Comparison())
     *
     * @param structure the structure to prove
     */
    public Query(Structure structure) {
        this(null, new Scope(), new Structure[]{structure});
    }

    /**
     * Returns a string representation of this query.
     *
     * @return a string representation of this query.
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < structures.length; i++) {
            if (i > 0) {
                buf.append(", ");
            }
            buf.append(structures[i].toString());
        }
        return buf.toString();
    }

    private static final Logger LOG = Logger.getLogger(Query.class.getName());

}
