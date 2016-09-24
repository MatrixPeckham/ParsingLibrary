package com.matrixpeckham.parse.examples.mechanics;

import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Repetition;
import com.matrixpeckham.parse.parse.Sequence;
import com.matrixpeckham.parse.parse.chars.CharacterAssembly;
import com.matrixpeckham.parse.parse.chars.SpecificChar;
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
 * This class shows that the "right" answer for a repetition object is not
 * always to match all that it can.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowAstarAB {

    /**
     * This class shows that the "right" answer for a repetition object is not
     * always to match all that it can.
     *
     * @param args
     */
    public static void main(String[] args) {

        Parser<Character, NullCloneable, NullCloneable> aStar
                = new Repetition<>(
                        new SpecificChar<NullCloneable, NullCloneable>('a'));

        Parser<Character, NullCloneable, NullCloneable> ab
                = new Sequence<Character, NullCloneable, NullCloneable>()
                .add(new SpecificChar<>('a'))
                .add(new SpecificChar<>('b')); // ab

        Parser<Character, NullCloneable, NullCloneable> aStarAB
                = new Sequence<Character, NullCloneable, NullCloneable>()
                .add(aStar)
                .add(ab); // a*ab

        ArrayList<Assembly<Character, NullCloneable, NullCloneable>> v
                = new ArrayList<>();
        String s = "aaaab";
        v.add(new CharacterAssembly<>(s));

        System.out.println(aStar.match(v));
        System.out.println(ab.match(aStar.match(v)));
        System.out.println(aStarAB.match(v));
    }

    private static final Logger LOG
            = Logger.getLogger(ShowAstarAB.class.getName());

}
