package com.matrixpeckham.parse.examples.tests;

import static com.matrixpeckham.parse.examples.tests.Dangle.statement;
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
 * Test the statement parser from class <code>Dangle</code>.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowDangleTest {

    /**
     * Test the statement parser from class <code>Dangle</code>.
     *
     * @param args
     */
    public static void main(String[] args) {
        Parser<Token, NullCloneable, NullCloneable> p = statement();
        TokenTester<NullCloneable, NullCloneable> tt = new TokenTester<>(p);
        tt.setLogTestStrings(false);
        tt.test();
    }

    private static final Logger LOG
            = Logger.getLogger(ShowDangleTest.class.getName());

}
