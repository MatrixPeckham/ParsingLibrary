package com.matrixpeckham.parse.examples.track;

import com.matrixpeckham.parse.parse.Alternation;
import com.matrixpeckham.parse.parse.Assembly;
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
 * Show some examples of using a <code>Track</code>.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowTrack {
    /*
     * Return a parser that will recognize a list, for the
     * grammar:
     *
     *     list       = '(' contents ')';
     *     contents   = empty | actualList;
     *     actualList = Word (',' Word)*;
     */

    /**
     *
     * @return
     */
    public static Parser<Token, NullCloneable, NullCloneable> list() {

        Parser<Token, NullCloneable, NullCloneable> empty, commaWord, actualList, contents, list;

        empty = new Empty<>();

        commaWord = new Track<Token, NullCloneable, NullCloneable>()
                .add(new Symbol<NullCloneable, NullCloneable>(',').discard())
                .add(new Word<>());

        actualList = new Sequence<Token, NullCloneable, NullCloneable>()
                .add(new Word<>())
                .add(new Repetition<>(commaWord));

        contents = new Alternation<Token, NullCloneable, NullCloneable>()
                .add(empty)
                .add(actualList);

        list = new Track<Token, NullCloneable, NullCloneable>()
                .add(new Symbol<NullCloneable, NullCloneable>('(').discard())
                .add(contents)
                .add(new Symbol<NullCloneable, NullCloneable>(')').discard());

        return list;
    }

    /**
     * Show some examples of using a <code>Track</code>.
     *
     * @param args
     */
    public static void main(String args[]) {

        Parser<Token, NullCloneable, NullCloneable> list = list();

        String test[] = new String[]{
            "()",
            "(pilfer)",
            "(pilfer, pinch)",
            "(pilfer, pinch, purloin)",
            "(pilfer, pinch,, purloin)",
            "(",
            "(pilfer",
            "(pilfer, ",
            "(, pinch, purloin)",
            "pilfer, pinch"};

        System.out.println("Using parser: " + list);
        for (String test1 : test) {
            System.out.println("---\ntesting: " + test1);
            TokenAssembly<NullCloneable, NullCloneable> a = new TokenAssembly<>(
                    test1);
            try {
                Assembly<Token, NullCloneable, NullCloneable> out = list.
                        completeMatch(a);
                if (out == null) {
                    System.out.println("list.completeMatch() returns null");
                } else {
                    Object s = list.completeMatch(a).getStack();
                    System.out.println("Ok, stack is: " + s);
                }
            } catch (TrackException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static final Logger LOG
            = Logger.getLogger(ShowTrack.class.getName());

}
