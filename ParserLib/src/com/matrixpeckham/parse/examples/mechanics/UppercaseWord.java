package com.matrixpeckham.parse.examples.mechanics;

import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.Word;
import com.matrixpeckham.parse.utensil.PubliclyCloneable;
import static java.lang.Character.isUpperCase;
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
 * This class shows the how to introduce a new type of terminal, specifically
 * for recognizing uppercase words.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 * @param <Val>
 * @param <Tar>
 */
public class UppercaseWord<Val, Tar extends PubliclyCloneable<Tar>>
        extends Word<Val, Tar> {

    /**
     * Returns true if an assembly's next element is an upper case word.
     *
     * @param o an element from a assembly
     *
     * @return true, if a assembly's next element is an upper case word
     */
    @Override
    protected boolean qualifies(Token o) {
        Token t = o;
        if (!t.isWord()) {
            return false;
        }
        String word = t.sval();
        return word.length() > 0 && isUpperCase(word.charAt(0));
    }

    @Override
    public Parser<Token, Val, Tar> copy() {
        UppercaseWord<Val, Tar> t = new UppercaseWord<>();
        t.assembler = assembler;
        t.discard = discard;
        t.name = name;
        return t;
    }

    /**
     * Create a set with one random uppercase word (with 3 to 7 characters).
     *
     * @return
     */
    @Override
    protected ArrayList<String> randomExpansion(int maxDepth, int depth) {
        int n = (int) (5.0 * random()) + 3;

        char[] letters = new char[n];
        for (int i = 0; i < n; i++) {
            int c = (int) (26.0 * random()) + 'A';
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
     * @see ProductionRule#toString()
     */
    @Override
    protected String unvisitedString(ArrayList<Parser<Token, Val, Tar>> visited,
            int level) {
        return "Word";
    }

    private static final Logger LOG
            = Logger.getLogger(UppercaseWord.class.getName());

}
