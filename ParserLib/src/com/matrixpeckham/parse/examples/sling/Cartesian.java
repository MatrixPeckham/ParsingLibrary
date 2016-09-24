package com.matrixpeckham.parse.examples.sling;

import java.util.logging.Logger;

public class Cartesian extends SlingFunction {

    /**
     * Constructs <code>cartesian(t, t)</code>.
     */
    public Cartesian() {
        super(new T(), new T());
    }

    /**
     * Constructs a function object that combines the y components of the two
     * sources into the x and y values of a new function.
     *
     * @param sX
     * @param sY
     */
    public Cartesian(SlingFunction sX, SlingFunction sY) {
        super(sX, sY);
    }

    @Override
    public Cartesian copy() {
        return new Cartesian(source[0].copy(), source[1].copy()); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Combine the y components of the sources into a new point.
     *
     * @param t a number that represents how far along a plot is, and thus tells
     * which point to return
     *
     * @return a new point: <code>(sx.f(t).y, sy.f(t).y)</code>
     */
    @Override
    public Point f(double t) {
        return new Point(source[0].f(t).y, source[1].f(t).y);
    }

    /**
     * Returns a string representation of this function.
     *
     * @return
     */
    @Override
    public String toString() {
        return "cartesian(" + source[0] + ", " + source[1] + ")";
    }

    private static final Logger LOG
            = Logger.getLogger(Cartesian.class.getName());

}
