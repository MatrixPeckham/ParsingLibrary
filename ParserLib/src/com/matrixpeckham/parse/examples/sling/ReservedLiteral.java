package com.matrixpeckham.parse.examples.sling;

import static com.matrixpeckham.parse.examples.reserved.WordOrReservedState.TT_RESERVED;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Terminal;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.utensil.PubliclyCloneable;
import java.util.ArrayList;
import java.util.logging.Logger;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * A <code>ReservedLiteral</code> matches a specific string that a tokenizer
 * returns as a reserved word type.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 * @param <Val>
 * @param <Tar>
 */
public class ReservedLiteral<Val, Tar extends PubliclyCloneable<Tar>>
        extends Terminal<Token, Val, Tar> {

    /**
     * the literal to match
     */
    protected Token literal;

    /**
     * Constructs a reserved literal that will match the specified string as a
     * reserved word.
     *
     * @param s the string to match as a token
     *
     */
    public ReservedLiteral(String s) {
        literal = new Token(
                TT_RESERVED, s, 0);
    }

    /**
     * Returns true if the literal this object equals a assembly's next element.
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

    @Override
    public Parser<Token, Val, Tar> copy() {
        ReservedLiteral<Val, Tar> t = new ReservedLiteral<>(literal.sval());
        t.assembler = assembler;
        t.discard = discard;
        t.name = name;
        return t;
    }

    /**
     * Returns a textual description of this parser.
     *
     * @param visited a list of parsers already printed in this description
     *
     * @return a textual description of this parser
     */
    @Override
    protected String unvisitedString(ArrayList<Parser<Token, Val, Tar>> visited,
            int level) {
        return literal.toString();
    }

    private static final Logger LOG
            = Logger.getLogger(ReservedLiteral.class.getName());

}
