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
 * An ArithmeticOperator represents an arithmetic operation that will perform
 * itself as part of a proof.
 * <p>
 * An ArithmeticOperator has an operator and two terms. The operator must be
 * '+', '-', '/', '*' or '%', or else the eval() value of this object will
 * always be 0. The terms may be other arithmetic operators, variables, or
 * number structures.
 * <p>
 * For example, an ArithmeticOperator might be appear in a comparison, as
 * follows:
 * <blockquote><pre>
 *     &gt;(+(X, 3), 42)
 * </pre></blockquote>
 * The arithmetic operator will have a valid value if X is instantiated to a
 * NumberStructure object. If X is instantiated to, say, 40, then the arithmetic
 * operator's reply to eval() will be 47, and the comparison will succeed.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ArithmeticOperator extends Structure
        implements ArithmeticTerm {

    /**
     *
     */
    protected char operator;

    /**
     *
     */
    protected ArithmeticTerm term0;

    /**
     *
     */
    protected ArithmeticTerm term1;

    /**
     * Constructs an arithmetic operator with the indicated operator and terms.
     * <p>
     * The operator must be '+', '-', '/', '*' or '%', or else the eval() value
     * of this object will always be 0. The terms must be other arithmetic
     * operators, variables, or number structures. If either term is invalid,
     * this object will throw an EvaluationException during a proof.
     *
     * @param operator the operator
     *
     * @param term0    the first term
     * @param term1    the second term
     *
     */
    public ArithmeticOperator(
            char operator, ArithmeticTerm term0, ArithmeticTerm term1) {
        super(operator, new Term[]{term0, term1});
        this.operator = operator;
        this.term0 = term0;
        this.term1 = term1;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ArithmeticOperator)) {
            return false;
        }
        return super.equals(o); //all local fields passed to superclass
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


    /*
     * Do the math.
     */
    /**
     *
     * @param d0
     * @param d1
     *
     * @return
     */
    protected Object arithmeticValue(double d0, double d1) {
        double result;
        switch (operator) {
            case '+':
                result = d0 + d1;
                break;
            case '-':
                result = d0 - d1;
                break;
            case '*':
                result = d0 * d1;
                break;
            case '/':
                result = d0 / d1;
                break;
            case '%':
                result = d0 % d1;
                break;
            default:
                result = 0.0;
        }
        return result;
    }

    /**
     * Create a copy using the supplied scope for variables.
     *
     * @param ignored
     * @param scope   the scope to use for variables
     *
     * @return a copy with variables from the supplied scope
     */
    @Override
    public Term copyForProof(AxiomSource ignored, Scope scope) {
        return new ArithmeticOperator(
                operator,
                (ArithmeticTerm) term0.copyForProof(null, scope),
                (ArithmeticTerm) term1.copyForProof(null, scope));
    }

    /**
     * Returns the result of applying this object's operator against the
     * arithmetic values of its two terms. For example,
     * <p>
     * <blockquote><pre>
     *     NumberStructure two = new NumberStructure(2);
     *     ArithmeticOperator x, y;
     *     x = new ArithmeticOperator('*', two, two);
     *     y = new ArithmeticOperator('+', x, two);
     *     System.out.println(y + " = " + y.eval());
     * </pre></blockquote>
     * <p>
     * prints out:
     * <p>
     * <blockquote><pre>
     *     +(*(2, 2), 2) = 6.0
     * </pre></blockquote>
     *
     * @return the result of applying this object's operator to the arithmetic
     *         value of its two terms
     *
     * @exception EvaluationException if either term is not a valid arithmetic
     *                                value
     */
    @Override
    public Object eval() {

        double d0 = eval(term0);
        double d1 = eval(term1);

        return arithmeticValue(d0, d1);
    }

    /*
     * get the "double" value of this term
     */

    /**
     *
     * @param t
     *
     * @return
     */
    protected double eval(ArithmeticTerm t) {
        Object o = t.eval();
        if (o == null) {
            throw new EvaluationException(
                    t + " is undefined in " + this);
        }
        if (!(o instanceof Number)) {
            throw new EvaluationException(
                    t + " is not a number in " + this);
        }
        Number n = (Number) o;
        return n.doubleValue();
    }

    private static final Logger LOG
            = Logger.getLogger(ArithmeticOperator.class.getName());

}
