package com.matrixpeckham.parse.parse;

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
 * An <code>Empty</code> parser matches any assembly once, and applies its
 * assembler that one time.
 * <p>
 * Language elements often contain empty parts. For example, a language may at
 * some point allow a list of parameters in parentheses, and may allow an empty
 * list. An empty parser makes it easy to match, within the parenthesis, either
 * a list of parameters or "empty".
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 * @param <Tok>
 * @param <Val>
 * @param <Tar>
 *
 */
public class Empty<Tok, Val, Tar extends PubliclyCloneable<Tar>>
        extends Parser<Tok, Val, Tar> {

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
        pv.visitEmpty(this, visited);
    }

    @Override
    public Parser<Tok, Val, Tar> copy() {
        Empty<Tok, Val, Tar> e = new Empty<>();
        e.name = name;
        e.assembler = assembler;
        return e;
    }

    /**
     * Given a set of assemblies, this method returns the set as a successful
     * match.
     *
     * @return the input set of states
     *
     * @param in a vector of assemblies to match against
     *
     */
    @Override
    public ArrayList<Assembly<Tok, Val, Tar>> match(
            ArrayList<Assembly<Tok, Val, Tar>> in) {
        return elementClone(in);
    }
    /*
     * There really is no way to expand an empty parser, so
     * return an empty vector.
     */

    /**
     *
     * @param maxDepth
     * @param depth
     * @return
     */
    @Override
    protected ArrayList<String> randomExpansion(int maxDepth, int depth) {
        return new ArrayList<>();
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
        return " empty ";
    }

    private static final Logger LOG = Logger.getLogger(Empty.class.getName());

}
