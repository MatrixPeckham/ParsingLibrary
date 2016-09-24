package com.matrixpeckham.parse.examples.mechanics;

import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Repetition;
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
 * This class shows that a repetition object creates more than one match from a
 * single assembly.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowRepetitionMatch {

    /**
     * This class shows that a repetition object creates more than one match
     * from a single assembly.
     *
     * @param args
     */
    public static void main(String[] args) {

        Parser<Token, NullCloneable, NullCloneable> p = new Repetition<>(
                new Word<NullCloneable, NullCloneable>());
        ArrayList<Assembly<Token, NullCloneable, NullCloneable>> v
                = new ArrayList<>();
        String s = "How many cups are in a gallon?";
        v.add(new TokenAssembly<>(s));
        System.out.println(p.match(v));
    }

    private static final Logger LOG
            = Logger.getLogger(ShowRepetitionMatch.class.getName());

}
