package com.matrixpeckham.parse.parse.tokens;

import static com.matrixpeckham.parse.parse.tokens.Token.TT_EOF;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * A TokenStringSource enumerates over a specified reader,
 * returning TokenStrings delimited by a specified delimiter.
 * <p>
 * For example,
 * <blockquote><pre>
 *
 *    String s = "I came; I saw; I left in peace;";
 *
 *    TokenStringSource tss =
 *        new TokenStringSource(new Tokenizer(s), ";");
 *
 *    while (tss.hasMoreTokenStrings()) {
 *        System.out.println(tss.nextTokenString());
 *    }
 *
 * </pre></blockquote>
 * <p>
 * prints out:
 * <p>
 * <blockquote><pre>
 *     I came
 *     I saw
 *     I left in peace
 * </pre></blockquote>
 */
public class TokenStringSource {

    /**
     *
     */
    protected Tokenizer tokenizer;

    /**
     *
     */
    protected String delimiter;

    /**
     *
     */
    protected TokenString cachedTokenString = null;

    /**
     * Constructs a TokenStringSource that will read TokenStrings using the
     * specified tokenizer, delimited by the specified delimiter.
     *
     * @param tokenizer a tokenizer to read tokens from
     *
     * @param delimiter the character that fences off where one TokenString ends
     *                  and the next begins
     *
     */
    public TokenStringSource(
            Tokenizer tokenizer, String delimiter) {

        this.tokenizer = tokenizer;
        this.delimiter = delimiter;
    }

    /**
     * The design of <code>nextTokenString</code> is that is always returns a
     * cached value. This method will (at least attempt to) load the cache if
     * the cache is empty.
     */
    protected void ensureCacheIsLoaded() {
        if (cachedTokenString == null) {
            loadCache();
        }
    }

    /**
     * Returns true if the source has more TokenStrings.
     *
     * @return true, if the source has more TokenStrings that have not yet been
     *         popped with <code>
     *           nextTokenString</code>.
     */
    public boolean hasMoreTokenStrings() {
        ensureCacheIsLoaded();
        return cachedTokenString != null;
    }

    /**
     * Loads the next TokenString into the cache, or sets the cache to null if
     * the source is out of tokens.
     */
    protected void loadCache() {
        ArrayList<Token> tokenArrayList = nextArrayList();
        if (tokenArrayList.isEmpty()) {
            cachedTokenString = null;
        } else {
            Token tokens[] = new Token[tokenArrayList.size()];
            tokens = tokenArrayList.toArray(tokens);
            cachedTokenString = new TokenString(tokens);
        }
    }

    /**
     * Shows the example in the class comment.
     *
     * @param args ignored
     */
    public static void main(String args[]) {

        String s = "I came; I saw; I left in peace;";

        TokenStringSource tss = new TokenStringSource(new Tokenizer(s), ";");

        while (tss.hasMoreTokenStrings()) {
            System.out.println(tss.nextTokenString());
        }
    }

    /**
     * Returns the next TokenString from the source.
     *
     * @return the next TokenString from the source
     */
    public TokenString nextTokenString() {
        ensureCacheIsLoaded();
        TokenString returnTokenString = cachedTokenString;
        cachedTokenString = null;
        return returnTokenString;
    }

    /**
     * Returns a ArrayList of the tokens in the source up to either the
     * delimiter or the end of the source.
     *
     * @return a ArrayList of the tokens in the source up to either the
     *         delimiter or the end of the source.
     */
    protected ArrayList<Token> nextArrayList() {
        ArrayList<Token> v = new ArrayList<>();
        try {
            while (true) {
                Token tok = tokenizer.nextToken();
                if (tok.ttype() == TT_EOF || tok.sval().equals(delimiter)) {

                    break;
                }
                v.add(tok);
            }
        } catch (IOException e) {
            throw new InternalError(
                    "Problem tokenizing string: " + e);
        }
        return v;
    }

    private static final Logger LOG
            = Logger.getLogger(TokenStringSource.class.getName());

}
