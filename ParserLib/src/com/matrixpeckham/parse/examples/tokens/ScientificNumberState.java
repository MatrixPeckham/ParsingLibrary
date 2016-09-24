package com.matrixpeckham.parse.examples.tokens;

import com.matrixpeckham.parse.parse.tokens.NumberState;
import com.matrixpeckham.parse.parse.tokens.Token;
import static com.matrixpeckham.parse.parse.tokens.Token.TT_WORD;
import com.matrixpeckham.parse.parse.tokens.Tokenizer;
import java.io.IOException;
import java.io.PushbackReader;
import static java.lang.Math.pow;
import java.util.logging.Logger;

public class ScientificNumberState extends NumberState {

    /**
     *
     */
    protected boolean absorbedE;

    /**
     * Just a demo.
     *
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        Tokenizer t = new Tokenizer();
        ScientificNumberState sns = new ScientificNumberState();
        t.setCharacterState('0', '9', sns);
        t.setCharacterState('.', '.', sns);
        t.setCharacterState('-', '-', sns);

        t.setString("1e2");
        System.out.println(t.nextToken());
    }
    /*
     * Parse from a decimal point to the end of a number,
     * including exponential or "scientific" notation.
     */

    /**
     *
     * @param r
     * @throws IOException
     */
    @Override
    protected void parseRight(PushbackReader r)
            throws IOException {

        super.parseRight(r);
        int sign = 1;
        if (c == 'e') {
            absorbedE = true;
            c = r.read();
            if (c == '-') {
                c = r.read();
                sign = -1;
            }
            value *= pow(10, sign * absorbDigits(r, false));
        }
    }
    /*
     * Prepare to assemble a new number.
     */

    /**
     *
     * @param cin
     */
    @Override
    protected void reset(int cin) {
        super.reset(cin);
        absorbedE = false;
    }
    /*
     * Put together the pieces of a number.
     */

    /**
     *
     * @param r
     * @param t
     * @return
     * @throws IOException
     */
    @Override
    protected Token value(PushbackReader r, Tokenizer t)
            throws IOException {

        if (!gotAdigit && absorbedE) {
            String s = "";
            if (absorbedLeadingMinus) {
                s += "-";
            }
            if (absorbedDot) {
                s += ".";
            }
            s += "e";
            return new Token(TT_WORD, s, 0);
        }
        return super.value(r, t);
    }

    private static final Logger LOG
            = Logger.getLogger(ScientificNumberState.class.getName());

}
