package com.matrixpeckham.parse.examples.query;

import com.matrixpeckham.parse.engine.Axiom;
import com.matrixpeckham.parse.engine.Term;
import com.matrixpeckham.parse.parse.tokens.TokenTester;
import com.matrixpeckham.parse.utensil.TypeOrType;
import java.util.logging.Logger;

/**
 * This class tests that class <code>Jaql</code> can parse
 * random language elements.
 *
 * @see JaqlUe
 */
public class JaqlTester extends TokenTester<TypeOrType<Axiom, Term>, QueryBuilder> {

    /**
     *
     */
    static Speller speller;

    /**
     *
     */
    public JaqlTester() {
        super(new JaqlParser(speller()).start());
    }

    /**
     * A Jaql target is a <code>QueryBuilder</code>.
     *
     * @return
     */
    @Override
    protected QueryBuilder freshTarget() {
        return new QueryBuilder(speller());
    }

    /**
     * Run a test.
     *
     * @param args
     */
    public static void main(String[] args) {
        new JaqlTester().test();
    }

    /*
     * Provide a spell that allows any class or variable name.
     */

    /**
     *
     * @return
     */
    protected synchronized static Speller speller() {
        if (speller == null) {
            speller = new MellowSpeller();
        }
        return speller;
    }

    private static final Logger LOG
            = Logger.getLogger(JaqlTester.class.getName());

}
