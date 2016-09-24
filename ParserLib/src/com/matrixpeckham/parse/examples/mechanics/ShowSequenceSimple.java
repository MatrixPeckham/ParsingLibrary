package com.matrixpeckham.parse.examples.mechanics;

import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.tokens.Literal;
import com.matrixpeckham.parse.parse.tokens.Symbol;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.TokenAssembly;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.ArrayList;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * This class uses a <code>VerboseSequence</code> to show the progress a
 * sequence makes during matching.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowSequenceSimple {

    /**
     * Using a <code>VerboseSequence</code>, show the progress a sequence makes
     * during matching.
     *
     * @param args
     */
    public static void main(String[] args) {

        Parser<Token, NullCloneable, NullCloneable> hello = new Literal<>(
                "Hello");
        Parser<Token, NullCloneable, NullCloneable> world = new Literal<>(
                "world");
        Parser<Token, NullCloneable, NullCloneable> bang = new Symbol<>('!');

        Parser<Token, NullCloneable, NullCloneable> s
                = new VerboseSequence<Token, NullCloneable, NullCloneable>()
                .add(hello)
                .add(world)
                .add(bang);

        ArrayList<Assembly<Token, NullCloneable, NullCloneable>> v
                = new ArrayList<>();
        v.add(new TokenAssembly<>("Hello world!"));
        s.match(v);
    }

    private static final Logger LOG
            = Logger.getLogger(ShowSequenceSimple.class.getName());

}
