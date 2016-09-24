package com.matrixpeckham.parse.examples.tests;

import static com.matrixpeckham.parse.examples.regular.RegularParser.start;
import com.matrixpeckham.parse.parse.chars.CharacterTester;
import java.util.logging.Logger;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Test the <code>RegularParser</code> class.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowRegularTester {

    /**
     * Test the <code>RegularParser</code> class.
     *
     * @param args
     */
    public static void main(String[] args) {
        new CharacterTester<>(start()).test();
    }

    private static final Logger LOG
            = Logger.getLogger(ShowRegularTester.class.getName());

}
