package com.matrixpeckham.parse.examples.reserved;

import static com.matrixpeckham.parse.examples.reserved.WordOrReservedState.TT_RESERVED;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Terminal;
import com.matrixpeckham.parse.parse.tokens.Token;
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
 * A ReservedWord matches a word whose token type is
 * <code>WordOrReservedState.TT_RESERVED</code>.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 * @param <Val>
 * @param <Tar>
 */
public class ReservedWord<Val, Tar extends PubliclyCloneable<Tar>>
        extends Terminal<Token, Val, Tar> {

    /**
     * Returns true if an assembly's next element is a reserved word.
     *
     * @param o an element from an assembly
     *
     * @return true, if an assembly's next element is a reserved word
     */
    @Override
    protected boolean qualifies(Token o) {
        Token t = o;
        return t.ttype() == TT_RESERVED;
    }

    @Override
    public Parser<Token, Val, Tar> copy() {
        ReservedWord<Val, Tar> t = new ReservedWord<>();
        t.assembler = assembler;
        t.discard = discard;
        t.name = name;
        return t;
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
        return "ReservedWord";
    }

    private static final Logger LOG
            = Logger.getLogger(ReservedWord.class.getName());

}
