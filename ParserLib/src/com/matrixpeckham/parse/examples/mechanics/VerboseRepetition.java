package com.matrixpeckham.parse.examples.mechanics;

import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Repetition;
import com.matrixpeckham.parse.utensil.PubliclyCloneable;
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
 * The <code>match()</code> method of this class prints the collection of
 * assemblies it receives, and the new collection it creates.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 * @param <Tok>
 * @param <Val>
 * @param <Tar>
 */
public class VerboseRepetition<Tok, Val, Tar extends PubliclyCloneable<Tar>>
        extends Repetition<Tok, Val, Tar> {

    /**
     * Constructs a VerboseRepetition of the given parser.
     *
     * @param p the parser to repeat
     */
    public VerboseRepetition(Parser<Tok, Val, Tar> p) {
        super(p);
    }

    /**
     * Constructs a VerboseRepetition of the given parser with the given name.
     *
     * @param p the parser to repeat
     *
     * @param name a name to be known by
     */
    public VerboseRepetition(Parser<Tok, Val, Tar> p, String name) {
        super(p, name);
    }

    /**
     * Just a verbose version of <code>Repetition.match()
     * </code>.
     *
     * @return
     */
    @Override
    public ArrayList<Assembly<Tok, Val, Tar>> match(
            ArrayList<Assembly<Tok, Val, Tar>> in) {
        System.out.println(" in: " + in);
        ArrayList<Assembly<Tok, Val, Tar>> out = super.match(in);
        System.out.println("out: " + out);
        return out;
    }

    private static final Logger LOG
            = Logger.getLogger(VerboseRepetition.class.getName());

}
