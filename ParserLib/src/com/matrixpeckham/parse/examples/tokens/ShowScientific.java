package com.matrixpeckham.parse.examples.tokens;

import static com.matrixpeckham.parse.examples.arithmetic.ArithmeticParser.start;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.TokenAssembly;
import com.matrixpeckham.parse.parse.tokens.Tokenizer;
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
 * This class shows how to use a tokenizer that accepts scientific notation with
 * an arithmetic parser.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowScientific {

    /**
     * Show how to use a tokenizer that accepts scientific notation with an
     * arithmetic parser.
     *
     * @param args
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {

        Tokenizer t = new Tokenizer();
        ScientificNumberState sns = new ScientificNumberState();
        t.setCharacterState('0', '9', sns);
        t.setCharacterState('.', '.', sns);
        t.setCharacterState('-', '-', sns);

        t.setString("1e2 + 1e1 + 1e0 + 1e-1 + 1e-2 + 1e-3");

        Parser<Token, Double, NullCloneable> p = start();
        Assembly<Token, Double, NullCloneable> a = p.bestMatch(
                new TokenAssembly<>(t));
        System.out.println(a.pop());
    }

    private static final Logger LOG
            = Logger.getLogger(ShowScientific.class.getName());

}
