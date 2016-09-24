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
 * This helper sets a target coffee object's <code>roast
 * </code> attribute.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class RoastHelper extends Helper<ArrayList<Coffee>> {

    /**
     * Sets a target coffee object's <code>roast</code> attribute to the given
     * string. The target coffee is the last coffee in a ArrayList of coffees.
     *
     * @param s
     * @param target
     */
    @Override
    public void characters(String s, ArrayList<Coffee> target) {
        Coffee c = target.get(target.size() - 1);
        c.setRoast(s);
    }

    private static final Logger LOG
            = Logger.getLogger(RoastHelper.class.getName());

}
