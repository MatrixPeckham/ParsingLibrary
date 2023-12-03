package com.matrixpeckham.parse.examples.sling;

import static java.lang.Math.*;

import java.util.logging.Logger;

/**
 * This class represents the location of a sling stone in
 * terms of the length of a sling's strap, and the speed
 * of the stone's rotation around the slinger's head.
 * <p>
 * The constructor accepts two source functions. The y
 * component of the first function represents the length of
 * the strap, and the y component of the second function
 * represents the stone's angular speed. A speed of 1
 * represents one full rotation that occurs during the course
 * of a plot.
 */
public class Sling extends SlingFunction {

    /**
     * Constructs sling(t, t), which is mainly useful for a prototypical sling.
     */
    public Sling() {
        super(new T(), new T());
    }

    /**
     * Constructs a sling from the given sources, using the y component of the
     * first function as a sling's strap length, and the y component of the
     * second function as the sling's speed.
     *
     * @param radius
     * @param speed
     */
    public Sling(SlingFunction radius, SlingFunction speed) {
        super(radius, speed);
    }

    @Override
    public Sling copy() {
        return new Sling(source[0].copy(), source[1].copy()); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Combine the y components of the sources into a new point, using the first
     * source for the sling's strap length, and the second source for the
     * stone's speed.
     *
     * @param t a number that represents how far along a plot is, and thus tells
     *          which point to return
     *
     * @return a new point that represents a stone's location given the length
     *         of the strap and the number of rotations. If r is the strap length, then
     *         r = f1.f(t).y. If s is the speed, then s = f2.f(t).y. If the angle
     *         through which the stone has swept is theta, then theta = pi*2*speed * t.
     *         The returned point, then, is (r*cos(theta), r*sin(theta));
     */
    @Override
    public Point f(double t) {
        double r = source[0].f(t).y;
        double speed = source[1].f(t).y;
        double theta = PI * 2 * speed * t;
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
        return "sling(" + source[0] + ", " + source[1] + ")";
    }

    private static final Logger LOG = Logger.getLogger(Sling.class.getName());

}
