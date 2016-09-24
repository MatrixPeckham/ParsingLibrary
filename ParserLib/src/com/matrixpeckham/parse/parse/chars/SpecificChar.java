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
 * A SpecificChar matches a specified character from a character assembly.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 * @param <Val>
 * @param <Tar>
 */
public class SpecificChar<Val, Tar extends PubliclyCloneable<Tar>>
        extends Terminal<Character, Val, Tar> {

    /**
     * the character to match
     */
    protected Character character;

    /**
     * Constructs a SpecificChar to match the specified char.
     *
     * @param c the character to match
     */
    public SpecificChar(char c) {
        this(Character.valueOf(c));
    }

    @Override
    public Parser<Character, Val, Tar> copy() {
        SpecificChar<Val, Tar> t = new SpecificChar<>(character);
        t.assembler = assembler;
        t.discard = discard;
        t.name = name;
        return t;
    }

    /**
     * Constructs a SpecificChar to match the specified character.
     *
     * @param character the character to match
     */
    public SpecificChar(Character character) {
        this.character = character;
    }

    /**
     * Returns true if an assembly's next element is equal to the character this
     * object was constructed with.
     *
     * @param o an element from an assembly
     *
     * @return true, if an assembly's next element is equal to the character
     * this object was constructed with
     */
    @Override
    public boolean qualifies(Character o) {
        Character c = o;
        return c.equals(character);
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
        return character.toString();
    }

    private static final Logger LOG
            = Logger.getLogger(SpecificChar.class.getName());

}
