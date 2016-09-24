package com.matrixpeckham.parse.examples.tokens;

import com.matrixpeckham.parse.parse.Assembler;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Repetition;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.TokenAssembly;
import com.matrixpeckham.parse.parse.tokens.TokenString;
import com.matrixpeckham.parse.parse.tokens.TokenStringSource;
import com.matrixpeckham.parse.parse.tokens.Tokenizer;
import com.matrixpeckham.parse.parse.tokens.Word;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.logging.Logger;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * This class shows a collaboration of objects from classes
 * <code>Tokenizer</code>, <code>TokenStringSource</code>,
 * <code>TokenString</code>, <code>TokenAssembly</code>.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowTokenString {

    /**
     * Show a collaboration of token-related objects.
     *
     * @param args
     */
    public static void main(String args[]) {

        // a parser that counts words
        Parser<Token, Integer, NullCloneable> w
                = new Word<Integer, NullCloneable>().discard();
        w.setAssembler(new Assembler<Token, Integer, NullCloneable>() {
            @Override
            public void workOn(Assembly<Token, Integer, NullCloneable> a) {
                if (a.stackIsEmpty()) {
                    a.pushVal(1);
                } else {
                    Integer i = a.popVal();
                    a.pushVal(i + 1);
                }
            }
        });

        // a repetition of the word counter
        Parser<Token, Integer, NullCloneable> p = new Repetition<>(w);

        // consume token strings separated by semicolons
        String s = "I came; I saw; I left in peace;";
        Tokenizer t = new Tokenizer(s);
        TokenStringSource tss = new TokenStringSource(t, ";");

        // count the words in each token string
        while (tss.hasMoreTokenStrings()) {
            TokenString ts = tss.nextTokenString();
            TokenAssembly<Integer, NullCloneable> ta = new TokenAssembly<>(ts);
            Assembly<Token, Integer, NullCloneable> a = p.completeMatch(ta);
            System.out.println(
                    ts + " (" + a.pop() + " words)");
        }
    }

    private static final Logger LOG
            = Logger.getLogger(ShowTokenString.class.getName());

}
