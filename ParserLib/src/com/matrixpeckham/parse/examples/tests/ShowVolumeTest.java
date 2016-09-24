package com.matrixpeckham.parse.examples.tests;

import static com.matrixpeckham.parse.examples.tests.VolumeQuery.query;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.TokenTester;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.logging.Logger;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Test the query parser from class <code>VolumeQuery
 * </code>.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowVolumeTest {

    /**
     * Test the query parser from class <code>VolumeQuery
     * </code>.
     *
     * @param args
     */
    public static void main(String[] args) {
        Parser<Token, String, NullCloneable> p = query();
        TokenTester<String, NullCloneable> tt = new TokenTester<>(p);
        tt.test();
    }

    private static final Logger LOG
            = Logger.getLogger(ShowVolumeTest.class.getName());

}
