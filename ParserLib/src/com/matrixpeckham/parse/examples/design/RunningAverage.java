package com.matrixpeckham.parse.examples.design;

import java.util.logging.Logger;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Objects of this class maintain a running average. Each number that is added
 * with the <code>add</code> method increases the count by 1, and the total by
 * the amount added.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class RunningAverage
        implements
        com.matrixpeckham.parse.utensil.PubliclyCloneable<RunningAverage> {

    /**
     *
     */
    protected double count = 0;

    /**
     *
     */
    protected double total = 0;

    /**
     * Add a value to the running average, increasing the count by 1 and the
     * total by the given value.
     *
     * @param d the value to add into the running average
     */
    public void add(double d) {
        count++;
        total += d;
    }

    /**
     * Return the average so far.
     *
     * @return the average so far
     */
    public double average() {
        return total / count;
    }

    /**
     * Return a copy of this object.
     *
     * @return a copy of this object
     */
    @Override
    public RunningAverage copy() {
        RunningAverage ave = new RunningAverage();
        ave.count = count;
        ave.total = total;
        return ave;
    }

    private static final Logger LOG
            = Logger.getLogger(RunningAverage.class.getName());

}
