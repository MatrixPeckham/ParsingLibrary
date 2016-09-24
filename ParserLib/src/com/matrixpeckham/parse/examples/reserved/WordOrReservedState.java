package com.matrixpeckham.parse.examples.reserved;

import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.TokenType;
import com.matrixpeckham.parse.parse.tokens.Tokenizer;
import com.matrixpeckham.parse.parse.tokens.WordState;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.logging.Logger;

public class WordOrReservedState extends WordState {

    ArrayList<String> reserved = new ArrayList<>();

    /**
     * A constant indicating that a token is a reserved word.
     */
    public static final TokenType TT_RESERVED = new TokenType("reserved");

    /**
     * Adds the specified string as a known reserved word.
     *
     * @param word the word to add
     */
    public void addReservedWord(String word) {
        reserved.add(word);
    }

    /**
     * Return all the known reserved words.
     *
     * @return ArrayList all the known reserved words
     */
    public ArrayList<String> getReservedWords() {
        return reserved;
    }

    /**
     * Return a reserved token or a word token from a reader.
     *
     * @return a reserved token or a word token from a reader
     * @throws java.io.IOException
     */
    @Override
    public Token nextToken(PushbackReader r, int c, Tokenizer t)
            throws IOException {

        Token tok = super.nextToken(r, c, t);
        if (reserved.contains(tok.sval())) {
            return new Token(TT_RESERVED, tok.sval(), 0);
        }
        return tok;
    }

    private static final Logger LOG
            = Logger.getLogger(WordOrReservedState.class.getName());

}
