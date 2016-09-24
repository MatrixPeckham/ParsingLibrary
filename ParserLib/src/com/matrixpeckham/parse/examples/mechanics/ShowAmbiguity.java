package com.matrixpeckham.parse.examples.mechanics;

import com.matrixpeckham.parse.parse.Alternation;
import com.matrixpeckham.parse.parse.Assembler;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Repetition;
import com.matrixpeckham.parse.parse.Sequence;
import com.matrixpeckham.parse.parse.tokens.Literal;
import com.matrixpeckham.parse.parse.tokens.Symbol;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.TokenAssembly;
import com.matrixpeckham.parse.parse.tokens.Word;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.ArrayList;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * This class shows than a parser can find more than one way to completely
 * consume an assembly.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowAmbiguity {

    /**
     * Show than a parser can find more than one way to completely consume an
     * assembly.
     *
     * @param args
     */
    public static void main(String[] args) {

        // volume = "cups" | "gallon" | "liter";
        Parser<Token, String, NullCloneable> volume
                = new Alternation<Token, String, NullCloneable>()
                .add(new Literal<>("cups"))
                .add(new Literal<>("gallon"))
                .add(new Literal<>("liter"));

        // an anonymous Assembler subclass notes volume matches
        volume.setAssembler(new Assembler<Token, String, NullCloneable>() {
            @Override
            public void workOn(Assembly<Token, String, NullCloneable> a) {
                Token o = a.popTok();
                a.pushVal("VOL(" + o + ")");
            }
        });

        // query = (Word | volume)* '?';
        Parser<Token, String, NullCloneable> wordOrVolume
                = new Alternation<Token, String, NullCloneable>()
                .add(new Word<>())
                .add(volume);

        Parser<Token, String, NullCloneable> query
                = new Sequence<Token, String, NullCloneable>()
                .add(new Repetition<>(wordOrVolume))
                .add(new Symbol<>('?'));

        ArrayList<Assembly<Token, String, NullCloneable>> v = new ArrayList<>();
        v.add(
                new TokenAssembly<>("How many cups are in a gallon?"));

        System.out.println(query.match(v));
    }

    private static final Logger LOG
            = Logger.getLogger(ShowAmbiguity.class.getName());

}
