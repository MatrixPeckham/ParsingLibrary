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
 * A whitespace state ignores whitespace (such as blanks and tabs), and returns
 * the tokenizer's next token. By default, all characters from 0 to 32 are
 * whitespace.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class WhitespaceState extends TokenizerState {

    /**
     *
     */
    protected boolean whitespaceChar[] = new boolean[256];

    /**
     * Constructs a whitespace state with a default idea of what characters are,
     * in fact, whitespace.
     *
     */
    public WhitespaceState() {
        setWhitespaceChars(0, ' ', true);
    }

    /**
     * Ignore whitespace (such as blanks and tabs), and return the tokenizer's
     * next token.
     *
     * @param aWhitespaceChar
     * @return the tokenizer's next token
     * @throws java.io.IOException
     */
    @Override
    public Token nextToken(
            PushbackReader r, int aWhitespaceChar, Tokenizer t)
            throws IOException {

        int c;
        do {
            c = r.read();
        } while (c >= 0 && c < whitespaceChar.length && whitespaceChar[c]);

        if (c >= 0) {
            r.unread(c);
        }
        return t.nextToken();
    }

    /**
     * Establish the given characters as whitespace to ignore.
     *
     * @param from char
     *
     * @param to char
     * @param b true, if this state should ignore characters in the given range
     */
    public final void setWhitespaceChars(int from, int to, boolean b) {
        for (int i = from; i <= to; i++) {
            if (i >= 0 && i < whitespaceChar.length) {
                whitespaceChar[i] = b;
            }
        }
    }

    private static final Logger LOG
            = Logger.getLogger(WhitespaceState.class.getName());

}
