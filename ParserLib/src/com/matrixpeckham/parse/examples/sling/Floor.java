package com.matrixpeckham.parse.examples.sling;

import static java.lang.Math.floor;

import java.util.logging.Logger;

/**
 * This class wraps a floor function around another source
 * function.
 * <p>
 * The floor function applied to a number x returns the
 * largest integer that is equal to or less than x.
 */
public class Floor extends SlingFunction {

    /**
     * Constructs <code>floor(t)</code>.
     */
    public Floor() {
        super(new T());
    }

    /**
     * Constructs a function object that wraps a floor function around the given
     * source function.
     *
     * @param source
     */
    public Floor(SlingFunction source) {
        super(source);
    }

    @Override
    public Floor copy() {
        return new Floor(source[0].copy()); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Returns, essentially, <code>floor(source.f(t))</code>.
     *
     * @param t a number that represents how far along a plot is, and thus tells
     *          which point to return
     *
     * @return a new point: <code>(t, floor(source.f(t).y))</code>
     *
     * @see com.matrixpeckham.parse.examples.sling.Abs
     * @see com.matrixpeckham.parse.examples.sling.Abs#f(double)
     */
    @Override
    public Point f(double t) {
        return new Point(t, floor(source[0].f(t).y));
    }

    /**
     * Returns a string representation of this function.
     *
     * @return
     */
    @Override
    public String toString() {
        return "floor(" + source[0] + ")";
    }

    private static final Logger LOG = Logger.getLogger(Floor.class.getName());

}
