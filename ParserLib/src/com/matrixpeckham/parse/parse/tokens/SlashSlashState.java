package com.matrixpeckham.parse.parse.tokens;

import java.io.IOException;
import java.io.PushbackReader;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * A slashSlash state ignores everything up to an end-of-line and returns the
 * tokenizer's next token.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class SlashSlashState extends TokenizerState {

    /**
     * Ignore everything up to an end-of-line and return the tokenizer's next
     * token.
     *
     * @param r
     * @param theSlash
     * @return the tokenizer's next token
     * @throws java.io.IOException
     */
    @Override
    public Token nextToken(
            PushbackReader r, int theSlash, Tokenizer t)
            throws IOException {

        int c;
        while ((c = r.read()) != '\n' && c != '\r' && c >= 0) {
        }
        return t.nextToken();
    }

    private static final Logger LOG
            = Logger.getLogger(SlashSlashState.class.getName());

}
