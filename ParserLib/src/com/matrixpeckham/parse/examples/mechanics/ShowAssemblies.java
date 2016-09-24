package com.matrixpeckham.parse.examples.mechanics;

import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Repetition;
import com.matrixpeckham.parse.parse.Terminal;
import com.matrixpeckham.parse.parse.chars.CharacterAssembly;
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
 * This class shows that a repetition of an object of the <code>Terminal</code>
 * base class will match an entire assembly. This example brings out the fact
 * that a <code>TokenAssembly</code> returns tokens as elements, and
 * <code>CharacterAssembly</code> returns individual characters as elements.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class ShowAssemblies {

    /**
     * This class shows that a repetition of an object of the
     * <code>Terminal</code> base class will match an entire assembly.
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        //we need to use raw types because we can't get one parser to work with
        //different tyeps of assemblies otherwise, usually we'd know
        //which one of the two we were using and would not use raw types
        Parser<Token, NullCloneable, NullCloneable> pt
                = new Repetition<Token, NullCloneable, NullCloneable>(
                        new Terminal<>());
        Parser<Character, NullCloneable, NullCloneable> pc
                = new Repetition<Character, NullCloneable, NullCloneable>(
                        new Terminal<>());

        String s = "She's a 'smart cookie'!";
        System.out.println(pt.bestMatch(
                new TokenAssembly<>(s)));
        System.out.println(pc.bestMatch(
                new CharacterAssembly<>(s)));
    }

    private static final Logger LOG
            = Logger.getLogger(ShowAssemblies.class.getName());

}
