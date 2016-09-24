package com.matrixpeckham.parse.examples.tokens;

import com.matrixpeckham.parse.parse.tokens.Token;
import static com.matrixpeckham.parse.parse.tokens.Token.EOF;
import com.matrixpeckham.parse.parse.tokens.Tokenizer;
import java.io.IOException;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * This class shows how to add a new multi-character symbol.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowNewSymbol {

    /**
     * Demonstrate how to add a multi-character symbol.
     *
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String args[]) throws IOException {
        Tokenizer t = new Tokenizer("42.001 =~= 42");

        t.symbolState().add("=~=");

        while (true) {
            Token tok = t.nextToken();
            if (tok.equals(EOF)) {
                break;
            }
            System.out.println(tok);
        }
    }

    private static final Logger LOG
            = Logger.getLogger(ShowNewSymbol.class.getName());

}
