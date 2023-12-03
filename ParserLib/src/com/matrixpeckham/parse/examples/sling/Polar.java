package com.matrixpeckham.parse.examples.sling;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.util.logging.Logger;

/**
 * This class represents the location of a sling stone in
 * terms of the length of a sling's strap, and the angle (in
 * radians) around the slinger's head.
 * <p>
 * The constructor accepts two source functions. The y
 * component of the first function represents the length of
 * the strap, and the y component of the second function
 * represents the strap's angle.
 * <p>
 * When you whirl a sling, the stone's path covers 2*pi
 * "radians" of arc as the stone makes one revolution. The
 * number "pi" is equal to the ratio of a circle's
 * circumference to its diameter, about 3.1416. As a stone
 * makes one revolution, its arc varies from 0 to 2*pi,
 * starting in the positive x direction and rotating
 * counterclockwise.
 * <p>
 * We can use the variable r to represent the strap's length,
 * and the variable theta to represent the strap's angle. At
 * any point in time, the stone's position is <code>polar(r,
 * theta)</code>. This is equivalent to <code>cartesian(r *
 * cos(theta), r * sin(theta))</code>, since the <code>cos
 * </code> and <code>sin</code> functions represent the x and
 * y components of a stone's path.
 */
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
     *          which point to return
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
