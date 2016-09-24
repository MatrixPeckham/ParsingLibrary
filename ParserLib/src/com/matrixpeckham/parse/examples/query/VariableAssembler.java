package com.matrixpeckham.parse.examples.query;

import com.matrixpeckham.parse.engine.Axiom;
import com.matrixpeckham.parse.engine.Term;
import com.matrixpeckham.parse.engine.Variable;
import com.matrixpeckham.parse.parse.Assembler;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.utensil.TypeOrType;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * This assembler pops a token from the stack, extracts its string, and pushes a
 * <code>Variable</code> of that name. This assembler also looks up the name in
 * a <code>ChipSpeller</code>, and throws a runtime exception if this variable
 * name is unknown.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class VariableAssembler extends Assembler<Token, TypeOrType<Axiom, Term>, QueryBuilder> {

    Speller speller;

    /**
     * Construct a VariableAssembler that will consult the given speller for the
     * proper spelling of variable names.
     *
     * @param speller
     */
    public VariableAssembler(Speller speller) {
        this.speller = speller;
    }

    /**
     * Pop a token from the stack, extract its string, and push a
     * <code>Variable</code> of that name. Check the spelling of the name with
     * the speller provided in the constructor.
     *
     * @param a
     */
    @Override
    public void workOn(Assembly<Token, TypeOrType<Axiom, Term>, QueryBuilder> a) {
        Token t = a.popTok();
        String properName = speller.getVariableName(t.sval());
        if (properName == null) {
            throw new UnrecognizedVariableException(
                    "No variable named " + t.sval() + " in object model");
        }
        a.pushVal(TypeOrType.fromV(new Variable(properName)));
    }

    private static final Logger LOG
            = Logger.getLogger(VariableAssembler.class.getName());

}
