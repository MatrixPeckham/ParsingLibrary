package com.matrixpeckham.parse.utensil;

import java.util.logging.Logger;

public class LinearCalculator {

    double xFrom;

    double xTo;

    double yFrom;

    double yTo;

    /**
     * Create a LinearCalculator from known points on two scales.
     *
     * @param xFrom
     * @param xTo
     * @param yFrom
     * @param yTo
     */
    public LinearCalculator(double xFrom, double xTo, double yFrom, double yTo) {
        this.xFrom = xFrom;
        this.xTo = xTo;
        this.yFrom = yFrom;
        this.yTo = yTo;
    }

    /**
     * Return the value on the first scale, corresponding to the given value on
     * the second scale.
     *
     * @param y
     * @return the value on the first scale, corresponding to the given value on
     * the second scale
     */
    public double calculateXforGivenY(double y) {
        if (yTo == yFrom) {
            return (xFrom + xTo) / 2;
        }
        return (y - yFrom) / (yTo - yFrom) * (xTo - xFrom) + xFrom;
    }

    /**
     * Return the value on the second scale, corresponding to the given value on
     * the first scale.
     *
     * @param x
     * @return the value on the second scale, corresponding to the given value
     * on the first scale
     */
    public double calculateYforGivenX(double x) {
        if (xTo == xFrom) {
            return (yFrom + yTo) / 2;
        }
        return (x - xFrom) / (xTo - xFrom) * (yTo - yFrom) + yFrom;
    }

    /**
     * Show the example in the class comment.
     *
     * @param args ignored.
     */
    public static void main(String args[]) {
        LinearCalculator lc = new LinearCalculator(32, 212, 0, 100);
        System.out.println(lc.calculateYforGivenX(68));
    }

    /**
     * Return a textual description of this object.
     *
     * @return a textual description of this object
     */
    @Override
    public String toString() {
        return "" + xFrom + ":" + xTo + "::" + yFrom + ":" + yTo;
    }

    private static final Logger LOG
            = Logger.getLogger(LinearCalculator.class.getName());

}
