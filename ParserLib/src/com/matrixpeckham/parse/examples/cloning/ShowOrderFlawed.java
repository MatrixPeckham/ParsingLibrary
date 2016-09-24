package com.matrixpeckham.parse.examples.cloning;

import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Show the flaw in OrderFlawed.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowOrderFlawed {

    /**
     * Show the flaw in OrderFlaw. OrderFlaw has an inadequate clone() method,
     * which just creates a new object with the same fields. Adjusting either an
     * original or a cloned OrderFlaw object will affect both objects.
     *
     * @param args
     * @throws java.lang.CloneNotSupportedException
     */
    public static void main(String args[]) throws CloneNotSupportedException {
        Customer al = new Customer("Albert", 180);

        OrderFlawed orig = new OrderFlawed(al);
        OrderFlawed bogus = (OrderFlawed) orig.clone();

        bogus.getCustomer().setIQ(100);
        System.out.println(orig.getCustomer().getIQ());
    }

    private static final Logger LOG
            = Logger.getLogger(ShowOrderFlawed.class.getName());

}
