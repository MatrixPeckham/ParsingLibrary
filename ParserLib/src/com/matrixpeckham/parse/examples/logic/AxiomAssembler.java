package com.matrixpeckham.parse.examples.logic;

import com.matrixpeckham.parse.engine.Axiom;
import com.matrixpeckham.parse.engine.Rule;
import com.matrixpeckham.parse.engine.Structure;
import com.matrixpeckham.parse.engine.Term;
import com.matrixpeckham.parse.examples.query.QueryBuilder;
import com.matrixpeckham.parse.parse.Assembler;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.utensil.TypeOrType;
import java.util.Stack;
import java.util.logging.Logger;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Pops the structures of a rule from an assembly's stack, and constructs and
 * pushes a rule.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class AxiomAssembler extends Assembler<Token, TypeOrType<Axiom, Term>, QueryBuilder> {

    /**
     * Pops all of the structures on the stack, builds a rule from them, and
     * pushes it.
     *
     * @param a the assembly to work on
     */
    @Override
    public void workOn(
            Assembly<Token, TypeOrType<Axiom, Term>, QueryBuilder> a) {
        Stack<TypeOrType<Token, TypeOrType<Axiom, Term>>> s = a.getStack();
        Structure[] sa = new Structure[s.size()];
        for (int i = 0; i < s.size(); i++) {
            Term t = s.get(i).asV().asV();
            if (t instanceof Structure) {
                sa[i] = (Structure) t;
            } else {
                //this should never happen because if we got here the structure parsers have succeeded
                throw new LogikusException(
                        "Axioms are composed of structures, "
                        + "found non-structure on stack");
            }
        }
        s.removeAllElements();
        a.pushVal(TypeOrType.fromT(new Rule(sa)));
    }

    private static final Logger LOG
            = Logger.getLogger(AxiomAssembler.class.getName());

}
