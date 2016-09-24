package com.matrixpeckham.parse.parse.tokens;

import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Terminal;
import com.matrixpeckham.parse.utensil.PubliclyCloneable;
import static java.lang.Math.random;
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
 * A QuotedString matches a quoted string, like "this one" from a token
 * assembly.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 * @param <Val>
 * @param <Tar>
 */
public class QuotedString<Val, Tar extends PubliclyCloneable<Tar>>
        extends Terminal<Token, Val, Tar> {

    /**
     * Returns true if an assembly's next element is a quoted string.
     *
     * @param o an element from a assembly
     *
     * @return true, if a assembly's next element is a quoted string, like
     * "chubby cherubim".
     */
    @Override
    protected boolean qualifies(Token o) {
        Token t = o;
        return t.isQuotedString();
    }

    @Override
    public Parser<Token, Val, Tar> copy() {
        QuotedString<Val, Tar> t = new QuotedString<>();
        t.assembler = assembler;
        t.discard = discard;
        t.name = name;
        return t;
    }

    /**
     * Create a set with one random quoted string (with 2 to 6 characters).
     *
     * @return
     */
    @Override
    protected ArrayList<String> randomExpansion(int maxDepth, int depth) {
        int n = (int) (5.0 * random());

        char[] letters = new char[n + 2];
        letters[0] = '"';
        letters[n + 1] = '"';

        for (int i = 0; i < n; i++) {
            int c = (int) (26.0 * random()) + 'a';
            letters[i + 1] = (char) c;
        }

        ArrayList<String> v = new ArrayList<>();
        v.add(new String(letters));
        return v;
    }

    /**
     * Returns a textual description of this parser.
     *
     * @param visited a list of parsers already printed in this description
     *
     * @return string a textual description of this parser
     *
     * @see Parser#toString()
     */
    @Override
    protected String unvisitedString(ArrayList<Parser<Token, Val, Tar>> visited,
            int level) {
        return "QuotedString";
    }

    private static final Logger LOG
            = Logger.getLogger(QuotedString.class.getName());

}
