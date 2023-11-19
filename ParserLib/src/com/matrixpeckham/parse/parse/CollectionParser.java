package com.matrixpeckham.parse.parse;

import com.matrixpeckham.parse.utensil.PubliclyCloneable;
import java.util.ArrayList;
import java.util.Iterator;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * This class abstracts the behavior common to parsers that consist of a series
 * of other parsers.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 * @param <Tok>
 * @param <Val>
 * @param <Tar>
 */
public abstract class CollectionParser<Tok, Val, Tar extends PubliclyCloneable<Tar>>
        extends Parser<Tok, Val, Tar> {

    /**
     * the parsers this parser is a collection of
     */
    protected ArrayList<Parser<Tok, Val, Tar>> subparsers = new ArrayList<>();

    /**
     * Supports subclass constructors with no arguments.
     */
    public CollectionParser() {
    }

    /**
     * Supports subclass constructors with a name argument
     *
     *
     * @param name the name of this parser
     */
    public CollectionParser(String name) {
        super(name);
    }

    /**
     * A convenient way to construct a CollectionParser with the given parser.
     *
     * @param p
     */
    public CollectionParser(Parser<Tok, Val, Tar> p) {
        subparsers.add(p);
    }

    /**
     * A convenient way to construct a CollectionParser with the given parsers.
     *
     * @param p1
     * @param p2
     */
    public CollectionParser(Parser<Tok, Val, Tar> p1, Parser<Tok, Val, Tar> p2) {
        subparsers.add(p1);
        subparsers.add(p2);
    }

    /**
     * A convenient way to construct a CollectionParser with the given parsers.
     *
     * @param p1
     * @param p2
     * @param p3
     */
    public CollectionParser(Parser<Tok, Val, Tar> p1, Parser<Tok, Val, Tar> p2,
            Parser<Tok, Val, Tar> p3) {
        subparsers.add(p1);
        subparsers.add(p2);
        subparsers.add(p3);
    }

    /**
     * A convenient way to construct a CollectionParser with the given parsers.
     *
     * @param p1
     * @param p4
     * @param p2
     * @param p3
     */
    public CollectionParser(
            Parser<Tok, Val, Tar> p1,
            Parser<Tok, Val, Tar> p2,
            Parser<Tok, Val, Tar> p3,
            Parser<Tok, Val, Tar> p4) {
        //
        subparsers.add(p1);
        subparsers.add(p2);
        subparsers.add(p3);
        subparsers.add(p4);
    }

    /**
     * Adds a parser to the collection.
     *
     * @param e the parser to add
     *
     * @return this
     */
    public CollectionParser<Tok, Val, Tar> add(Parser<Tok, Val, Tar> e) {
        subparsers.add(e);
        return this;
    }

    /**
     * Return this parser's subparsers.
     *
     * @return ArrayList this parser's subparsers
     */
    public ArrayList<Parser<Tok, Val, Tar>> getSubparsers() {
        return subparsers;
    }

    /**
     * Helps to textually describe this CollectionParser.
     *
     * @return
     *
     * @return the string to place between parsers in the collection
     */
    protected abstract String toStringSeparator();

    /*
     * Returns a textual description of this parser.
     */

    /**
     *
     * @param visited
     * @param level
     *
     * @return
     */
    @Override
    protected String unvisitedString(ArrayList<Parser<Tok, Val, Tar>> visited,
            int level) {
        StringBuilder buf = new StringBuilder("<");
        boolean needSeparator = false;
        Iterator<Parser<Tok, Val, Tar>> e = subparsers.iterator();
        while (e.hasNext()) {
            if (needSeparator) {
                buf.append(toStringSeparator());
            }
            Parser<Tok, Val, Tar> next = e.next();
            buf.append(next.toString(visited, level));
            needSeparator = true;
        }
        buf.append(">");
        return buf.toString();
    }

}
