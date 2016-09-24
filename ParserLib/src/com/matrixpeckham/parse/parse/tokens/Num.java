package com.matrixpeckham.parse.parse.tokens;

import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Terminal;
import com.matrixpeckham.parse.utensil.PubliclyCloneable;
import static java.lang.Math.floor;
import static java.lang.Math.random;
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
 * A Num matches a number from a token assembly.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 * @param <Val>
 * @param <Tar>
 */
public class Num<Val, Tar extends PubliclyCloneable<Tar>>
        extends Terminal<Token, Val, Tar> {

    /**
     * Returns true if an assembly's next element is a number.
     *
     * @param o object an element from an assembly
     *
     * @return true, if an assembly's next element is a number as recognized the
     * tokenizer
     */
    @Override
    protected boolean qualifies(Token o) {
        Token t = o;
        return t.isNumber();
    }

    @Override
    public Parser<Token, Val, Tar> copy() {
        Num<Val, Tar> t = new Num<>();
        t.assembler = assembler;
        t.discard = discard;
        t.name = name;
        return t;
    }

    /**
     * Create a set with one random number (between 0 and 100).
     *
     * @return
     */
    @Override
    protected ArrayList<String> randomExpansion(int maxDepth, int depth) {
        double d = floor(1000.0 * random()) / 10;
        ArrayList<String> v = new ArrayList<>();
        v.add(Double.toString(d));
        return v;
    }

    /**
     * Returns a textual description of this parser.
     *
     * @param visited a list of parsers already printed in this description
     *
     * @return string a textual description of this parser
     *
     * @see Parser#toString()
     */
    @Override
    protected String unvisitedString(ArrayList<Parser<Token, Val, Tar>> visited,
            int level) {
        return "Num";
    }

    private static final Logger LOG = Logger.getLogger(Num.class.getName());

}
