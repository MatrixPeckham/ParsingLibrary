package com.matrixpeckham.parse.examples.sling;

import java.util.logging.Logger;

/**
 * This class combines the y component of each of two source
 * functions to form a new two-dimensional function. This
 * allows functions which are normally one-dimensional to
 * recombine into a two-dimensional function.
 * <p>
 * For example, both <code>sin</code> and <code>cos</code> are
 * normally one-dimensional functions, returning a single
 * number for any given value. The classes <code>Sin</code>
 * and <code>Cos</code> store their results in the y dimension
 * of a function, augmenting any particular point with t as
 * the x value. Objects of the <code>Cartesian</code> class
 * ignore the x component of each source function. The y
 * component of the first source function becomes the x
 * component of the <code>Cartesian</code> function, and the y
 * component of the second function becomes the y component of
 * the <code>Cartesian</code> function.
 * <p>
 * Consider the following program, which plots a circle:
 * <p>
 * <blockquote><pre>
 *     theta = 2*pi*t;
 *     x = cos(theta);
 *     y = sin(theta);
 *     plot cartesian(x, y);
 * </pre></blockquote>
 * <p>
 * The design principles at play in this package augment the x
 * and y functions above, so that they are effectively
 * <code>cartesian(t, cos(theta))</code> and
 * <code>cartesian(t, sin(theta))</code>. The program
 * recombines the y components of these functions into a new
 * <code>cartesian</code> with an x value of <code>cos(theta)
 * </code> and a y value of <code>sin(theta)</code>.
 */
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
     *          which point to return
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
