package com.matrixpeckham.parse.examples.logic;

import com.matrixpeckham.parse.engine.Atom;
import com.matrixpeckham.parse.engine.Axiom;
import com.matrixpeckham.parse.engine.NumberFact;
import com.matrixpeckham.parse.engine.Term;
import com.matrixpeckham.parse.examples.query.QueryBuilder;
import com.matrixpeckham.parse.parse.Assembler;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.utensil.TypeOrType;
import java.util.logging.Logger;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Exchanges a token on an assembly's stack with an atom that has the token's
 * value as its functor.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class AtomAssembler extends Assembler<Token, TypeOrType<Axiom, Term>, QueryBuilder> {

    /**
     * Exchanges a token on an assembly's stack with an atom that has the
     * token's value as its functor. In the case of a quoted string, this
     * assembler removes the quotes, so that a string such as "Smith" becomes
     * just Smith. In the case of a number, this assembler pushes a NumberFact.
     *
     * @param a the assembly to work on
     */
    @Override
    public void workOn(Assembly<Token, TypeOrType<Axiom, Term>, QueryBuilder> a) {
        Token t = a.popTok();
        // remove quotes from quoted string
        if (t.isQuotedString()) {
            String s = t.sval();
            String plain = s.substring(1, s.length() - 1);
            Atom at = new Atom(plain);
            a.pushVal(TypeOrType.fromBoth(at, at));
        } else if (t.isNumber()) {
            NumberFact nf = new NumberFact(t.nval());
            a.pushVal(TypeOrType.fromBoth(nf, nf));
        } else {
            Atom at = new Atom(t.value());
            a.pushVal(TypeOrType.fromBoth(at, at));
        }
    }

    private static final Logger LOG
            = Logger.getLogger(AtomAssembler.class.getName());

}
