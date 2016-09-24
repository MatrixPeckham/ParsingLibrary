package com.matrixpeckham.parse.parse.tokens;

import static com.matrixpeckham.parse.parse.tokens.Token.TT_SYMBOL;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.logging.Logger;

public class SlashState extends TokenizerState {

    /**
     *
     */
    protected SlashStarState slashStarState = new SlashStarState();

    /**
     *
     */
    protected SlashSlashState slashSlashState = new SlashSlashState();

    /**
     * Either delegate to a comment-handling state, or return a token with just
     * a slash in it.
     *
     * @param r
     * @param theSlash
     * @return either just a slash token, or the results of delegating to a
     * comment-handling state
     * @throws java.io.IOException
     */
    @Override
    public Token nextToken(
            PushbackReader r, int theSlash, Tokenizer t)
            throws IOException {

        int c = r.read();
        if (c == '*') {
            return slashStarState.nextToken(r, '*', t);
        }
        if (c == '/') {
            return slashSlashState.nextToken(r, '/', t);
        }
        if (c >= 0) {
            r.unread(c);
        }
        return new Token(TT_SYMBOL, "/", 0);
    }

    private static final Logger LOG
            = Logger.getLogger(SlashState.class.getName());

}
