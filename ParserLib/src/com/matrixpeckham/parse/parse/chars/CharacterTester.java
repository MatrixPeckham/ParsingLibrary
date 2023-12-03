package com.matrixpeckham.parse.parse.chars;

import com.matrixpeckham.parse.parse.*;
import com.matrixpeckham.parse.utensil.PubliclyCloneable;
import java.util.logging.Logger;

/**
 * Tester for character based parsers.
 *
 * @author Owner
 * @param <Val>
 * @param <Tar>
 */
public class CharacterTester<Val, Tar extends PubliclyCloneable<Tar>>
        extends ParserTester<Character, Val, Tar> {

    /**
     *
     * @param p
     */
    public CharacterTester(Parser<Character, Val, Tar> p) {
        super(p);
    }

    /**
     * Creates a new assembly for character based assemblies.
     *
     * @param s
     *
     * @return
     */
    @Override
    protected Assembly<Character, Val, Tar> assembly(String s) {
        return new CharacterAssembly<>(s);
    }

    /**
     *
     * @return java.lang.String
     */
    @Override
    protected String separator() {
        return "";
    }

    private static final Logger LOG
            = Logger.getLogger(CharacterTester.class.getName());

}
