package com.matrixpeckham.parse.examples.mechanics;

import static com.matrixpeckham.parse.examples.arithmetic.ArithmeticParser.start;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.TokenAssembly;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.ArrayList;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * This class shows the complete results of matching an arithmetic expression
 * parser against an expression.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowSequenceLeftovers {

    /**
     * Show the complete results of matching an arithmetic expression parser
     * against an expression.
     *
     * @param args
     */
    public static void main(String args[]) {

        ArrayList<Assembly<Token, Double, NullCloneable>> v = new ArrayList<>();
        v.add(new TokenAssembly<>("3 * 4 + 5"));

        System.out.println(start().match(v));
    }

    private static final Logger LOG
            = Logger.getLogger(ShowSequenceLeftovers.class.getName());

}
