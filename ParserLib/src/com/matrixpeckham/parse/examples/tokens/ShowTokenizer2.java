package com.matrixpeckham.parse.examples.tokens;

import java.io.IOException;
import java.io.StreamTokenizer;
import static java.io.StreamTokenizer.TT_EOF;
import static java.io.StreamTokenizer.TT_NUMBER;
import static java.io.StreamTokenizer.TT_WORD;
import java.io.StringReader;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Show a <code>StreamTokenizer</code> object at work.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowTokenizer2 {

    /**
     * Show a StreamTokenizer at work.
     *
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String args[]) throws IOException {

        String s = "\"It's 123 blast-off!\", she said, // watch out!\n"
                + "and <= 3 'ticks' later /* wince */ , it's blast-off!";

        System.out.println(s);
        System.out.println();

        StreamTokenizer t = new StreamTokenizer(new StringReader(s));
        t.ordinaryChar('/');
        t.slashSlashComments(true);
        t.slashStarComments(true);

        boolean done = false;
        while (!done) {
            t.nextToken();
            switch (t.ttype) {
                case TT_EOF:
                    done = true;
                    break;
                case TT_WORD:
                case '\"':
                case '\'':
                    System.out.println("(" + t.sval + ")");
                    break;
                case TT_NUMBER:
                    System.out.println("(" + t.nval + ")");
                    break;
                default:
                    System.out.println(
                            "(" + (char) t.ttype + ")");
                    break;
            }
        }
    }

    private static final Logger LOG
            = Logger.getLogger(ShowTokenizer2.class.getName());

}
