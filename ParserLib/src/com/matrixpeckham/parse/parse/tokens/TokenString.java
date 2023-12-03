package com.matrixpeckham.parse.parse.tokens;

import static com.matrixpeckham.parse.parse.tokens.Token.TT_EOF;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * A TokenString is like a String, but it is a series of
 * Tokens rather than a series of chars. Once a TokenString is
 * created, it is "immutable", meaning it cannot change. This
 * lets you freely copy TokenStrings without worrying about
 * their state.
 */
public class TokenString {

    /**
     * the tokens in this tokenString
     */
    protected Token tokens[];

    /**
     * Constructs a tokenString from the supplied tokens.
     *
     * @param tokens the tokens to use
     */
    public TokenString(Token[] tokens) {
        this.tokens = Arrays.copyOf(tokens, tokens.length);
    }

    /**
     * Constructs a tokenString from the supplied string.
     *
     * @param s the string to tokenize
     *
     */
    public TokenString(String s) {
        this(new Tokenizer(s));
    }

    /**
     * Constructs a tokenString from the supplied reader and tokenizer.
     *
     * @param t the tokenizer that will produces the tokens
     *
     */
    public TokenString(Tokenizer t) {
        ArrayList<Token> v = new ArrayList<>();
        try {
            while (true) {
                Token tok = t.nextToken();
                if (tok.ttype() == TT_EOF) {
                    break;
                }
                v.add(tok);
            }
        } catch (IOException e) {
            throw new InternalError(
                    "Problem tokenizing string: " + e);
        }
        tokens = new Token[v.size()];
        v.toArray(tokens);
    }

    /**
     * Returns the number of tokens in this tokenString.
     *
     * @return the number of tokens in this tokenString
     */
    public int length() {
        return tokens.length;
    }

    /**
     * Returns the token at the specified index.
     *
     * @param i the index of the desired token
     *
     * @return token the token at the specified index
     */
    public Token tokenAt(int i) {
        return tokens[i];
    }

    /**
     * Returns a string representation of this tokenString.
     *
     * @return a string representation of this tokenString
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < tokens.length; i++) {
            if (i > 0) {
                buf.append(" ");
            }
            buf.append(tokens[i]);
        }
        return buf.toString();
    }

    private static final Logger LOG
            = Logger.getLogger(TokenString.class.getName());

}
