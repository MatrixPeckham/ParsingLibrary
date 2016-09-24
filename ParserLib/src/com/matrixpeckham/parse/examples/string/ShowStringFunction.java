package com.matrixpeckham.parse.examples.string;

import static java.lang.System.out;
import java.util.logging.Logger;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Create and use a string function at runtime.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowStringFunction {

    /**
     * Create and use a string function at runtime.
     * @param args
     */
    public static void main(String[] args) {
        StringFunction func = new Trim(new LowerCase());
        System.out.println(
                ">" + func.f(" TAKE IT EASY ") + "<");
    }

    private static final Logger LOG
            = Logger.getLogger(ShowStringFunction.class.getName());
}
