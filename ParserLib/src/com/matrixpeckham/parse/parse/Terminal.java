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
 * A <code>Terminal</code> is a parser that is not a composition of other
 * parsers. Terminals are "terminal" because they do not pass matching work on
 * to other parsers. The criterion that terminals use to check a match is
 * something other than another parser. Terminals are also the only parsers that
 * advance an assembly.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 * @param <Tok>
 * @param <Val>
 * @param <Tar>
 */
public class Terminal<Tok, Val, Tar extends PubliclyCloneable<Tar>>
        extends Parser<Tok, Val, Tar> {
    /*
     * whether or not this terminal should push itself upon an
     * assembly's stack after a successful match
     */

    /**
     *
     */
    protected boolean discard = false;

    /**
     * Constructs an unnamed terminal.
     */
    public Terminal() {
    }

    /**
     * Constructs a terminal with the given name.
     *
     * @param name A name to be known by.
     */
    public Terminal(String name) {
        super(name);
    }

    /**
     * Accept a "visitor" and a collection of previously visited parsers.
     *
     * @param pv the visitor to accept
     *
     *
     * @param visited a collection of previously visited parsers
     */
    @Override
    public void accept(ParserVisitor<Tok, Val, Tar> pv,
            ArrayList<Parser<Tok, Val, Tar>> visited) {
        pv.visitTerminal(this, visited);
    }

    @Override
    public Parser<Tok, Val, Tar> copy() {
        Terminal<Tok, Val, Tar> t = new Terminal<>();
        t.assembler = assembler;
        t.discard = discard;
        t.name = name;
        return t;
    }

    /**
     * A convenience method that sets discarding to be true.
     *
     * @return this
     */
    public Terminal<Tok, Val, Tar> discard() {
        return setDiscard(true);
    }

    /**
     * Given a collection of assemblies, this method matches this terminal
     * against all of them, and returns a new collection of the assemblies that
     * result from the matches.
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
        Iterator<Assembly<Tok, Val, Tar>> e = in.iterator();
        while (e.hasNext()) {
            Assembly<Tok, Val, Tar> a = e.next();
            Assembly<Tok, Val, Tar> b = matchOneAssembly(a);
            if (b != null) {
                out.add(b);
            }
        }
        return out;
    }

    /**
     * Returns an assembly equivalent to the supplied assembly, except that this
     * terminal will have been removed from the front of the assembly. As with
     * any parser, if the match succeeds, this terminal's assembler will work on
     * the assembly. If the match fails, this method returns null.
     *
     * @param in the assembly to match against
     *
     * @return a copy of the incoming assembly, advanced by this terminal
     */
    protected Assembly<Tok, Val, Tar> matchOneAssembly(
            Assembly<Tok, Val, Tar> in) {
        if (!in.hasNext()) {
            return null;
        }
        if (qualifies(in.peekTok())) {
            Assembly<Tok, Val, Tar> out = in.copy();
            Tok o = out.next();
            if (!discard) {
                out.pushTok(o);
            }
            return out;
        }
        return null;
    }

    /**
     * The mechanics of matching are the same for many terminals, except for the
     * check that the next element on the assembly qualifies as the type of
     * terminal this terminal looks for. This method performs that check.
     *
     * @param o an element from a assembly
     *
     * @return true, if the object is the kind of terminal this parser seeks
     */
    protected boolean qualifies(Tok o) {
        return true;
    }
    /*
     * By default, create a collection with this terminal's
     * string representation of itself. (Most subclasses
     * override this.)
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
        v.add(this.toString());
        return v;
    }

    /**
     * By default, terminals push themselves upon a assembly's stack, after a
     * successful match. This routine will turn off (or turn back on) that
     * behavior.
     *
     * @param discard true, if this terminal should push itself on a assembly's
     * stack
     *
     * @return this
     */
    public Terminal<Tok, Val, Tar> setDiscard(boolean discard) {
        this.discard = discard;
        return this;
    }
    /*
     * Returns a textual description of this parser.
     */

    /**
     *
     * @param visited
     * @return
     */
    @Override
    protected String unvisitedString(ArrayList<Parser<Tok, Val, Tar>> visited,
            int level) {
        return "any";
    }

    private static final Logger LOG = Logger.getLogger(Terminal.class.getName());

}
