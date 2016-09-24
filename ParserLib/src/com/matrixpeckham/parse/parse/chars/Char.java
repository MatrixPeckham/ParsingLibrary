package com.matrixpeckham.parse.parse.chars;

import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Terminal;
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
 * A Char matches a character from a character assembly.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 * @param <Val>
 * @param <Tar>
 */
public class Char<Val, Tar extends PubliclyCloneable<Tar>>
        extends Terminal<Character, Val, Tar> {

    /**
     * Returns true every time, since this class assumes it is working against a
     * CharacterAssembly.
     *
     * @param o ignored
     *
     * @return true, every time, since this class assumes it is working against
     * a CharacterAssembly
     */
    @Override
    public boolean qualifies(Character o) {
        return true;
    }

    @Override
    public Parser<Character, Val, Tar> copy() {
        Char<Val, Tar> t = new Char<>();
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
        return "C";
    }

    private static final Logger LOG = Logger.getLogger(Char.class.getName());

}
