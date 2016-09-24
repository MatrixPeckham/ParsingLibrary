package com.matrixpeckham.parse.parse.chars;

import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Terminal;
import com.matrixpeckham.parse.utensil.PubliclyCloneable;
import static java.lang.Character.isDigit;
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
 * A Digit matches a digit from a character assembly.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 * @param <Val>
 * @param <Tar>
 */
public class Digit<Val, Tar extends PubliclyCloneable<Tar>>
        extends Terminal<Character, Val, Tar> {

    /**
     * Returns true if an assembly's next element is a digit.
     *
     * @param o an element from an assembly
     *
     * @return true, if an assembly's next element is a digit
     */
    @Override
    public boolean qualifies(Character o) {
        return isDigit(o);
    }

    /**
     * Create a set with one random digit.
     *
     * @return
     */
    @Override
    protected ArrayList<String> randomExpansion(int maxDepth, int depth) {
        char c = (char) (10 * random() + '0');
        ArrayList<String> v = new ArrayList<>();
        v.add(new String(new char[]{c}));
        return v;
    }

    @Override
    public Parser<Character, Val, Tar> copy() {
        Digit<Val, Tar> t = new Digit<>();
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
    protected String unvisitedString(
            ArrayList<Parser<Character, Val, Tar>> visited, int level) {
        return "D";
    }

    private static final Logger LOG = Logger.getLogger(Digit.class.getName());

}
