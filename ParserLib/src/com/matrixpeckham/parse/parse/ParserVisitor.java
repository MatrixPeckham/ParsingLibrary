package com.matrixpeckham.parse.parse;

import com.matrixpeckham.parse.utensil.PubliclyCloneable;
import java.util.ArrayList;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * This class provides a "visitor" hierarchy in support of the Visitor pattern
 * -- see the book, "Design Patterns" for an explanation of this pattern.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 * @param <Tok>
 * @param <Val>
 * @param <Tar>
 */
public abstract class ParserVisitor<Tok, Val, Tar extends PubliclyCloneable<Tar>> {

    /**
     * Visit an alternation.
     *
     * @param a the parser to visit
     *
     * @param visited a collection of previously visited parsers
     *
     */
    public abstract void visitAlternation(
            Alternation<Tok, Val, Tar> a,
            ArrayList<Parser<Tok, Val, Tar>> visited);

    /**
     * Visit an empty parser.
     *
     * @param e the parser to visit
     *
     * @param visited a collection of previously visited parsers
     *
     */
    public abstract void visitEmpty(Empty<Tok, Val, Tar> e,
            ArrayList<Parser<Tok, Val, Tar>> visited);

    /**
     * Visit a repetition.
     *
     * @param r the parser to visit
     *
     * @param visited a collection of previously visited parsers
     *
     */
    public abstract void visitRepetition(
            Repetition<Tok, Val, Tar> r,
            ArrayList<Parser<Tok, Val, Tar>> visited);

    /**
     * Visit a sequence.
     *
     * @param s the parser to visit
     *
     * @param visited a collection of previously visited parsers
     *
     */
    public abstract void visitSequence(Sequence<Tok, Val, Tar> s,
            ArrayList<Parser<Tok, Val, Tar>> visited);

    /**
     * Visit a terminal.
     *
     * @param t the parser to visit
     *
     * @param visited a collection of previously visited parsers
     *
     */
    public abstract void visitTerminal(Terminal<Tok, Val, Tar> t,
            ArrayList<Parser<Tok, Val, Tar>> visited);
}
