package com.matrixpeckham.parse.examples.design;

import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Repetition;
import com.matrixpeckham.parse.parse.tokens.Num;
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
 * Show how to use an assembly's stack.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowStack {

    /**
     * Show how to use an assembly's stack.
     *
     * @param args
     */
    public static void main(String args[]) {

        Parser<Token, NullCloneable, NullCloneable> p = new Repetition<>(
                new Num<NullCloneable, NullCloneable>());
        Assembly<Token, NullCloneable, NullCloneable> a = p.completeMatch(
                new TokenAssembly<>("2 4 6 8"));
        System.out.println(a);
    }

    private static final Logger LOG
            = Logger.getLogger(ShowStack.class.getName());

}
