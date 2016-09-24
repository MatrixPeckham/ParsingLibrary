package com.matrixpeckham.parse.examples.sling;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.logging.Logger;

public class Polar extends SlingFunction {

    /**
     * Constructs <code>polar(t, t)</code>.
     */
    public Polar() {
        super(new T(), new T());
    }

    /**
     * Constructs a polar function object from the given sources,using the y
     * component of the first function as a sling's strap length, and the y
     * component of the second function as the sling's angle.
     *
     * @param radius
     * @param theta
     */
    public Polar(SlingFunction radius, SlingFunction theta) {
        super(radius, theta);
    }

    @Override
    public Polar copy() {
        return new Polar(source[0].copy(), source[1].copy()); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Combine the y components of the sources into a new point, using the first
     * source for the sling's strap length, and the second source for the
     * stone's angle.
     *
     * @param t a number that represents how far along a plot is, and thus tells
     * which point to return
     *
     * @return a new point: <code>(r * Math.cos(theta),
     *         r * Math.sin(theta))</code>, where r is f1.f(t).y and theta is f2.f(t).y.
     */
    @Override
    public Point f(double t) {
        double r = source[0].f(t).y;
        double theta = source[1].f(t).y;
        return new Point(
                r * cos(theta), r * sin(theta));
    }

    /**
     * Return a string representation of this function.
     *
     * @return
     */
    @Override
    public String toString() {
        return "polar(" + source[0] + ", " + source[1] + ")";
    }

    private static final Logger LOG = Logger.getLogger(Polar.class.getName());

}
