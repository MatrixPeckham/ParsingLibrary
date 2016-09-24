package com.matrixpeckham.parse.examples.mechanics;

import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Sequence;
import com.matrixpeckham.parse.parse.tokens.Num;
import com.matrixpeckham.parse.parse.tokens.Symbol;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.TokenAssembly;
import com.matrixpeckham.parse.utensil.NullCloneable;
import com.matrixpeckham.parse.utensil.TypeOrType;
import java.util.Stack;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Show the value of not pushing the element a terminal matches.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowPush {

    private static final Logger LOG = Logger.getLogger(ShowPush.class.getName());

    /**
     * Show the value of not pushing the element a terminal matches.
     *
     * @param args
     */
    public static void main(String[] args) {
        Parser<Token, NullCloneable, NullCloneable> open
                = new Symbol<NullCloneable, NullCloneable>('(').discard();
        Parser<Token, NullCloneable, NullCloneable> close
                = new Symbol<NullCloneable, NullCloneable>(')').discard();
        Parser<Token, NullCloneable, NullCloneable> comma
                = new Symbol<NullCloneable, NullCloneable>(',').discard();
        Num<NullCloneable, NullCloneable> num = new Num<>();
        Parser<Token, NullCloneable, NullCloneable> coord
                = new Sequence<Token, NullCloneable, NullCloneable>()
                .add(open)
                .add(num).add(comma).add(num).add(comma).add(num)
                .add(close);
        Assembly<Token, NullCloneable, NullCloneable> out = coord.bestMatch(
                new TokenAssembly<>("(23.4, 34.5, 45.6)"));
        Stack<TypeOrType<Token, NullCloneable>> s = out.getStack();
        while (!s.empty()) {
            System.out.println(s.pop());
        }
    }

}
