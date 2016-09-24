package com.matrixpeckham.parse.examples.introduction;

import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Sequence;
import com.matrixpeckham.parse.parse.tokens.Literal;
import com.matrixpeckham.parse.parse.tokens.Num;
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
 * Show a parser that recognizes an int declaration.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowLiteral {

    /**
     * Just a little demo.
     *
     * @param args
     */
    public static void main(String args[]) {
        Sequence<Token, NullCloneable, NullCloneable> s = new Sequence<>();
        s.add(new Literal<>("int"));
        s.add(new Word<>());
        s.add(new Symbol<>('='));
        s.add(new Num<>());
        s.add(new Symbol<>(';'));

        Assembly<Token, ?, ?> a = s.completeMatch(
                new TokenAssembly<>("int i = 3;"));

        System.out.println(a);
    }

    private static final Logger LOG
            = Logger.getLogger(ShowLiteral.class.getName());

}
