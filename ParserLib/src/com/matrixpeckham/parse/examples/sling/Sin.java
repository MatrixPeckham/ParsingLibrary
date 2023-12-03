package com.matrixpeckham.parse.examples.sling;

import static java.lang.Math.sin;

import java.util.logging.Logger;

/**
 * This class wraps a sin function around another source
 * function.
 * <p>
 * The sin function is the y component of a stone's location
 * at an angle given in radians.
 */
public class Sin extends SlingFunction {

    /**
     * Constructs <code>sin(t)</code>.
     */
    public Sin() {
        super(new T());
    }

    /**
     * Constructs a function object that wraps a sin function around the given
     * source function.
     *
     * @param source
     */
    public Sin(SlingFunction source) {
        super(source);
    }

    @Override
    public Sin copy() {
        return new Sin(source[0].copy()); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Returns, essentially, <code>sin(source.f(t))</code>.
     *
     * @param t a number that represents how far along a plot is, and thus tells
     *          which point to return
     *
     * @return a new point: <code>(t, sin(source.f(t).y))</code>
     *
     * @see com.matrixpeckham.parse.examples.sling.Abs
     * @see com.matrixpeckham.parse.examples.sling.Abs#f(double)
     */
    @Override
    public Point f(double t) {
        return new Point(t, sin(source[0].f(t).y));
    }

    /**
     * Returns a string representation of this function.
     *
     * @return
     */
    @Override
    public String toString() {
        return "sin(" + source[0] + ")";
    }

    private static final Logger LOG = Logger.getLogger(Sin.class.getName());

}
