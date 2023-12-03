package com.matrixpeckham.parse.examples.sling;

import static java.lang.Math.cos;

import java.util.logging.Logger;

/**
 * This class wraps a cos function around another source
 * function.
 * <p>
 * The cos function is the x component of a stone's location
 * at an angle given in radians.
 */
public class Cos extends SlingFunction {

    /**
     * Constructs <code>cos(t)</code>.
     */
    public Cos() {
        super(new T());
    }

    /**
     * Constructs a function object that wraps a cos function around the given
     * source function.
     *
     * @param source
     */
    public Cos(SlingFunction source) {
        super(source);
    }

    @Override
    public Cos copy() {
        return new Cos(source[0].copy()); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Returns, essentially, <code>cos(source.f(t))</code>.
     *
     * @param t a number that represents how far along a plot is, and thus tells
     *          which point to return
     *
     * @return a new point: <code>(t, cos(source.f(t).y))</code>
     *
     * @see com.matrixpeckham.parse.examples.sling.Abs
     * @see com.matrixpeckham.parse.examples.sling.Abs#f(double)
     */
    @Override
    public Point f(double t) {
        return new Point(t, cos(source[0].f(t).y));
    }

    /**
     * Returns a string representation of this function.
     *
     * @return
     */
    @Override
    public String toString() {
        return "cos(" + source[0] + ")";
    }

    private static final Logger LOG = Logger.getLogger(Cos.class.getName());

}
