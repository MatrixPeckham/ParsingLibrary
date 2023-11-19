package com.matrixpeckham.parse.parse.tokens;

import static com.matrixpeckham.parse.parse.tokens.Token.TT_SYMBOL;
import static java.lang.String.valueOf;

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
 * A Symbol matches a specific sequence, such as <code>&lt;</code>, or
 * <code>&lt;=</code> that a tokenizer returns as a symbol.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 * @param <Val>
 * @param <Tar>
 */
public class Symbol<Val, Tar extends PubliclyCloneable<Tar>>
        extends Terminal<Token, Val, Tar> {

    /**
     * the literal to match
     */
    protected Token symbol;

    /**
     * Constructs a symbol that will match the specified char.
     *
     * @param c the character to match. The char must be one that the tokenizer
     *          will return as a symbol token. This typically includes most characters
     *          except letters and digits.
     *
     */
    public Symbol(char c) {
        this(valueOf(c));
    }

    /**
     * Constructs a symbol that will match the specified sequence of characters.
     *
     * @param s the characters to match. The characters must be a sequence that
     *          the tokenizer will return as a symbol token, such as <code>&lt;=</code>.
     *
     */
    public Symbol(String s) {
        symbol = new Token(TT_SYMBOL, s, 0);
    }

    /**
     * Returns true if the symbol this object represents equals an assembly's
     * next element.
     *
     * @param o an element from an assembly
     *
     * @return true, if the specified symbol equals the next token from an
     *         assembly
     */
    @Override
    protected boolean qualifies(Token o) {
        return symbol.equals(o);
    }

    @Override
    public Parser<Token, Val, Tar> copy() {
        Symbol<Val, Tar> t = new Symbol<>(symbol.sval());
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
     * @return string a textual description of this parser
     *
     * @see Parser#toString()
     */
    @Override
    protected String unvisitedString(ArrayList<Parser<Token, Val, Tar>> visited,
            int level) {
        return symbol.toString();
    }

    private static final Logger LOG = Logger.getLogger(Symbol.class.getName());

}
