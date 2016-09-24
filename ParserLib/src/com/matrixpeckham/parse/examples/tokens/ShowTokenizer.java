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
 * Show a default <code>Tokenizer</code> object at work.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowTokenizer {

    /**
     * Show a default Tokenizer at work.
     *
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String args[]) throws IOException {

        String s = "\"It's 123 blast-off!\", she said, // watch out!\n"
                + "and <= 3 'ticks' later /* wince */ , it's blast-off!";

        System.out.println(s);
        System.out.println();

        Tokenizer t = new Tokenizer(s);

        while (true) {
            Token tok = t.nextToken();
            if (tok.equals(EOF)) {
                break;
            }
            System.out.println("(" + tok + ")");
        }
    }

    private static final Logger LOG
            = Logger.getLogger(ShowTokenizer.class.getName());

}
