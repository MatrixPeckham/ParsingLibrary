package com.matrixpeckham.parse.examples.sling;

import static java.lang.Math.random;
import java.util.logging.Logger;

public class Random extends SlingFunction {

    @Override
    public Random copy() {
        return new Random(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Generate a random number r between 0 and 1, and return the point (t, r).
     *
     * @param t a number that represents how far along a plot is, and thus tells
     * which point to return
     *
     * @return a new point: <code>(t, random())</code>
     */
    @Override
    public Point f(double t) {
        return new Point(t, random());
    }

    /**
     * Returns a string representation of this function.
     *
     * @return
     */
    @Override
    public String toString() {
        return "random";
    }

    private static final Logger LOG = Logger.getLogger(Random.class.getName());

}
