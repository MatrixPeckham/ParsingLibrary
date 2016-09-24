package com.matrixpeckham.parse.examples.mechanics;

import com.matrixpeckham.parse.parse.Alternation;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Sequence;
import com.matrixpeckham.parse.parse.tokens.Literal;
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
 * This class uses a <code>VerboseRepetition</code> to show the progress a
 * repetition makes during matching.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowVacation {

    /**
     * Using a <code>VerboseRepetition</code>, show the progress a repetition
     * makes during matching.
     *
     * @param args
     */
    public static void main(String args[]) {

        Parser<Token, NullCloneable, NullCloneable> prepare
                = new Alternation<Token, NullCloneable, NullCloneable>()
                .add(new Literal<NullCloneable, NullCloneable>("plan").discard()).
                add(new Literal<NullCloneable, NullCloneable>("shop").discard())
                .add(new Literal<NullCloneable, NullCloneable>("pack").discard());

        Parser<Token, NullCloneable, NullCloneable> enjoy
                = new Alternation<Token, NullCloneable, NullCloneable>()
                .add(new Literal<NullCloneable, NullCloneable>("swim").discard()).
                add(new Literal<NullCloneable, NullCloneable>("hike").discard())
                .add(new Literal<NullCloneable, NullCloneable>("relax").
                        discard());

        Parser<Token, NullCloneable, NullCloneable> vacation
                = new Sequence<Token, NullCloneable, NullCloneable>()
                .add(new VerboseRepetition<>(
                                prepare))
                .add(new VerboseRepetition<>(enjoy));

        ArrayList<Assembly<Token, NullCloneable, NullCloneable>> v
                = new ArrayList<>();
        v.add(new TokenAssembly<>("plan pack hike relax"));

        vacation.match(v);
    }

    private static final Logger LOG
            = Logger.getLogger(ShowVacation.class.getName());

}
