package com.matrixpeckham.parse.examples.sling;

import static java.lang.Math.ceil;

/**
 * This class wraps a ceil function around another source
 * function.
 * <p>
 * The ceil function applied to a number x returns the
 * smallest integer that is equal to or greater than x.
 */
public class Ceil extends SlingFunction {

    /**
     * Constructs <code>ceil(t)</code>.
     */
    public Ceil() {
        super(new T());
    }

    /**
     * Constructs a function object that wraps a ceil function around the given
     * source function.
     *
     * @param source
     */
    public Ceil(SlingFunction source) {
        super(source);
    }

    @Override
    public Ceil copy() {
        return new Ceil(source[0].copy()); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Returns, essentially, <code>ceil(source.f(t))</code>.
     *
     * @param t a number that represents how far along a plot is, and thus tells
     *          which point to return
     *
     * @return a new point: <code>(t, ceil(source.f(t).y))</code>
     *
     * @see com.matrixpeckham.parse.examples.sling.Abs
     * @see com.matrixpeckham.parse.examples.sling.Abs#f(double)
     */
    @Override
    public Point f(double t) {
        return new Point(t, ceil(source[0].f(t).y));
    }

    /**
     * Returns a string representation of this function.
     *
     * @return
     */
    @Override
    public String toString() {
        return "ceil(" + source[0] + ")";
    }

}
