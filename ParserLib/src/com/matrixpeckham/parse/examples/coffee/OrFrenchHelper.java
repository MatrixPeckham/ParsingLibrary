package com.matrixpeckham.parse.examples.coffee;

import java.util.ArrayList;
import java.util.logging.Logger;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * This helper sets a target coffee object's <code>
 * alsoOfferFrench</code> attribute to true.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class OrFrenchHelper extends Helper<ArrayList<Coffee>> {

    /**
     * Sets a target coffee object's <code>alsoOfferFrench
     * </code> attribute to true. The target coffee is the last coffee in a
     * ArrayList of coffees.
     *
     * @param target
     */
    @Override
    public void startElement(ArrayList<Coffee> target) {
        Coffee c = target.get(target.size() - 1);
        c.setAlsoOfferFrench(true);
    }

    private static final Logger LOG
            = Logger.getLogger(OrFrenchHelper.class.getName());

}
