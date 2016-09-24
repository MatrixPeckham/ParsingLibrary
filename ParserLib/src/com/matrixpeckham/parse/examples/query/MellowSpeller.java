package com.matrixpeckham.parse.examples.query;

import java.util.logging.Logger;


/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * This class provides a speller that allows any spelling, which facilitates
 * random testing of Jaql language elements.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 *
 * @see JaqlUe
 */
public class MellowSpeller implements Speller {

    /**
     * Allow any spelling of any class name.
     * @return 
     */
    @Override
    public String getClassName(String s) {
        return s;
    }

    /**
     * Allow any spelling of any variable name.
     * @return 
     */
    @Override
    public String getVariableName(String s) {
        return s;
    }

    private static final Logger LOG
            = Logger.getLogger(MellowSpeller.class.getName());
}
