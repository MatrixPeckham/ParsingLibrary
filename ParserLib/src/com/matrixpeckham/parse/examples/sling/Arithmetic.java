package com.matrixpeckham.parse.examples.sling;

import java.util.logging.Logger;

/**
 * This class wraps an arithmetic function around two source
 * functions.
 * <p>
 * An <code>Arithmetic</code> object has an operator and two
 * sources. The operator must be '+', '-', '/', '*' or '%' (or
 * else <code>f(t)</code> of this object will always be (0,
 * 0)).
 * <p>
 * The value of this function at time t is the value of the
 * operator applied to the source functions at time t. For
 * example, evaluating the arithmetic sum <code>f1 + f2</code>
 * at time t creates the point <code>(f1(t).x + f2(t).x,
 * f1(t).y + f2(t).y)</code>.
 */
public class Arithmetic extends SlingFunction {

    /**
     *
     */
    protected char operator;

    /**
     * Constructs <code>operator(t, t)</code>, where operator is one of the
     * symbols +, -, /, *, %.
     *
     * @param operator
     */
    public Arithmetic(char operator) {
        this(operator, new T(), new T());
    }

    /**
     * Constructs a function object that is the sum, difference, quotient,
     * product or remainder (depending on the operator) of the supplied
     * functions.
     *
     * @param operator the operator to apply, one of the characters in
     *                 <code>+-/*%</code>
     *
     * @param b        an operand
     *
     * @param a        another operand
     */
    public Arithmetic(
            char operator, SlingFunction a, SlingFunction b) {

        super(a, b);
        this.operator = operator;
    }

    /*
     * Do the math.
     */

    /**
     *
     * @param a
     * @param b
     *
     * @return
     */
    protected double arithmetic(double a, double b) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
            case '%':
                return a % b;
        }
        return 0.0;
    }

    @Override
    public SlingFunction copy() {
        return new Arithmetic(operator, source[0].copy(), source[1].copy());
    }

    /**
     * Returns the sum, difference, quotient, product or remainder (depending on
     * the operator) of this object's source functions.
     *
     * @param t a number that represents how far along a plot is
     *
     * @return a new point that applies this object's operator to the values of
     *         the source functions at time t
     *
     */
    @Override
    public Point f(double t) {
        Point a = source[0].f(t);
        Point b = source[1].f(t);
        return new Point(
                arithmetic(a.x, b.x), arithmetic(a.y, b.y));
    }

    /**
     * Returns a string representation of this function.
     *
     * @return
     */
    @Override
    public String toString() {
        return operator + "(" + source[0] + ", " + source[1] + ")";
    }

    private static final Logger LOG
            = Logger.getLogger(Arithmetic.class.getName());

}
