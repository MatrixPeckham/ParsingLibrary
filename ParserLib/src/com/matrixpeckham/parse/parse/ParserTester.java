package com.matrixpeckham.parse.parse;

import com.matrixpeckham.parse.utensil.PubliclyCloneable;
import java.util.ArrayList;
import java.util.Iterator;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * This class generates random language elements for a parser and tests that the
 * parser can accept them.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 * @param <Tok>
 * @param <Val>
 * @param <Tar>
 */
public abstract class ParserTester<Tok, Val, Tar extends PubliclyCloneable<Tar>> {

    /**
     * Return a subset of the supplied vector of assemblies, filtering for
     * assemblies that have been completely matched.
     *
     * @param <T1>
     * @param <V>
     * @param <T2>
     * @param in a collection of partially or completely matched assemblies
     *
     * @return a collection of completely matched assemblies
     */
    public static <T1, V, T2 extends PubliclyCloneable<T2>> ArrayList<Assembly<T1, V, T2>> completeMatches(
            ArrayList<Assembly<T1, V, T2>> in) {
        ArrayList<Assembly<T1, V, T2>> out = new ArrayList<>();
        Iterator<Assembly<T1, V, T2>> e = in.iterator();
        while (e.hasNext()) {
            Assembly<T1, V, T2> a = e.next();
            if (!a.hasNext()) {
                out.add(a);
            }
        }
        return out;
    }
    /*
     * Give subclasses a chance to provide fresh target at
     * the beginning of a parse.
     */

    protected boolean logTestStrings = true;

    protected Parser<Tok, Val, Tar> p;

    /**
     * Constructs a tester for the given parser.
     *
     * @param p
     */
    public ParserTester(Parser<Tok, Val, Tar> p) {
        this.p = p;
    }
    /*
     * Subclasses must override this, to produce an assembly
     * from the given (random) string.
     */

    protected abstract Assembly<Tok, Val, Tar> assembly(String s);
    /*
     * Generate a random language element, and return true if
     * the parser cannot unambiguously parse it.
     */

    protected boolean canGenerateProblem(int depth) {
        String s = p.randomInput(depth, separator());
        logTestString(s);
        Assembly<Tok, Val, Tar> a = assembly(s);
        a.setTarget(freshTarget());
        ArrayList<Assembly<Tok, Val, Tar>> in = new ArrayList<>();
        in.add(a);
        ArrayList<Assembly<Tok, Val, Tar>> out = completeMatches(p.match(in));
        if (out.size() != 1) {
            logProblemFound(s, out.size());
            return true;
        }
        return false;
    }

    protected Tar freshTarget() {
        return null;
    }
    /*
     * This method is broken out to allow subclasses to create
     * less verbose tester, or to direct logging to somewhere
     * other than System.out.
     */

    protected void logDepthChange(int depth) {
        System.out.println("Testing depth " + depth + "...");
    }
    /*
     * This method is broken out to allow subclasses to create
     * less verbose tester, or to direct logging to somewhere
     * other than System.out.
     */

    protected void logPassed() {
        System.out.println("No problems found.");
    }
    /*
     * This method is broken out to allow subclasses to create
     * less verbose tester, or to direct logging to somewhere
     * other than System.out.
     */

    protected void logProblemFound(String s, int matchSize) {
        System.out.println("Problem found for string:");
        System.out.println(s);
        if (matchSize == 0) {
            System.out.println(
                    "Parser cannot match this apparently " + "valid string.");
        } else {
            System.out.println(
                    "The parser found " + matchSize
                    + " ways to parse this string.");
        }
    }
    /*
     * This method is broken out to allow subclasses to create
     * less verbose tester, or to direct logging to somewhere
     * other than System.out.
     */

    protected void logTestString(String s) {
        if (logTestStrings) {
            System.out.println("    Testing string " + s);
        }
    }
    /*
     * By default, place a blank between randomly generated
     * "words" of a language.
     */

    protected String separator() {
        return " ";
    }

    /**
     * Set the boolean which determines if this class displays every test
     * string.
     *
     * @param logTestStrings true, if the user wants to see every test string
     */
    public void setLogTestStrings(boolean logTestStrings) {
        this.logTestStrings = logTestStrings;
    }

    /**
     * Create a series of random language elements, and test that the parser can
     * unambiguously parse each one.
     */
    public void test() {
        for (int depth = 2; depth < 8; depth++) {
            logDepthChange(depth);
            for (int k = 0; k < 100; k++) {
                if (canGenerateProblem(depth)) {
                    return;
                }
            }
        }
        logPassed();
    }

}
