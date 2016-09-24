package com.matrixpeckham.parse.examples.tokens;

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
 * This class shows a Tokenizer consuming a word.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowWord {

    /**
     * Just an example.
     *
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        Tokenizer t = new Tokenizer("A65 B66");
        System.out.println(t.nextToken());
        System.out.println(t.nextToken());
    }

    private static final Logger LOG = Logger.getLogger(ShowWord.class.getName());

}
