package com.matrixpeckham.parse.examples.tokens;

import com.matrixpeckham.parse.parse.tokens.Token;
import static com.matrixpeckham.parse.parse.tokens.Token.TT_NUMBER;
import static com.matrixpeckham.parse.parse.tokens.Token.TT_SYMBOL;
import static com.matrixpeckham.parse.parse.tokens.Token.TT_WORD;
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
 * This class shows some aspects of default tokenization.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowDefaultTokenization {

    /**
     * Show some aspects of default tokenization.
     *
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String args[]) throws IOException {
        Tokenizer t = new Tokenizer(">give 2receive");

        Token manual[] = new Token[]{
            new Token(TT_SYMBOL, ">", 0),
            new Token(TT_WORD, "give", 0),
            new Token(TT_NUMBER, "", 2.0),
            new Token(TT_WORD, "receive", 0)};

        for (int i = 0; i < 4; i++) {
            Token tok = t.nextToken();
            if (tok.equals(manual[i])) {
                System.out.print("ok! ");
            }
        }
    }

    private static final Logger LOG
            = Logger.getLogger(ShowDefaultTokenization.class.getName());

}
