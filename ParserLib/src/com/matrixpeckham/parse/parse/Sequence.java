package com.matrixpeckham.parse.parse;

import com.matrixpeckham.parse.utensil.PubliclyCloneable;
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
 * A <code>Sequence</code> object is a collection of parsers, all of which must
 * in turn match against an assembly for this parser to successfully match.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 * @param <Tok>
 * @param <Val>
 * @param <Tar>
 */
public class Sequence<Tok, Val, Tar extends PubliclyCloneable<Tar>>
        extends CollectionParser<Tok, Val, Tar> {

    /**
     * Constructs a nameless sequence.
     */
    public Sequence() {
    }

    /**
     * Constructs a sequence with the given name.
     *
     * @param name a name to be known by
     */
    public Sequence(String name) {
        super(name);
    }

    /**
     * A convenient way to construct a CollectionParser with the given parser.
     *
     * @param p
     */
    public Sequence(Parser<Tok, Val, Tar> p) {
        super(p);
    }

    /**
     * A convenient way to construct a CollectionParser with the given parsers.
     *
     * @param p1
     * @param p2
     */
    public Sequence(Parser<Tok, Val, Tar> p1, Parser<Tok, Val, Tar> p2) {
        super(p1, p2);
    }

    /**
     * A convenient way to construct a CollectionParser with the given parsers.
     *
     * @param p1
     * @param p2
     * @param p3
     */
    public Sequence(Parser<Tok, Val, Tar> p1, Parser<Tok, Val, Tar> p2,
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
    public Sequence(
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
        pv.visitSequence(this, visited);
    }

    @Override
    public Parser<Tok, Val, Tar> copy() {
        Sequence<Tok, Val, Tar> s = new Sequence<>(name);
        s.subparsers = elementClone(subparsers);
        s.assembler = assembler;
        return s;
    }

    /**
     * Given a set of assemblies, this method matches this sequence against all
     * of them, and returns a new set of the assemblies that result from the
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
        ArrayList<Assembly<Tok, Val, Tar>> out = in;
        Iterator<Parser<Tok, Val, Tar>> e = subparsers.iterator();
        while (e.hasNext()) {
            Parser<Tok, Val, Tar> p = e.next();
            out = p.matchAndAssemble(out);
            if (out.isEmpty()) {
                return out;
            }
        }
        return out;
    }
    /*
     * Create a random expansion for each parser in this
     * sequence and return a collection of all these expansions.
     */

    /**
     *
     * @param maxDepth
     * @param depth
     * @return
     */
    @Override
    protected ArrayList<String> randomExpansion(int maxDepth, int depth) {
        ArrayList<String> v = new ArrayList<>();
        Iterator<Parser<Tok, Val, Tar>> e = subparsers.iterator();
        while (e.hasNext()) {
            Parser<Tok, Val, Tar> p = e.next();
            ArrayList<String> w = p.randomExpansion(maxDepth, depth++);
            Iterator<String> f = w.iterator();
            while (f.hasNext()) {
                v.add(f.next());
            }
        }
        return v;
    }
    /*
     * Returns the string to show between the parsers this
     * parser is a sequence of. This is an empty string,
     * since convention indicates sequence quietly. For
     * example, note that in the regular expression
     * <code>(a|b)c</code>, the lack of a delimiter between
     * the expression in parentheses and the 'c' indicates a
     * sequence of these expressions.
     */

    @Override
    protected String toStringSeparator() {
        return "";
    }

    private static final Logger LOG = Logger.getLogger(Sequence.class.getName());

}
