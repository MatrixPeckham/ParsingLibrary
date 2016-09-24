package com.matrixpeckham.parse.parse.chars;

import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.ParserTester;
import com.matrixpeckham.parse.utensil.PubliclyCloneable;
import java.util.logging.Logger;

/**
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
     * assembly method comment.
     *
     * @param s
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
