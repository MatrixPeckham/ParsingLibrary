package com.matrixpeckham.parse.examples.introduction;

import com.matrixpeckham.parse.parse.Alternation;
import com.matrixpeckham.parse.parse.Empty;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Repetition;
import com.matrixpeckham.parse.parse.Sequence;
import com.matrixpeckham.parse.parse.tokens.Symbol;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.TokenAssembly;
import com.matrixpeckham.parse.parse.tokens.Word;
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
 * Show how to put the <code>Empty</code> class to good use.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowEmpty {

    /**
     * Show the value of the <code>Empty</code> parser, using a list parser.
     *
     * A list, in this example, is a pair of brackets around some contents. The
     * contents may be empty, or may be an actual list. An actual list is one or
     * more words, separated by commas. That is, an actual list is a word
     * followed by zero or more sequences of (comma, word).
     *
     * @param args
     */
    public static void main(String args[]) {

        Parser<Token, NullCloneable, NullCloneable> empty, commaTerm, actualList, contents, list;

        empty = new Empty<>();

        commaTerm = new Sequence<Token, NullCloneable, NullCloneable>()
                .add(new Symbol<NullCloneable, NullCloneable>(',').discard())
                .add(new Word<>());

        actualList = new Sequence<Token, NullCloneable, NullCloneable>()
                .add(new Word<>())
                .add(new Repetition<>(commaTerm));

        contents = new Alternation<Token, NullCloneable, NullCloneable>()
                .add(empty)
                .add(actualList);

        list = new Sequence<Token, NullCloneable, NullCloneable>()
                .add(new Symbol<NullCloneable, NullCloneable>('[').discard())
                .add(contents)
                .add(new Symbol<NullCloneable, NullCloneable>(']').discard());

        String test[] = new String[]{
            "[die_bonder_2, oven_7, wire_bonder_3, mold_1]",
            "[]",
            "[mold_1]"};
        for (String test1 : test) {
            TokenAssembly<NullCloneable, NullCloneable> a = new TokenAssembly<>(
                    test1);
            System.out.println(list.completeMatch(a).getStack());
        }
    }

    private static final Logger LOG
            = Logger.getLogger(ShowEmpty.class.getName());

}
