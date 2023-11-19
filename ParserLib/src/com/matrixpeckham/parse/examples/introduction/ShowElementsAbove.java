package com.matrixpeckham.parse.examples.introduction;

import com.matrixpeckham.parse.parse.*;
import com.matrixpeckham.parse.parse.tokens.*;
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
 * Show how to use <code>Assembler.iteratorAbove()</code>.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowElementsAbove {

    /**
     * Show how to use <code>Assembler.iteratorAbove()</code>.
     *
     * @param args
     */
    public static void main(String args[]) {

        Parser<Token, NullCloneable, NullCloneable> list
                = new Sequence<Token, NullCloneable, NullCloneable>()
                        .add(new Symbol<>('{'))
                        .add(new Repetition<Token, NullCloneable, NullCloneable>(
                                new Word<>()))
                        .add(new Symbol<NullCloneable, NullCloneable>('}').
                                discard());

        list.setAssembler(new Assembler<Token, NullCloneable, NullCloneable>() {

            @Override
            public void workOn(Assembly<Token, NullCloneable, NullCloneable> a) {
                Token fence = new Token('{');
                System.out.println(elementsAbove(a, fence));
            }

        });

        list.bestMatch(
                new TokenAssembly<>("{ Washington Adams Jefferson }"));
    }

    private static final Logger LOG
            = Logger.getLogger(ShowElementsAbove.class.getName());

}
