package com.matrixpeckham.parse.examples.tests;

import com.matrixpeckham.parse.parse.Alternation;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Repetition;
import com.matrixpeckham.parse.parse.Sequence;
import com.matrixpeckham.parse.parse.tokens.Literal;
import com.matrixpeckham.parse.parse.tokens.Symbol;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.Word;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.logging.Logger;

public class VolumeQuery {
    /*
     * Return a parser that recognizes the grammar:
     *
     *     query = (Word | volume)* '?';
     */

    /**
     *
     * @return
     */
    public static Parser<Token, String, NullCloneable> query() {
        Parser<Token, String, NullCloneable> a
                = new Alternation<Token, String, NullCloneable>()
                .add(new Word<>())
                .add(volume());
        Parser<Token, String, NullCloneable> s
                = new Sequence<Token, String, NullCloneable>()
                .add(new Repetition<>(a))
                .add(new Symbol<>('?'));
        return s;
    }
    /*
     * Return a parser that recognizes the grammar:
     *
     *     volume = "cups" | "gallon" | "liter";
     */

    /**
     *
     * @return
     */
    public static Parser<Token, String, NullCloneable> volume() {
        Parser<Token, String, NullCloneable> a
                = new Alternation<Token, String, NullCloneable>()
                .add(new Literal<>("cups"))
                .add(new Literal<>("gallon"))
                .add(new Literal<>("liter"));
        return a;
    }

    private static final Logger LOG
            = Logger.getLogger(VolumeQuery.class.getName());

}
