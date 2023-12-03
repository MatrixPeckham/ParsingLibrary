package com.matrixpeckham.parse.parse.tokens;

import static com.matrixpeckham.parse.parse.tokens.Token.EOF;

import java.io.*;
import java.util.logging.Logger;

/**
 * A tokenizer divides a string into tokens. This class is
 * highly customizable with regard to exactly how this division
 * occurs, but it also has defaults that are suitable for many
 * languages. This class assumes that the character values read
 * from the string lie in the range 0-255. For example, the
 * Unicode value of a capital A is 65, so
 * <code> System.out.println((char)65); </code> prints out a
 * capital A.
 * <p>
 * <p>
 * The behavior of a tokenizer depends on its character state
 * table. This table is an array of 256 <code>TokenizerState
 * </code> states. The state table decides which state to
 * enter upon reading a character from the input
 * string.
 * <p>
 * <p>
 * For example, by default, upon reading an 'A', a tokenizer
 * will enter a "word" state. This means the tokenizer will
 * ask a <code>WordState</code> object to consume the 'A',
 * along with the characters after the 'A' that form a word.
 * The state's responsibility is to consume characters and
 * return a complete token.
 * <p>
 * <p>
 * The default table sets a SymbolState for every character
 * from 0 to 255, and then overrides this with:
 * <p>
 * <blockquote><pre>
 *     From    To     State
 *       0     ' '    whitespaceState
 *      'a'    'z'    wordState
 *      'A'    'Z'    wordState
 *     160     255    wordState
 *      '0'    '9'    numberState
 *      '-'    '-'    numberState
 *      '.'    '.'    numberState
 *      '"'    '"'    quoteState
 *     '\''   '\''    quoteState
 *      '/'    '/'    slashState
 * </pre></blockquote>
 * <p>
 * In addition to allowing modification of the state table,
 * this class makes each of the states above available. Some
 * of these states are customizable. For example, wordState
 * allows customization of what characters can be part of a
 * word, after the first character.
 */
public class Tokenizer {

    /**
     * The reader to read characters from
     * <p>
     */
    protected PushbackReader reader;

    /**
     * The number of characters that might be in a symbol;
     * <p>
     */
    protected static final int DEFAULT_SYMBOL_MAX = 4;

    /**
     * The state lookup table
     * <p>
     */
    protected TokenizerState[] characterState = new TokenizerState[256];

    /**
     * The default states that actually consume text and
     * produce a token
     * <p>
     */
    protected NumberState numberState = new NumberState();

    /**
     *
     */
    protected QuoteState quoteState = new QuoteState();

    /**
     *
     */
    protected SlashState slashState = new SlashState();

    /**
     *
     */
    protected SymbolState symbolState = new SymbolState();

    /**
     *
     */
    protected WhitespaceState whitespaceState = new WhitespaceState();

    /**
     *
     */
    protected WordState wordState = new WordState();

    /**
     * Constructs a tokenizer with a default state table (as described in the
     * class comment).
     * <p>
     */
    public Tokenizer() {

        setCharacterState(0, 255, symbolState()); // the default

        setCharacterState(0, ' ', whitespaceState());
        setCharacterState('a', 'z', wordState());
        setCharacterState('A', 'Z', wordState());
        setCharacterState(0xc0, 0xff, wordState());
        setCharacterState('0', '9', numberState());
        setCharacterState('-', '-', numberState());
        setCharacterState('.', '.', numberState());
        setCharacterState('"', '"', quoteState());
        setCharacterState('\'', '\'', quoteState());
        setCharacterState('/', '/', slashState());
    }

    /**
     * Constructs a tokenizer to read from the supplied string.
     *
     * @param s the string to read from
     */
    public Tokenizer(String s) {
        this();
        setString(s);
    }

    /**
     * Return the reader this tokenizer will read from.
     *
     * @return the reader this tokenizer will read from
     */
    public PushbackReader getReader() {
        return reader;
    }

    /**
     * Return the next token.
     *
     * @return the next token.
     *
     * @exception IOException if there is any problem reading
     */
    public Token nextToken() throws IOException {
        int c = reader.read();

        /* There was a defect here, that resulted from the fact
         * that unreading a -1 results in the next read having a
         * value of (int)(char)-1, which is 65535. This may be
         * a defect in PushbackReader. */
        if (c >= 0 && c < characterState.length) {
            return characterState[c].nextToken(reader, c, this);
        }
        return EOF;
    }

    /**
     * Return the state this tokenizer uses to build numbers.
     *
     * @return the state this tokenizer uses to build numbers
     */
    public final NumberState numberState() {
        return numberState;
    }

    /**
     * Return the state this tokenizer uses to build quoted strings.
     *
     * @return the state this tokenizer uses to build quoted strings
     */
    public final QuoteState quoteState() {
        return quoteState;
    }

    /**
     * Change the state the tokenizer will enter upon reading any character
     * between "from" and "to".
     *
     * @param from  the "from" character
     *
     * @param to    the "to" character
     *
     *
     * @param state the state to enter upon reading a character between "from"
     *              and "to"
     */
    public final void setCharacterState(
            int from, int to, TokenizerState state) {

        for (int i = from; i <= to; i++) {
            if (i >= 0 && i < characterState.length) {
                characterState[i] = state;
            }
        }
    }

    /**
     * Set the reader to read from.
     *
     * @param r the reader to read from
     */
    public void setReader(PushbackReader r) {
        this.reader = r;
    }

    /**
     * Set the string to read from.
     *
     * @param s the string to read from
     */
    public final void setString(String s) {
        setString(s, DEFAULT_SYMBOL_MAX);
    }

    /**
     * Set the string to read from.
     *
     * @param s         the string to read from
     * @param symbolMax the maximum length of a symbol, which establishes the
     *                  size of pushback buffer we need
     */
    public void setString(String s, int symbolMax) {
        setReader(
                new PushbackReader(new StringReader(s), symbolMax));
    }

    /**
     * Return the state this tokenizer uses to recognize (and ignore) comments.
     *
     * @return the state this tokenizer uses to recognize (and ignore) comments
     *
     */
    public final SlashState slashState() {
        return slashState;
    }

    /**
     * Return the state this tokenizer uses to recognize symbols.
     *
     * @return the state this tokenizer uses to recognize symbols
     */
    public final SymbolState symbolState() {
        return symbolState;
    }

    /**
     * Return the state this tokenizer uses to recognize (and ignore)
     * whitespace.
     *
     * @return the state this tokenizer uses to recognize whitespace
     */
    public final WhitespaceState whitespaceState() {
        return whitespaceState;
    }

    /**
     * Return the state this tokenizer uses to build words.
     *
     * @return the state this tokenizer uses to build words
     */
    public final WordState wordState() {
        return wordState;
    }

    private static final Logger LOG
            = Logger.getLogger(Tokenizer.class.getName());

}
