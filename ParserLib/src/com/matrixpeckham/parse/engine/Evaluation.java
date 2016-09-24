package com.matrixpeckham.parse.engine;

import java.util.logging.Logger;

public class Evaluation extends Gateway {

    Term term0;

    Term term1;

    /**
     *
     */
    protected Unification currentUnification;

    /**
     * Constructs an Evaluation that will unify the first term with the second
     * term during proofs.
     *
     * @param term0 the first term to unify
     *
     * @param term1 the term whose value should unify with the first term
     */
    public Evaluation(Term term0, Term term1) {
        super("#", new Term[]{term0, term1});
        this.term0 = term0;
        this.term1 = term1;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Evaluation)) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Returns true if this Evaluation can unify its first term with the value
     * of its second term.
     * <p>
     * If the attempt to evaluate the second term causes an exception, this
     * method swallows it and simply fails.
     *
     * @return <code>true<</code>, if this Evaluation can unify its first term
     * with the arithmetic value of its second term
     */
    @Override
    public boolean canProveOnce() {
        Object o;
        try {
            o = term1.eval();
        } catch (EvaluationException e) {
            return false;
        }
        currentUnification = term0.unify(new Atom(o));
        return currentUnification != null;
    }
    /*
     * The superclass calls this after the evaluation has
     * succeeded once, and rule is now failing backwards. The
     * assigment needs to undo any binding it did on the way
     * forward.
     */

    @Override
    protected void cleanup() {
        unbind();
    }

    /**
     * Create a copy that uses the provided scope.
     *
     * @param ignored ignored
     *
     * @param scope the scope to use for variables in the copy
     *
     * @return a copy that uses the provided scope
     */
    @Override
    public Term copyForProof(AxiomSource ignored, Scope scope) {
        return new Evaluation(
                term0.copyForProof(null, scope),
                term1.copyForProof(null, scope));
    }

    /**
     * Releases the variable bindings that the last unification produced.
     *
     */
    public void unbind() {
        if (currentUnification != null) {
            currentUnification.unbind();
        }
        currentUnification = null;
    }

    private static final Logger LOG
            = Logger.getLogger(Evaluation.class.getName());

}
