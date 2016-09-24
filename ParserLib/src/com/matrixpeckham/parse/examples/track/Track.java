package com.matrixpeckham.parse.examples.track;

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
 * A Track is a sequence that throws a <code>
 * TrackException</code> if the sequence begins but does not complete.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 * @param <Tok>
 * @param <Val>
 * @param <Tar>
 */
public class Track<Tok, Val, Tar extends PubliclyCloneable<Tar>>
        extends Sequence<Tok, Val, Tar> {

    /**
     * Constructs a nameless Track.
     */
    public Track() {
    }

    /**
     * Constructs a Track with the given name.
     *
     * @param name a name to be known by
     */
    public Track(String name) {
        super(name);
    }

    @Override
    public Parser<Tok, Val, Tar> copy() {
        Track<Tok, Val, Tar> s = new Track<>(name);
        s.subparsers = elementClone(subparsers);
        s.assembler = assembler;
        return s;
    }

    /**
     * Given a collection of assemblies, this method matches this track against
     * all of them, and returns a new collection of the assemblies that result
     * from the matches.
     *
     * If the match begins but does not complete, this method throws a
     * <code>TrackException</code>.
     *
     * @return a ArrayList of assemblies that result from matching against a
     * beginning set of assemblies
     *
     * @param in a vector of assemblies to match against
     *
     */
    @Override
    public ArrayList<Assembly<Tok, Val, Tar>> match(
            ArrayList<Assembly<Tok, Val, Tar>> in) {
        boolean inTrack = false;
        ArrayList<Assembly<Tok, Val, Tar>> last = in;
        ArrayList<Assembly<Tok, Val, Tar>> out = in;
        Iterator<Parser<Tok, Val, Tar>> e = subparsers.iterator();
        while (e.hasNext()) {
            Parser<Tok, Val, Tar> p = e.next();
            out = p.matchAndAssemble(last);
            if (out.isEmpty()) {
                if (inTrack) {
                    throwTrackException(last, p);
                }
                return out;
            }
            inTrack = true;
            last = out;
        }
        return out;
    }
    /*
     * Throw an exception showing how far the match had
     * progressed, what it found next, and what it was
     * expecting.
     */

    /**
     *
     * @param previousState
     * @param p
     */
    protected void throwTrackException(
            ArrayList<Assembly<Tok, Val, Tar>> previousState,
            Parser<Tok, Val, Tar> p) {

        Assembly<Tok, Val, Tar> best = best(previousState);
        String after = best.consumed(" ");
        if (after.isEmpty()) {
            after = "-nothing-";
        }

        String expected = p.toString();

        Object next = best.peek();
        String found = (next == null) ? "-nothing-" : next.toString();

        throw new TrackException(after, expected, found);
    }

    private static final Logger LOG = Logger.getLogger(Track.class.getName());

}
