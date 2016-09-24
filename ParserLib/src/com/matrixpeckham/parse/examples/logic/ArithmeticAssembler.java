package com.matrixpeckham.parse.examples.logic;

import com.matrixpeckham.parse.engine.ArithmeticOperator;
import com.matrixpeckham.parse.engine.ArithmeticTerm;
import com.matrixpeckham.parse.engine.Axiom;
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
 * This assembler pops two arithmetic operands, builds an ArithmeticOperator
 * from them, and pushes it.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ArithmeticAssembler extends Assembler<Token, TypeOrType<Axiom, Term>, QueryBuilder> {

    /**
     * the character which represents an arithmetic operator
     */
    protected char operator;

    /**
     * Constructs an assembler that will stack an ArithmeticOperator with the
     * specified operator.
     *
     * @param operator
     */
    public ArithmeticAssembler(char operator) {
        this.operator = operator;
    }

    /**
     * Pop two arithmetic operands, build an ArithmeticOperator from them, and
     * push it.
     *
     * @param a the assembly to work on
     */
    @Override
    public void workOn(Assembly<Token, TypeOrType<Axiom, Term>, QueryBuilder> a) {
        Term operand1T = a.popVal().asV();
        Term operand0T = a.popVal().asV();
        if (operand1T instanceof ArithmeticTerm
                && operand0T instanceof ArithmeticTerm) {
            ArithmeticTerm operand1 = (ArithmeticTerm) operand1T;
            ArithmeticTerm operand0 = (ArithmeticTerm) operand0T;
            a.pushVal(TypeOrType.fromV(new ArithmeticOperator(
                    operator, operand0, operand1)));
        } else {//shouldn't happen
            throw new LogikusException(
                    "Arithmetic Assemblyer needs two arithmetic "
                    + "terms and didn't find them");
        }
    }

    private static final Logger LOG
            = Logger.getLogger(ArithmeticAssembler.class.getName());

}
