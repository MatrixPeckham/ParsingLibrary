package com.matrixpeckham.parse.examples.minimath;

import com.matrixpeckham.parse.parse.Alternation;
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
 *     e = Num | e '-' Num;
 * </pre></blockquote>
 *
 * Writing a parser directly from this grammar shows that left recusion will
 * hang a parser.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class MiniLeftRecursion {

    /**
     * Demonstrates an infinite loop.
     *
     * @param args
     */
    public static void main(String args[]) {
        Alternation<Token, NullCloneable, NullCloneable> e = new Alternation<>();
        Num<NullCloneable, NullCloneable> n = new Num<>();

        Sequence<Token, NullCloneable, NullCloneable> s = new Sequence<>();
        s.add(e);
        s.add(new Symbol<NullCloneable, NullCloneable>('-').discard());
        s.add(n);

        e.add(n);
        e.add(s);

        // now hang (or crash)
        e.completeMatch(new TokenAssembly<>("25 - 16 - 9"));
    }

    private static final Logger LOG
            = Logger.getLogger(MiniLeftRecursion.class.getName());

}
