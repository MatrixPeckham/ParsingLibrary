package com.matrixpeckham.parse.examples.tests;

import com.matrixpeckham.parse.parse.*;
import com.matrixpeckham.parse.parse.tokens.*;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.logging.Logger;

/**
 * This class provides an ambiguous parser in its <code>
 * query</code> method, which serves to show that
 * the test classes can find ambiguity.
 * <p>
 * The grammar this class supports is:
 * <blockquote><pre>
 *
 *     query  = (Word | volume)* '?';
 *     volume = "cups" | "gallon" | "liter";
 * </pre></blockquote>
 */
public class VolumeQuery {

    /**
     * Return a parser that recognizes the grammar:
     * <p>
     * query = (Word | volume)* '?';
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

    /**
     * Return a parser that recognizes the grammar:
     * <p>
     * volume = "cups" | "gallon" | "liter";
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
