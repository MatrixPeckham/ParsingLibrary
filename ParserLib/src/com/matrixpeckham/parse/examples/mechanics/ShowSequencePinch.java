package com.matrixpeckham.parse.examples.mechanics;

import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Repetition;
import com.matrixpeckham.parse.parse.tokens.Symbol;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.TokenAssembly;
import com.matrixpeckham.parse.parse.tokens.Word;
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
 * This class shows that a <code>Sequence</code> match may widen and then narrow
 * the state of a match.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowSequencePinch {

    /**
     * Show that a <code>Sequence</code> match may widen and then narrow the
     * state of a match.
     *
     * @param args
     */
    public static void main(String[] args) {

        Parser<Token, NullCloneable, NullCloneable> s
                = new VerboseSequence<Token, NullCloneable, NullCloneable>()
                .add(new Repetition<Token, NullCloneable, NullCloneable>(
                                new Word<>()))
                .add(new Symbol<>('!'));

        ArrayList<Assembly<Token, NullCloneable, NullCloneable>> v
                = new ArrayList<>();
        v.add(new TokenAssembly<>("Hello world!"));
        s.match(v);
    }

    private static final Logger LOG
            = Logger.getLogger(ShowSequencePinch.class.getName());

}
