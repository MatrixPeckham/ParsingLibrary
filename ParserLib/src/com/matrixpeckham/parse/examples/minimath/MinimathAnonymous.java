package com.matrixpeckham.parse.examples.minimath;

import com.matrixpeckham.parse.parse.Assembler;
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
 *
 * This class just gives a little demo of how to create anonymous assemblers.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class MinimathAnonymous {

    /**
     * Just a little demo.
     *
     * @param args
     */
    public static void main(String args[]) {
        Sequence<Token, Double, NullCloneable> e = new Sequence<>();

        Num<Double, NullCloneable> n = new Num<>();
        n.setAssembler(new Assembler<Token, Double, NullCloneable>() {
            @Override
            public void workOn(Assembly<Token, Double, NullCloneable> a) {
                Token t = a.popTok();
                a.pushVal(t.nval());
            }
        });

        e.add(n);

        Sequence<Token, Double, NullCloneable> m = new Sequence<>();
        m.add(new Symbol<Double, NullCloneable>('-').discard());
        m.add(n);
        m.setAssembler(new Assembler<Token, Double, NullCloneable>() {
            @Override
            public void workOn(Assembly<Token, Double, NullCloneable> a) {
                Double d1 = a.popVal();
                Double d2 = a.popVal();
                Double d3 = d2 - d1;
                a.pushVal(d3);
            }
        });

        e.add(new Repetition<>(m));

        TokenAssembly<Double, NullCloneable> t = new TokenAssembly<>(
                "25 - 16 - 9");
        Assembly<Token, Double, NullCloneable> out = e.completeMatch(t);
        System.out.println(out.pop());

    }

    private static final Logger LOG
            = Logger.getLogger(MinimathAnonymous.class.getName());

}
