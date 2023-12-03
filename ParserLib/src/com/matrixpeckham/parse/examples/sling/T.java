package com.matrixpeckham.parse.examples.sling;

/**
 * This class represents time, which the Sling environment
 * defines to vary from 0 to 1 as a plot unfolds.
 */
public class T extends SlingFunction {

    @Override
    public T copy() {
        return new T(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Returns the given time
     *
     * @param t a number that represents how far along a plot is, and thus tells
     *          which point to return
     *
     * @return the given time, placing it in the y component, and augmenting a
     *         point with time also in the x component. The point returned is (t, t).
     */
    @Override
    public Point f(double t) {
        return new Point(t, t);
    }

    /**
     * Returns a string representation of the time function.
     *
     * @return
     */
    @Override
    public String toString() {
        return "t";
    }

}
