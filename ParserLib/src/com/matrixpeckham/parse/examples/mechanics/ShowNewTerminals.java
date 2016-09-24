package com.matrixpeckham.parse.examples.mechanics;

import com.matrixpeckham.parse.parse.Alternation;
import com.matrixpeckham.parse.parse.Assembler;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Repetition;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.TokenAssembly;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Show the use of new subclasses of <code>Terminal</code>.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowNewTerminals {

    /**
     * Show the use of new subclasses of <code>Terminal</code>.
     *
     * @param args
     */
    public static void main(String[] args) {

        /*  term     = variable | known;
         *  variable = UppercaseWord;
         *  known    = LowercaseWord;
         */
        Parser<Token, String, NullCloneable> variable = new UppercaseWord<>();
        Parser<Token, String, NullCloneable> known = new LowercaseWord<>();

        Parser<Token, String, NullCloneable> term
                = new Alternation<Token, String, NullCloneable>()
                .add(variable)
                .add(known);

        // anonymous Assembler subclasses note element type
        variable.setAssembler(new Assembler<Token, String, NullCloneable>() {
            @Override
            public void workOn(Assembly<Token, String, NullCloneable> a) {
                Token o = a.popTok();
                a.pushVal("VAR(" + o + ")");
            }
        });

        known.setAssembler(new Assembler<Token, String, NullCloneable>() {
            @Override
            public void workOn(Assembly<Token, String, NullCloneable> a) {
                Token o = a.popTok();
                a.pushVal("KNOWN(" + o + ")");
            }
        });

        // term* matching against knowns and variables:
        System.out.println(
                new Repetition<>(term).bestMatch(
                        new TokenAssembly<>(
                                "member X republican democrat")));
    }

    private static final Logger LOG
            = Logger.getLogger(ShowNewTerminals.class.getName());

}
