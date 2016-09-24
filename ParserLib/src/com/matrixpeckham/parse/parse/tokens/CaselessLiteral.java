package com.matrixpeckham.parse.parse.tokens;

import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.utensil.PubliclyCloneable;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * A CaselessLiteral matches a specified String from an assembly, disregarding
 * case.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 * @param <Val>
 * @param <Tar>
 */
public class CaselessLiteral<Val, Tar extends PubliclyCloneable<Tar>>
        extends Literal<Val, Tar> {

    /**
     * Constructs a literal that will match the specified string, given
     * mellowness about case.
     *
     * @param literal the string to match as a token
     *
     */
    public CaselessLiteral(String literal) {
        super(literal);
    }

    @Override
    public Parser<Token, Val, Tar> copy() {
        CaselessLiteral<Val, Tar> t = new CaselessLiteral<>(literal.sval());
        t.assembler = assembler;
        t.discard = discard;
        t.name = name;
        return t;
    }

    /**
     * Returns true if the literal this object equals an assembly's next
     * element, disregarding case.
     *
     * @param o an element from an assembly
     *
     * @return true, if the specified literal equals the next token from an
     * assembly, disregarding case
     */
    @Override
    protected boolean qualifies(Token o) {
        return literal.equalsIgnoreCase(o);
    }

    private static final Logger LOG
            = Logger.getLogger(CaselessLiteral.class.getName());

}
