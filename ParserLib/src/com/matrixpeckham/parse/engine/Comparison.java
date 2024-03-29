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
 * A Comparison object applies a comparison operator to its terms in order to
 * prove itself.
 * <p>
 * The functor of a comparison must be one of the strings, "&lt;",
 * "&gt;", "&lt;=", "&gt;=", "=", or "!=", indicating comparison this object
 * will perform
 * during a proof. Any other functor will cause the comparison to always be
 * false.
 * <p>
 * Here is an example of a comparison:
 * <blockquote><pre>
 *     Atom alpha = new Atom("alpha");
 *     Atom beta = new Atom("beta");
 *     Comparison c = new Comparison("&lt;=", alpha, beta);
 *     System.out.println(c + ", " + c.canFindNextProof());
 * </pre></blockquote> This prints out:
 * <blockquote><pre>
 *     &lt;=(alpha, beta), true
 * </pre></blockquote>
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class Comparison extends Gateway implements BooleanTerm {

    String operator;

    ComparisonTerm term0;

    ComparisonTerm term1;

    /**
     * Create a comparison with the specified operator and comparison terms.
     *
     * @param operator the comparison operator
     *
     * @param term0    the first term
     * @param term1    the second term
     */
    public Comparison(String operator, ComparisonTerm term0,
            ComparisonTerm term1) {

        super(operator, new Term[]{term0, term1});
        this.operator = operator;
        this.term0 = term0;
        this.term1 = term1;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Comparison)) {
            return false;
        }
        return super.equals(o); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Returns true if the comparison operator holds true between the values of
     * this comparison's terms.
     *
     * @return <code>true</code> if the comparison operator holds true between
     *         the values of this comparison's terms.
     */
    @Override
    public boolean canProveOnce() {
        Object p0 = term0.eval();
        Object p1 = term1.eval();
        return compare(p0, p1);
    }

    /**
     * Returns the result of comparing two objects, using the indicated
     * comparison operator.
     *
     * @param obj0 a string or number to compare
     *
     * @param obj1 a string or number to compare
     *
     * @return the result of comparing two objects
     */
    protected boolean compare(Object obj0, Object obj1) {
        if (obj0 instanceof Number && obj1 instanceof Number) {
            return compareNumber((Number) obj0, (Number) obj1);
        }
        if (obj0 instanceof String && obj1 instanceof String) {
            return compareString((String) obj0, (String) obj1);
        }
        return false;

    }

    /**
     * Returns the result of comparing two Numbers, using the indicated
     * comparison operator.
     *
     * @param n0 a Number to compare
     *
     *
     * @param n1 a Number to compare
     *
     * @return the result of comparing the two numbers
     */
    protected boolean compareNumber(Number n0, Number n1) {

        double d0 = n0.doubleValue();
        double d1 = n1.doubleValue();

        if (operator.equals(">")) {
            return d0 > d1;
        }
        if (operator.equals("<")) {
            return d0 < d1;
        }
        if (operator.equals("=")) {
            return Math.abs(d0 - d1) < 0.000001;
        }
        if (operator.equals(">=")) {
            return d0 >= d1;
        }
        if (operator.equals("<=")) {
            return d0 <= d1;
        }
        if (operator.equals("!=")) {
            return d0 != d1;
        }
        return false;
    }

    /**
     * Returns the result of comparing two Strings, using the indicated
     * comparison operator.
     *
     * @param s0 a String to compare
     *
     * @param s1 a String to compare
     *
     * @return the result of comparing the two strings
     */
    protected boolean compareString(String s0, String s1) {

        int comparison = s0.compareTo(s1);

        if (operator.equals(">")) {
            return comparison > 0;
        }
        if (operator.equals("<")) {
            return comparison < 0;
        }
        if (operator.equals("=")) {
            return comparison == 0;
        }
        if (operator.equals(">=")) {
            return comparison >= 0;
        }
        if (operator.equals("<=")) {
            return comparison <= 0;
        }
        if (operator.equals("!=")) {
            return comparison != 0;
        }
        return false;
    }

    /**
     * Create a copy that uses the provided scope.
     *
     * @param ignored ignored
     *
     * @param scope   the scope to use for variables in the copy
     *
     * @return a copy that uses the provided scope
     */
    @Override
    public Term copyForProof(AxiomSource ignored, Scope scope) {
        return new Comparison(
                operator,
                (ComparisonTerm) term0.copyForProof(null, scope),
                (ComparisonTerm) term1.copyForProof(null, scope));
    }

    /**
     * Returns <code>Boolean.TRUE</code> if the comparison operator holds true
     * between the values of the two terms.
     *
     * @return <code>Boolean.TRUE</code> if the comparison operator holds true
     *         between the values of the two terms.
     */
    @Override
    public Object eval() {
        return canProveOnce();
    }

    private static final Logger LOG
            = Logger.getLogger(Comparison.class.getName());

}
