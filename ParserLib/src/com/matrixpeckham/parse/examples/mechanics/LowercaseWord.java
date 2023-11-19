package com.matrixpeckham.parse.examples.mechanics;

import static java.lang.Character.isLowerCase;
import static java.lang.Math.random;

import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.Word;
import com.matrixpeckham.parse.utensil.PubliclyCloneable;
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
 * This class shows the how to introduce a new type of terminal, specifically
 * for recognizing lowercase words.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 * @param <Val>
 * @param <Tar>
 */
public class LowercaseWord<Val, Tar extends PubliclyCloneable<Tar>>
        extends Word<Val, Tar> {

    /**
     * Returns true if an assembly's next element is a lower case word.
     *
     * @param o an element from a assembly
     *
     * @return true, if an assembly's next element is a lowercase word
     */
    @Override
    protected boolean qualifies(Token o) {
        Token t = o;
        if (!t.isWord()) {
            return false;
        }
        String word = t.sval();
        return word.length() > 0 && isLowerCase(word.charAt(0));
    }

    @Override
    public Parser<Token, Val, Tar> copy() {
        LowercaseWord<Val, Tar> t = new LowercaseWord<>();
        t.assembler = assembler;
        t.discard = discard;
        t.name = name;
        return t;
    }

    /**
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

    /**
     * Returns a textual description of this production.
     *
     * @param visited a list of productions already printed in this description
     *
     * @return string a textual description of this production
     *
     * @see Parser#toString()
     */
    @Override
    protected String unvisitedString(ArrayList<Parser<Token, Val, Tar>> visited,
            int level) {
        return "word";
    }

    private static final Logger LOG
            = Logger.getLogger(LowercaseWord.class.getName());

}
