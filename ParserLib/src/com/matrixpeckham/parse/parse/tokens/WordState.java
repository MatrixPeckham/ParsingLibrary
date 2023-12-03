package com.matrixpeckham.parse.parse.tokens;

import static com.matrixpeckham.parse.parse.tokens.Token.TT_WORD;
import static java.lang.String.copyValueOf;
import static java.lang.System.arraycopy;

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
 * A wordState returns a word from a reader. Like other states, a tokenizer
 * transfers the job of reading to this state, depending on an initial
 * character. Thus, the tokenizer decides which characters may begin a word, and
 * this state determines which characters may appear as a second or later
 * character in a word. These are typically different sets of characters; in
 * particular, it is typical for digits to appear as parts of a word, but not as
 * the initial character of a word.
 * <p>
 * <p>
 * By default, the following characters may appear in a word. The method
 * <code>setWordChars()</code> allows customizing this.
 * <p>
 * <blockquote><pre>
 *     From    To
 *      'a', 'z'
 *      'A', 'Z'
 *      '0', '9'
 *
 *     as well as: minus sign, underscore, and apostrophe.
 *
 * </pre></blockquote>
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class WordState extends TokenizerState {

    /**
     *
     */
    protected char charbuf[] = new char[16];

    /**
     *
     */
    protected boolean wordChar[] = new boolean[256];

    /**
     * Constructs a word state with a default idea of what characters are
     * admissible inside a word (as described in the class comment).
     * <p>
     */
    public WordState() {
        setWordChars('a', 'z', true);
        setWordChars('A', 'Z', true);
        setWordChars('0', '9', true);
        setWordChars('-', '-', true);
        setWordChars('_', '_', true);
        setWordChars('\'', '\'', true);
        setWordChars(0xc0, 0xff, true);
    }

    /**
     * Fatten up charbuf as necessary.
     *
     * @param i
     */
    protected void checkBufLength(int i) {
        if (i >= charbuf.length) {
            char nb[] = new char[charbuf.length * 2];
            arraycopy(charbuf, 0, nb, 0, charbuf.length);
            charbuf = nb;
        }
    }

    /**
     * Return a word token from a reader.
     *
     * @param r
     *
     * @return a word token from a reader
     *
     * @throws java.io.IOException
     */
    @Override
    public Token nextToken(PushbackReader r, int c, Tokenizer t)
            throws IOException {

        int i = 0;
        do {
            checkBufLength(i);
            charbuf[i++] = (char) c;
            c = r.read();
        } while (wordChar(c));

        if (c >= 0) {
            r.unread(c);
        }
        String sval = copyValueOf(charbuf, 0, i);
        return new Token(TT_WORD, sval, 0);
    }

    /**
     * Establish characters in the given range as valid characters for part of a
     * word after the first character. Note that the tokenizer must determine
     * which characters are valid as the beginning character of a word.
     *
     * @param from char
     *
     * @param to   char
     * @param b    true, if this state should allow characters in the given
     *             range
     *             as part of a word
     */
    public final void setWordChars(int from, int to, boolean b) {
        for (int i = from; i <= to; i++) {
            if (i >= 0 && i < wordChar.length) {
                wordChar[i] = b;
            }
        }
    }

    /**
     * Just a test of the wordChar array.
     *
     * @param c
     *
     * @return
     */
    protected boolean wordChar(int c) {
        if (c >= 0 && c < wordChar.length) {
            return wordChar[c];
        }
        return false;
    }

    private static final Logger LOG
            = Logger.getLogger(WordState.class.getName());

}
