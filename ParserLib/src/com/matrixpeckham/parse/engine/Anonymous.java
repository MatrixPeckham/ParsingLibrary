package com.matrixpeckham.parse.engine;

import static com.matrixpeckham.parse.engine.Unification.empty;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * An anonymous variable unifies successfully with any other term, without
 * binding to the term.
 * <p>
 * Anonymous variables are useful for screening out unwanted terms. For example,
 * if a program describes a marriage in terms of an id, the husband, wife, and
 * the beginning and end dates of the marriage, its facts might look something
 * like:
 * <blockquote><pre>
 *     marriage(001, balthasar, grimelda, 14560512, 14880711);
 *     // ...
 *     marriage(257, kevin, karla, 19790623, present);
 * </pre></blockquote>
 * A rule that extracts just the husband from <code>marriage</code> facts is:
 * <blockquote><pre>
 *     husband(Id, Hub) :- marriage(Id, Hub, _, _, _);
 * </pre></blockquote>
 * The underscores in this rule represent anonymous variables. When the rule
 * executes, it will unify its <code>marriage</code> structure with
 * <code>marriage</code> facts, without regard to the last three terms of those
 * facts.
 * <p>
 * Without anonymous variables, the <code>husband</code> rule would need three
 * unused variables. Note that the following approach would fail:
 * <blockquote><pre>
 *     husband(Id, Hub) :-
 *         marriage(Id, Hub, Anon, Anon, Anon); // wrong
 * </pre></blockquote>
 * This approach, while tempting, will not work because the variable
 * <code>Anon</code> will bind to the structures it encounters. Issued against
 * the example program, <code>Anon</code> will first bind to
 * <code>grimelda</code>. Next, <code>Anon</code> will attempt to bind to
 * <code>14560512</code>. This will fail, since <code>Anon</code> will already
 * be bound to <code>grimelda</code>.
 * <p>
 * The essential behavior anonymous variables provide is that they unify
 * successfully without binding.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class Anonymous extends Variable {

    /**
     * Constructs an anonymous variable.
     */
    public Anonymous() {
        super("_");
    }

    /**
     * Returns this anonymous variable, which does not unify with anything and
     * thus does not need to copy itself.
     *
     * @param ignored2
     * @param ignored
     * @return this anonymous variable
     *
     *
     */
    @Override
    public Term copyForProof(
            AxiomSource ignored, Scope ignored2) {

        return this;
    }

    /**
     * Return the value of this anonymous variable to use in functions; this is
     * meaningless in logic programming, but the method returns the name of this
     * variable.
     *
     * @return the name of the anonymous variable
     */
    @Override
    public Object eval() {
        return name;
    }

    /**
     * Returns an empty unification.
     * <p>
     * The <code>unify</code> methods indicate failure by returning
     * <code>null</code>. Anonymous variables succeed without binding, so they
     * always return empty unifications.
     *
     * @param ignored ignored
     *
     * @return A successful, but empty, unification
     */
    @Override
    public Unification unify(Structure ignored) {
        return empty;
    }

    /**
     * Returns an empty unification.
     *
     * @param ignored ignored
     *
     * @return an empty unification
     */
    @Override
    public Unification unify(Term ignored) {
        return empty;
    }

    /**
     * Returns an empty unification.
     *
     * @param ignored ignored
     *
     * @return an empty unification
     */
    @Override
    public Unification unify(Variable ignored) {
        return empty;
    }

    /**
     * Returns an empty unification.
     *
     * @return an empty unification
     */
    @Override
    public Unification variables() {
        return empty;
    }

    private static final Logger LOG
            = Logger.getLogger(Anonymous.class.getName());

}
