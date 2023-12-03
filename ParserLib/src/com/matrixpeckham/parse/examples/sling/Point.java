package com.matrixpeckham.parse.examples.sling;

import java.util.logging.Logger;

/**
 * Objects of this class store two numbers that effectively
 * determine a point in two-dimensional space.
 */
public class Point extends SlingFunction {

    /**
     *
     */
    public double x;

    /**
     *
     */
    public double y;

    /**
     * Create a point with the given coordinates.
     *
     * @param x
     * @param y
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Point copy() {
        return new Point(x, y); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Points are not really functions at all, but the <code>
     * Point</code> class subclasses <code>SlingFunction</code> so that they may
     * serve in compositions of other functions. Points have nothing to compute,
     * so the receiver always returns itself.
     *
     * @param t ignored
     *
     * @return this point
     */
    @Override
    public Point f(double t) {
        return this;
    }

    /**
     * Returns a string representation of this point.
     *
     * @return
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    private static final Logger LOG = Logger.getLogger(Point.class.getName());

}
