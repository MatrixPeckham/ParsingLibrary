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
 * A Word matches a word from a token assembly.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 * @param <Val>
 * @param <Tar>
 */
public class Word<Val, Tar extends PubliclyCloneable<Tar>>
        extends Terminal<Token, Val, Tar> {

    /**
     * Returns true if an assembly's next element is a word.
     *
     * @param o an element from an assembly
     *
     * @return true, if an assembly's next element is a word
     */
    @Override
    protected boolean qualifies(Token o) {
        Token t = o;
        return t.isWord();
    }

    /**
     * Create a set with one random word (with 3 to 7 characters).
     *
     * @return
     */
    @Override
    protected ArrayList<String> randomExpansion(int maxDepth, int depth) {
        int n = (int) (5.0 * random()) + 3;

        char[] letters = new char[n];
        for (int i = 0; i < n; i++) {
            int c = (int) (26.0 * random()) + 'a';
            letters[i] = (char) c;
        }

        ArrayList<String> v = new ArrayList<>();
        v.add(new String(letters));
        return v;
    }

    @Override
    public Parser<Token, Val, Tar> copy() {
        Word<Val, Tar> t = new Word<>();
        t.assembler = assembler;
        t.discard = discard;
        t.name = name;
        return t;
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
        return "Word";
    }

    private static final Logger LOG = Logger.getLogger(Word.class.getName());

}
