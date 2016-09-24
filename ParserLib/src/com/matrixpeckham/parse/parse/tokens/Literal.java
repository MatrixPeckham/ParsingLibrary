package com.matrixpeckham.parse.parse.tokens;

import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Terminal;
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
 * A Literal matches a specific String from an assembly.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 * @param <Val>
 * @param <Tar>
 */
public class Literal<Val, Tar extends PubliclyCloneable<Tar>>
        extends Terminal<Token, Val, Tar> {

    /**
     * the literal to match
     */
    protected Token literal;

    /**
     * Constructs a literal that will match the specified string.
     *
     * @param s the string to match as a token
     *
     */
    public Literal(String s) {
        literal = new Token(s);
    }

    @Override
    public Parser<Token, Val, Tar> copy() {
        Literal<Val, Tar> t = new Literal<>(literal.sval());
        t.assembler = assembler;
        t.discard = discard;
        t.name = name;
        return t;
    }

    /**
     * Returns true if the literal this object equals an assembly's next
     * element.
     *
     * @param o an element from an assembly
     *
     * @return true, if the specified literal equals the next token from an
     * assembly
     */
    @Override
    protected boolean qualifies(Token o) {
        return literal.equals(o);
    }

    /**
     * Returns a textual description of this parser.
     *
     * @param visited a list of parsers already printed in this description
     *
     * @return string a textual description of this parser
     *
     * @see Parser#toString()
     */
    @Override
    protected String unvisitedString(ArrayList<Parser<Token, Val, Tar>> visited,
            int level) {
        return literal.toString();
    }

    private static final Logger LOG = Logger.getLogger(Literal.class.getName());

}
