package com.matrixpeckham.parse.engine;

import static com.matrixpeckham.parse.engine.Unification.empty;

import java.util.logging.Logger;

/**
 * A Fact is a Structure that contains only other Facts.
 * <p>
 * For example,
 * <blockquote><pre>
 *     Fact s = new Fact(
 *         "starred",
 *         new Fact[]{
 *             new Fact("James Cagney"),
 *             new Fact("Yankee Doodle Dandy")});
 * </pre></blockquote>
 * The Fact class offers several convenience constructors. For
 * example, you can create an identical fact with:
 * <p>
 * <blockquote><pre>
 *     Fact s = new Fact(
 *         "starred", "James Cagney", "Yankee Doodle Dandy");
 * </pre></blockquote>
 * or with:
 * <blockquote><pre>
 *     Fact s = new Fact(
 *         "starred",
 *         new Object[]{
 *             "James Cagney", "Yankee Doodle Dandy"});
 * </pre></blockquote>
 * Since they do not contain variables, Facts do not need to
 * copy themselves when they provide a "copy" for a proof.
 * They also avoid copying when then provide a dynamic
 * axiom.
 * <p>
 */
public class Fact extends Structure
        implements Axiom, DynamicAxiom {

    /*
     * With facts, there is nothing beyond the fact itself to
     * prove; there is nothing to resolve.
     */
    /**
     *
     */
    protected static final DynamicRule resolvent = new DynamicRule(null, null,
            new Structure[0]);

    /**
     * Contructs a fact from the specified object.
     *
     * @param functor the functor for this fact
     */
    public Fact(Object functor) {
        this(functor, new Fact[0]);
    }

    /**
     * Constructs a fact with the specified functor, and with terms that are
     * atoms wrapped around the supplied objects.
     *
     * @param functor the functor of the structure
     *
     *
     * @param objects the objects to convert into atoms and use as the terms of
     *                this fact
     *
     */
    public Fact(Object functor, Object[] objects) {
        super(functor, facts(objects));
    }

    /**
     * Constructs a fact with the specified functor and facts.
     *
     * @param functor the functor of the structure
     * @param terms   the terms of this fact, which can only be other facts
     */
    public Fact(Object functor, Fact[] terms) {
        super(functor, terms);
    }

    /**
     * Although "public", this method is not for public use.
     * <p>
     * Without this constructor, or if this constructor were private,
     * <p>
     * <blockquote><pre>
     * new Fact(
     *     "starred",
     *     new Term[]{new Fact("Cagney", "Yankee Doodle Dandy")})
     * </pre></blockquote>
     * <p>
     * would match the signature on <code>Fact(Object, Object[])
     * </code>, which is not what we want. This would wrap each fact in another
     * fact.
     * <p>
     * Allowing this constructor gives the appearance of allowing Facts with any
     * kind of terms, including variables, which are verboten.
     *
     * @param functor
     * @param objects
     *
     * @exception RuntimeException Cannot construct a fact from generic terms;
     *                             Use new Fact(functor, new Fact[]{...})
     */
    public Fact(Object functor, Term[] objects) {
        super(functor);
        throw new RuntimeException(
                "Cannot construct a fact from generic terms;\n"
                + "Use new Fact(functor, new Fact[]{...})");
    }

    /**
     * A convenience, equivalent to <code>new Fact(functor, new
     * Object[]{o})</code>.
     *
     * @param functor the functor of the structure
     *
     * @param o       the object to convert to an atom and use as the term of
     *                this
     *                fact
     *
     */
    public Fact(Object functor, Object o) {
        this(functor, new Object[]{o});
    }

    /**
     * A convenience, equivalent to <code>new Fact(functor, new
     * Object[]{o1, o2})</code>.
     *
     * @param functor the functor of the structure
     *
     * @param o1      an object to convert to an atom and use as the first term
     *                of
     *                this fact
     *
     * @param o2      an object to convert to an atom and use as the second term
     *                of
     *                this fact
     *
     */
    public Fact(Object functor, Object o1, Object o2) {
        this(functor, new Object[]{o1, o2});
    }

    /**
     * Returns this fact.
     *
     * @param ignored
     * @param ignored2
     *
     * @return this fact
     */
    @Override
    public Term copyForProof(
            AxiomSource ignored, Scope ignored2) {

        return this;
    }

    /**
     * Returns this fact.
     *
     * @param ignored
     *
     * @return this fact
     */
    @Override
    public DynamicAxiom dynamicAxiom(AxiomSource ignored) {
        return this;
    }

    /*
     * Create an array of (atomic) facts from an array of
     * objects.
     */

    /**
     *
     * @param objects
     *
     * @return
     */
    protected static Fact[] facts(Object[] objects) {
        Fact[] facts = new Fact[objects.length];
        for (int i = 0; i < objects.length; i++) {
            facts[i] = new Atom(objects[i]);
        }
        return facts;
    }

    /**
     * Returns this fact.
     *
     * @return this fact
     */
    @Override
    public Structure head() {
        return this;
    }

    /**
     * Returns an empty resolvent
     *
     * @return a dynamic rule with nothing in it
     */
    @Override
    public DynamicRule resolvent() {
        return resolvent;
    }

    /**
     * A speedier version of <code>unify(Structure s)</code>.
     *
     * @param f
     *
     * @return either an empty Unification, indicating success, or null,
     *         indicating failure
     */
    public Unification unify(Fact f) {
        if (!functorAndArityEquals(f)) {
            return null;
        }
        for (int i = 0; i < terms.length; i++) {
            Fact f1 = (Fact) terms[i];
            Fact f2 = (Fact) f.terms[i];
            if (f1.unify(f2) == null) {
                return null;
            }
        }
        return empty;
    }

    private static final Logger LOG = Logger.getLogger(Fact.class.getName());

}
