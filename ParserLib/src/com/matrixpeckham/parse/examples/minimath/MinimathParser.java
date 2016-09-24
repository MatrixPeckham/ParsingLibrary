package com.matrixpeckham.parse.examples.minimath;

import com.matrixpeckham.parse.examples.arithmetic.MinusAssembler;
import com.matrixpeckham.parse.examples.arithmetic.NumAssembler;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Repetition;
import com.matrixpeckham.parse.parse.Sequence;
import com.matrixpeckham.parse.parse.tokens.Num;
import com.matrixpeckham.parse.parse.tokens.Symbol;
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
 * Show a properly working utility class that provides an parser for "Minimath",
 * using the grammar:
 *
 * <blockquote><pre>
 *     e = Num m*;
 *     m = '-' Num;
 * </pre></blockquote>
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class MinimathParser {

    /**
     *
     */
    static Sequence<Token, Double, NullCloneable> e;

    /**
     * Return a parser that will recognize a Minimath expression.
     *
     * @return a parser that will recognize a Minimath expression
     */
    public synchronized static Parser<Token, Double, NullCloneable> e() {

// lazy-initialize e to prevent cycling
        if (e == null) {
            e = new Sequence<>();
            e.add(n());
            e.add(new Repetition<>(m()));
        }
        return e;
    }
    /*
     * a parser for the rule: m = '-' Num;
     */

    /**
     *
     * @return
     */
    protected static Parser<Token, Double, NullCloneable> m() {
        Sequence<Token, Double, NullCloneable> s = new Sequence<>();
        s.add(new Symbol<Double, NullCloneable>('-').discard());
        s.add(n());
        s.setAssembler(new MinusAssembler());
        return s;
    }

    /**
     * Just a little demo.
     *
     * @param args
     */
    public static void main(String[] args) {
        Assembly<Token, Double, NullCloneable> a = start()
                .completeMatch(
                        new TokenAssembly<>("25 - 16 - 9"));
        System.out.println(a.popVal());
    }
    /*
     * a parser to recognize a number. By default, Num
     * stacks a token. Here we use NumAssembler to replace
     * the token with its double value.
     */

    /**
     *
     * @return
     */
    protected static Parser<Token, Double, NullCloneable> n() {
        return new Num<Double, NullCloneable>().setAssembler(new NumAssembler());
    }

    /**
     * Returns a parser that will recognize a Minimath expression.
     *
     * @return a parser that will recognize a Minimath expression
     */
    public static Parser<Token, Double, NullCloneable> start() {
        return e();
    }

    private static final Logger LOG
            = Logger.getLogger(MinimathParser.class.getName());

}
