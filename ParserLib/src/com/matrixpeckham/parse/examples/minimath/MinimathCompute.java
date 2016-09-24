package com.matrixpeckham.parse.examples.minimath;

import com.matrixpeckham.parse.examples.arithmetic.MinusAssembler;
import com.matrixpeckham.parse.examples.arithmetic.NumAssembler;
import com.matrixpeckham.parse.parse.Assembly;
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
 * This class provides a parser that recognizes minimal arithmetic expressions,
 * specifically allowing only the '-' operator. The rules of the Minimath
 * language are:
 *
 * <blockquote><pre>
 *     e = Num m*;
 *     m = '-' Num;
 * </pre></blockquote>
 *
 * This class shows, in a minimal example, where assemblers plug into a parser
 * composite.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class MinimathCompute {

    /**
     * Just a little demo.
     *
     * @param args
     */
    public static void main(String args[]) {
        Sequence<Token, Double, NullCloneable> e = new Sequence<>();

        Num<Double, NullCloneable> n = new Num<>();
        n.setAssembler(new NumAssembler());

        e.add(n);

        Sequence<Token, Double, NullCloneable> m = new Sequence<>();
        m.add(new Symbol<Double, NullCloneable>('-').discard());
        m.add(n);
        m.setAssembler(new MinusAssembler());

        e.add(new Repetition<>(m));

        TokenAssembly<Double, NullCloneable> t = new TokenAssembly<>(
                "25 - 16 - 9");
        Assembly<Token, Double, NullCloneable> out = e.completeMatch(t);
        System.out.println(out.popVal());
    }

    private static final Logger LOG
            = Logger.getLogger(MinimathCompute.class.getName());

}
