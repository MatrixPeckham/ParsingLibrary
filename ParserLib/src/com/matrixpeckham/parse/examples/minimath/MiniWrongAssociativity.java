package com.matrixpeckham.parse.examples.minimath;

import com.matrixpeckham.parse.examples.arithmetic.MinusAssembler;
import com.matrixpeckham.parse.examples.arithmetic.NumAssembler;
import com.matrixpeckham.parse.parse.Alternation;
import com.matrixpeckham.parse.parse.Assembly;
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
 * This class uses a problematic grammar for Minimath. For a better grammar, see
 * class <code>MinimathCompute</code>. Here, the grammar is:
 *
 * <blockquote><pre>
 *     e = Num '-' e | Num;
 * </pre></blockquote>
 *
 * Writing a parser directly from this grammar will show that the associativity
 * is wrong. For example, this grammar will lead to a parser that calculates the
 * value of 25 - 16 - 9 as 18.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class MiniWrongAssociativity {

    /**
     * Demonstrates incorrect associativity.
     *
     * @param args
     */
    public static void main(String args[]) {
        Alternation<Token, Double, NullCloneable> e = new Alternation<>();
        Num<Double, NullCloneable> n = new Num<>();
        n.setAssembler(new NumAssembler());

        Sequence<Token, Double, NullCloneable> s = new Sequence<>();
        s.add(n);
        s.add(new Symbol<Double, NullCloneable>('-').discard());
        s.add(e);
        s.setAssembler(new MinusAssembler());

        e.add(s);
        e.add(n);

        Assembly<Token, Double, NullCloneable> out = e.completeMatch(
                new TokenAssembly<>("25 - 16 - 9"));

        System.out.println(out.popVal() + " // arguably wrong!");
    }

    private static final Logger LOG
            = Logger.getLogger(MiniWrongAssociativity.class.getName());

}
