package com.matrixpeckham.parse.examples.sling;

import com.matrixpeckham.parse.imperative.Command;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.tokens.*;
import com.matrixpeckham.parse.utensil.TypeOrType;
import java.util.logging.Logger;

/**
 * This class tests that class <code>SlingParser</code> can
 * parse random language elements.
 */
public class SlingTester extends TokenTester<TypeOrType<SlingFunction, Command>, SlingTarget> {

    /**
     * Create a tester for the primary Sling parser.
     */
    public SlingTester() {
        super(new SlingParser().statement());
    }

    /*
     * Return an assembly for a given string, using the Sling
     * tokenizer.
     */

    @Override
    protected Assembly<Token, TypeOrType<SlingFunction, Command>, SlingTarget> assembly(
            String s) {
        Tokenizer t = new SlingParser().tokenizer();
        t.setString(s);
        return new TokenAssembly<>(t);
    }

    /*
     * The Sling parser expects a SlingTarget object as an
     * assembly's target. Normally, this target expects two
     * sliders to be available, but we send null's here since
     * there is no GUI in use during random testing.
     */

    /**
     *
     * @return
     */
    @Override
    protected SlingTarget freshTarget() {
        return new SlingTarget(null, null);
    }

    /**
     * Run a test.
     *
     * @param args
     */
    public static void main(String[] args) {
        new SlingTester().test();
    }

    private static final Logger LOG
            = Logger.getLogger(SlingTester.class.getName());

}
