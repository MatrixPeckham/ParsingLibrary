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
 * This helper adds a new coffee object to the end of a vector of coffees.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class NewCoffeeHelper extends Helper<ArrayList<Coffee>> {

    /**
     * Add a new coffee object to the end of a vector of coffees.
     *
     * @param target
     */
    @Override
    public void startElement(ArrayList<Coffee> target) {
        ArrayList<Coffee> v = target;
        v.add(new Coffee());
    }

    private static final Logger LOG
            = Logger.getLogger(NewCoffeeHelper.class.getName());

}
