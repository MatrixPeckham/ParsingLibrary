package com.matrixpeckham.parse.parse.tokens;

import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.utensil.PubliclyCloneable;
import java.util.logging.Logger;

/**
 * A TokenAssembly is an Assembly whose elements are Tokens.
 * Tokens are, roughly, the chunks of text that a <code>
 * Tokenizer</code> returns.
 */
public class TokenAssembly<Val, Tar extends PubliclyCloneable<Tar>>
        extends Assembly<Token, Val, Tar> {

    /**
     * the "string" of tokens this assembly will consume
     */
    protected TokenString tokenString;

    /**
     * Constructs a TokenAssembly on a TokenString constructed from the given
     * String.
     *
     * @param s string the string to consume
     */
    public TokenAssembly(String s) {
        this(new TokenString(s));
    }

    /**
     * Constructs a TokenAssembly on a TokenString constructed from the given
     * Tokenizer.
     *
     * @param t the tokenizer to consume tokens from
     */
    public TokenAssembly(Tokenizer t) {
        this(new TokenString(t));
    }

    /**
     * Constructs a TokenAssembly from the given TokenString.
     *
     * @param tokenString the tokenString to consume
     */
    public TokenAssembly(TokenString tokenString) {
        this.tokenString = tokenString;
    }

    private TokenAssembly(TokenAssembly<Val, Tar> t) {
        super(t);
        if (t.tokenString != null) {
            tokenString = t.tokenString;
        }
    }

    @Override
    public TokenAssembly<Val, Tar> copy() {
        return new TokenAssembly<>(this);
    }

    /**
     * Returns a textual representation of the amount of this tokenAssembly that
     * has been consumed.
     *
     * @param delimiter the mark to show between consumed elements
     *
     * @return a textual description of the amount of this assembly that has
     *         been consumed
     */
    @Override
    public String consumed(String delimiter) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < elementsConsumed(); i++) {
            if (i > 0) {
                buf.append(delimiter);
            }
            buf.append(tokenString.tokenAt(i));
        }
        return buf.toString();
    }

    /**
     * Returns the default string to show between elements consumed or
     * remaining.
     *
     * @return the default string to show between elements consumed or remaining
     */
    @Override
    public String defaultDelimiter() {
        return "/";
    }

    /**
     * Returns the number of elements in this assembly.
     *
     * @return the number of elements in this assembly
     */
    @Override
    public int length() {
        return tokenString.length();
    }

    /**
     * Returns the next token.
     *
     * @return the next token from the associated token string.
     *
     * @exception ArrayIndexOutOfBoundsException if there are no more tokens in
     *                                           this tokenizer's string.
     */
    @Override
    public Token next() {
        return tokenString.tokenAt(index++);
    }

    /**
     * Shows the next object in the assembly, without removing it
     *
     * @return the next object
     *
     */
    @Override
    public Object peek() {
        if (index < length()) {
            return tokenString.tokenAt(index);
        } else {
            return null;
        }
    }

    /**
     * Shows the next object in the assembly, without removing it
     *
     * @return the next object
     *
     */
    @Override
    public Token peekTok() {
        if (index < length()) {
            return tokenString.tokenAt(index);
        } else {
            return null;
        }
    }

    /**
     * Returns a textual representation of the amount of this tokenAssembly that
     * remains to be consumed.
     *
     * @param delimiter the mark to show between consumed elements
     *
     * @return a textual description of the amount of this assembly that remains
     *         to be consumed
     */
    @Override
    public String remainder(String delimiter) {
        StringBuilder buf = new StringBuilder();
        for (int i = elementsConsumed();
                i < tokenString.length();
                i++) {

            if (i > elementsConsumed()) {
                buf.append(delimiter);
            }
            buf.append(tokenString.tokenAt(i));
        }
        return buf.toString();
    }

    private static final Logger LOG
            = Logger.getLogger(TokenAssembly.class.getName());

}
