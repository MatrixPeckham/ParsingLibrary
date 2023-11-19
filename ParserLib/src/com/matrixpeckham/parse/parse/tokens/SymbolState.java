package com.matrixpeckham.parse.parse.tokens;

import static com.matrixpeckham.parse.parse.tokens.Token.TT_SYMBOL;

import java.io.IOException;
import java.io.PushbackReader;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * The idea of a symbol is a character that stands on its own, such as an
 * ampersand or a parenthesis. For example, when tokenizing the expression <code>(isReady)&amp;
 * (isWilling) </code>, a typical tokenizer would return 7 tokens, including one
 * for each parenthesis and one for the ampersand. Thus a series of symbols such
 * as <code>)&amp;( </code> becomes three tokens, while a series of letters such
 * as
 * <code>isReady</code> becomes a single word token.
 * <p>
 * Multi-character symbols are an exception to the rule that a symbol is a
 * standalone character. For example, a tokenizer may want less-than-or-equals
 * to tokenize as a single token. This class provides a method for establishing
 * which multi-character symbols an object of this class should treat as single
 * symbols. This allows, for example, <code>"cat &lt;= dog"</code> to tokenize
 * as
 * three tokens, rather than splitting the less-than and equals symbols into
 * separate tokens.
 * <p>
 * By default, this state recognizes the following multi- character symbols: <code>!=, :-, &lt;=,
 * &gt;=</code>
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class SymbolState extends TokenizerState {

    SymbolRootNode symbols = new SymbolRootNode();

    /**
     * Constructs a symbol state with a default idea of what multi-character
     * symbols to accept (as described in the class comment).
     * <p>
     */
    public SymbolState() {
        add("!=");
        add(":-");
        add("<=");
        add(">=");
    }

    /**
     * Add a multi-character symbol.
     *
     * @param s the symbol to add, such as "=:="
     */
    public final void add(String s) {
        symbols.add(s);
    }

    /**
     * Return a symbol token from a reader.
     *
     * @param r
     * @param first
     *
     * @return a symbol token from a reader
     *
     * @throws java.io.IOException
     */
    @Override
    public Token nextToken(
            PushbackReader r, int first, Tokenizer t)
            throws IOException {

        String s = symbols.nextSymbol(r, first);
        return new Token(TT_SYMBOL, s, 0);
    }

    private static final Logger LOG
            = Logger.getLogger(SymbolState.class.getName());

}
