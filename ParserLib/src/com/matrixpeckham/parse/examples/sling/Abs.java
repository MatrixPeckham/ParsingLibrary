package com.matrixpeckham.parse.examples.sling;

import static java.lang.Math.abs;

/**
 * This class wraps an absolute value function around
 * another source function.
 * <p>
 * The absolute value of x is just x if x is positive, and
 * is –x if x is negative.
 */
public class Abs extends SlingFunction {

    /**
     * Constructs <code>abs(t)</code>.
     */
    public Abs() {
        this(new T());
    }

    /**
     * Constructs a function object that wraps an abs function around the given
     * source function.
     *
     * @param source
     */
    public Abs(SlingFunction source) {
        super(source);
    }

    @Override
    public Abs copy() {
        return new Abs(source[0].copy());
    }

    /**
     * Returns, essentially, <code>abs(source.f(t))</code>.
     * <p>
     * Subclasses of <code>SlingFunction</code> return a 2D point from the
     * <code>f()</code> method. The <code>abs</code> function needs only one
     * argument from its source, not a whole point. This method ignores the x
     * component of the source's f(t) value, and uses the y component as an
     * argument to <code>Math.abs</code>.
     * <p>
     * The <code>Math.abs</code> function returns a single number, so this
     * method uses t as the x component of the constant that it returns. Thus
     * the return value is:
     * <p>
     * <blockquote><pre>
     *    new Point(t, Math.abs(source.f(t).y))
     * </pre></blockquote>
     *
     * @param t a number that represents how far along a plot is, and thus tells
     *          which point to return
     *
     * @return a new point: <code>(t, abs(source.f(t).y))</code>
     */
    @Override
    public Point f(double t) {
        return new Point(t, abs(source[0].f(t).y));
    }

    /**
     * Returns a string representation of this function.
     *
     * @return
     */
    @Override
    public String toString() {
        return "abs(" + source[0] + ")";
    }

}
