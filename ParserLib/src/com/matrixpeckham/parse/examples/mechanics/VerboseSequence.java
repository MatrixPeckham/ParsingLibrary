package com.matrixpeckham.parse.examples.mechanics;

import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Sequence;
import com.matrixpeckham.parse.utensil.PubliclyCloneable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * The <code>match()</code> method of this class prints the collection of
 * assemblies it receives, and each new collection it creates.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 * @param <Tok>
 * @param <Val>
 * @param <Tar>
 */
public class VerboseSequence<Tok, Val, Tar extends PubliclyCloneable<Tar>>
        extends Sequence<Tok, Val, Tar> {

    /**
     * Constructs a nameless VerboseSequence.
     */
    public VerboseSequence() {
        super();
    }

    /**
     * Constructs a Sequence with the given name.
     *
     * @param name a name to be known by
     */
    public VerboseSequence(String name) {
        super(name);
    }

    @Override
    public Parser<Tok, Val, Tar> copy() {
        VerboseSequence<Tok, Val, Tar> s = new VerboseSequence<>(name);
        s.subparsers = elementClone(subparsers);
        s.assembler = assembler;
        return s;
    }

    /**
     * Just a verbose version of <code>Sequence.match()</code>.
     *
     * @param inputState
     * @return
     */
    @Override
    public ArrayList<Assembly<Tok, Val, Tar>> match(
            ArrayList<Assembly<Tok, Val, Tar>> inputState) {
        ArrayList<Assembly<Tok, Val, Tar>> finalState = inputState;
        System.out.println(finalState); // be verbose
        Iterator<Parser<Tok, Val, Tar>> e = subparsers.iterator();
        while (e.hasNext()) {
            Parser<Tok, Val, Tar> p = e.next();
            finalState = p.matchAndAssemble(finalState);
            if (finalState.isEmpty()) {
                return finalState;
            }
            System.out.println(finalState); // be verbose
        }
        return finalState;
    }

    private static final Logger LOG
            = Logger.getLogger(VerboseSequence.class.getName());

}
