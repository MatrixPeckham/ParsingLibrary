package com.matrixpeckham.parse.parse;

import com.matrixpeckham.parse.utensil.PubliclyCloneable;
import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * An <code>Alternation</code> object is a collection of parsers, any one of
 * which can successfully match against an assembly.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 * @param <Tok>
 * @param <Val>
 * @param <Tar>
 */
public class Alternation<Tok, Val, Tar extends PubliclyCloneable<Tar>>
        extends CollectionParser<Tok, Val, Tar> {

    /**
     * Constructs a nameless alternation.
     */
    public Alternation() {
    }

    /**
     * Constructs an alternation with the given name.
     *
     * @param name a name to be known by
     */
    public Alternation(String name) {
        super(name);
    }

    /**
     * A convenient way to construct a CollectionParser with the given parser.
     *
     * @param p
     */
    public Alternation(Parser<Tok, Val, Tar> p) {
        super(p);
    }

    /**
     * A convenient way to construct a CollectionParser with the given parsers.
     *
     * @param p1
     * @param p2
     */
    public Alternation(Parser<Tok, Val, Tar> p1, Parser<Tok, Val, Tar> p2) {
        super(p1, p2);
    }

    /**
     * A convenient way to construct a CollectionParser with the given parsers.
     *
     * @param p1
     * @param p2
     * @param p3
     */
    public Alternation(Parser<Tok, Val, Tar> p1, Parser<Tok, Val, Tar> p2,
            Parser<Tok, Val, Tar> p3) {
        super(p1, p2, p3);
    }

    /**
     * A convenient way to construct a CollectionParser with the given parsers.
     *
     * @param p1
     * @param p2
     * @param p3
     * @param p4
     */
    public Alternation(
            Parser<Tok, Val, Tar> p1,
            Parser<Tok, Val, Tar> p2,
            Parser<Tok, Val, Tar> p3,
            Parser<Tok, Val, Tar> p4) {
        super(p1, p2, p3, p4);
    }

    /**
     * Accept a "visitor" and a collection of previously visited parsers.
     *
     * @param pv the visitor to accept
     *
     * @param visited a collection of previously visited parsers
     */
    @Override
    public void accept(ParserVisitor<Tok, Val, Tar> pv,
            ArrayList<Parser<Tok, Val, Tar>> visited) {
        pv.visitAlternation(this, visited);
    }

    @Override
    public Parser<Tok, Val, Tar> copy() {
        Alternation<Tok, Val, Tar> a = new Alternation<>();
        a.name = name;
        a.assembler = assembler;
        a.subparsers = elementClone(subparsers);
        return a;
    }

    /**
     * Given a set of assemblies, this method matches this alternation against
     * all of them, and returns a new set of the assemblies that result from the
     * matches.
     *
     * @return a ArrayList of assemblies that result from matching against a
     * beginning set of assemblies
     *
     * @param in a vector of assemblies to match against
     *
     */
    @Override
    public ArrayList<Assembly<Tok, Val, Tar>> match(
            ArrayList<Assembly<Tok, Val, Tar>> in) {
        ArrayList<Assembly<Tok, Val, Tar>> out = new ArrayList<>();
        Iterator<Parser<Tok, Val, Tar>> e = subparsers.iterator();
        while (e.hasNext()) {
            Parser<Tok, Val, Tar> p = e.next();
            add(out, p.matchAndAssemble(in));
        }
        return out;
    }
    /*
     * Create a random collection of elements that correspond to
     * this alternation.
     */

    /**
     *
     * @param maxDepth
     * @param depth
     * @return
     */
    @Override
    protected ArrayList<String> randomExpansion(int maxDepth, int depth) {
        if (depth >= maxDepth) {
            return randomSettle(maxDepth, depth);
        }
        double n = subparsers.size();
        int i = (int) (n * random());
        Parser<Tok, Val, Tar> j = subparsers.get(i);
        return j.randomExpansion(maxDepth, depth + 1);
    }
    /*
     * This method is similar to randomExpansion, but it will
     * pick a terminal if one is available.
     */

    /**
     *
     * @param maxDepth
     * @param depth
     * @return
     */
    protected ArrayList<String> randomSettle(int maxDepth, int depth) {

        // which alternatives are terminals?
        ArrayList<Parser<Tok, Val, Tar>> terms = new ArrayList<>();
        Iterator<Parser<Tok, Val, Tar>> e = subparsers.iterator();
        while (e.hasNext()) {
            Parser<Tok, Val, Tar> j = e.next();
            if (j instanceof Terminal) {
                terms.add(j);
            }
        }

        // pick one of the terminals or, if there are no
        // terminals, pick any subparser
        ArrayList<Parser<Tok, Val, Tar>> which = terms;
        if (terms.isEmpty()) {
            which = subparsers;
        }

        double n = which.size();
        int i = (int) (n * random());
        Parser<Tok, Val, Tar> p = which.get(i);
        return p.randomExpansion(maxDepth, depth + 1);
    }
    /*
     * Returns the string to show between the parsers this
     * parser is an alternation of.
     */

    @Override
    protected String toStringSeparator() {
        return "|";
    }

    private static final Logger LOG
            = Logger.getLogger(Alternation.class.getName());

}
