package com.matrixpeckham.parse.examples.query;

import com.matrixpeckham.parse.engine.Axiom;
import com.matrixpeckham.parse.engine.Term;
import com.matrixpeckham.parse.examples.logic.ArithmeticAssembler;
import com.matrixpeckham.parse.examples.logic.AtomAssembler;
import com.matrixpeckham.parse.parse.*;
import com.matrixpeckham.parse.parse.tokens.*;
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
 * This utility class provides support to the Jaql parser, specifically for
 * <code>expression()</code> and <code>comparison()</code> subparsers.
 * <p>
 * The grammar this class supports is:
 * <p>
 * <blockquote><pre>
 *     comparison = arg operator arg;
 *     arg        = expression | QuotedString;
 *     expression = term ('+' term | '-' term)*;
 *     term       = factor ('*' factor | '/' factor)*;
 *     factor     = '(' expression ')' | Num | variable;
 *     variable   = Word;
 *     operator   = "&lt;" | "&gt;" | "=" | "&lt;=" | "&gt;=" | "!=";
 * </pre></blockquote>
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ComparisonParser {

    /**
     *
     */
    protected Sequence<Token, TypeOrType<Axiom, Term>, QueryBuilder> expression;

    /**
     *
     */
    protected Speller speller;

    /**
     * Construct a ComparisonParser that will consult the given speller for the
     * proper spelling of variable names.
     *
     * @param speller
     */
    public ComparisonParser(Speller speller) {
        this.speller = speller;
    }

    /**
     * Returns a parser that will recognize a comparison argument.
     *
     * @return
     */
    public Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> arg() {

        // arg = expression | QuotedString;
        Alternation<Token, TypeOrType<Axiom, Term>, QueryBuilder> a
                = new Alternation<>();
        a.add(expression());
        a.add(
                new QuotedString<TypeOrType<Axiom, Term>, QueryBuilder>()
                        .setAssembler(new AtomAssembler()));
        return a;
    }

    /**
     * Returns a parser that will recognize a comparison.
     *
     * @return
     */
    public Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> comparison() {
        Sequence<Token, TypeOrType<Axiom, Term>, QueryBuilder> s
                = new Sequence<>("comparison");
        s.add(arg());
        s.add(operator());
        s.add(arg());
        s.setAssembler(new ComparisonAssembler());
        return s;
    }

    /*
     * Recognize '/' followed by a factor.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> divideFactor() {
        Sequence<Token, TypeOrType<Axiom, Term>, QueryBuilder> s
                = new Sequence<>("divideFactor");
        s.add(new Symbol<TypeOrType<Axiom, Term>, QueryBuilder>('/').discard());
        s.add(factor());
        s.setAssembler(new ArithmeticAssembler('/'));
        return s;
    }

    /**
     * Returns a parser that will recognize an arithmetic expression.
     *
     * @return
     */
    public Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> expression() {
        /*
         * This use of a static variable avoids the infinite
         * recursion inherent in the language definition.
         */
        if (expression == null) {

            // expression = term ('+' term | '-' term)*;
            expression = new Sequence<>("expression");
            expression.add(term());

            // second part
            Alternation<Token, TypeOrType<Axiom, Term>, QueryBuilder> a
                    = new Alternation<>();
            a.add(plusTerm());
            a.add(minusTerm());
            expression.add(new Repetition<>(a));
        }
        return expression;
    }

    /*
     * Recognize an expression in parens, or a number, or a
     * variable.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> factor() {
        // factor = '(' expression ')' | Num | variable;
        Alternation<Token, TypeOrType<Axiom, Term>, QueryBuilder> factor
                = new Alternation<>("factor");

        //  '(' expression ')'
        Sequence<Token, TypeOrType<Axiom, Term>, QueryBuilder> s
                = new Sequence<>();
        s.add(new Symbol<TypeOrType<Axiom, Term>, QueryBuilder>('(').discard());
        s.add(expression());
        s.add(new Symbol<TypeOrType<Axiom, Term>, QueryBuilder>(')').discard());
        factor.add(s);

        // Num | variable
        factor.add(new Num<TypeOrType<Axiom, Term>, QueryBuilder>().
                setAssembler(new AtomAssembler()));
        factor.add(variable());

        return factor;
    }

    /*
     * Recognize '-' followed by a term.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> minusTerm() {
        Sequence<Token, TypeOrType<Axiom, Term>, QueryBuilder> s
                = new Sequence<>("minusTerm");
        s.add(new Symbol<TypeOrType<Axiom, Term>, QueryBuilder>('-').discard());
        s.add(term());
        s.setAssembler(new ArithmeticAssembler('-'));
        return s;
    }

    /*
     * Recognize an operator.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> operator() {
        Alternation<Token, TypeOrType<Axiom, Term>, QueryBuilder> a
                = new Alternation<>("operator");
        a.add(new Symbol<>('<'));
        a.add(new Symbol<>('>'));
        a.add(new Symbol<>('='));
        a.add(new Symbol<>("<="));
        a.add(new Symbol<>(">="));
        a.add(new Symbol<>("!="));
        return a;
    }

    /*
     * Recognize '+' followed by a term.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> plusTerm() {
        Sequence<Token, TypeOrType<Axiom, Term>, QueryBuilder> s
                = new Sequence<>("plusTerm");
        s.add(new Symbol<TypeOrType<Axiom, Term>, QueryBuilder>('+').discard());
        s.add(term());
        s.setAssembler(new ArithmeticAssembler('+'));
        return s;
    }

    /*
     * Recognize a "term", per the language definition.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> term() {
        // term = factor ('*' factor | '/' factor)*;
        Sequence<Token, TypeOrType<Axiom, Term>, QueryBuilder> term
                = new Sequence<>("term");

        // first part
        term.add(factor());

        // second part
        Alternation<Token, TypeOrType<Axiom, Term>, QueryBuilder> a
                = new Alternation<>();
        a.add(timesFactor());
        a.add(divideFactor());

        term.add(new Repetition<>(a));
        return term;
    }

    /*
     * Recognize '*' followed by a factor.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> timesFactor() {
        Sequence<Token, TypeOrType<Axiom, Term>, QueryBuilder> s
                = new Sequence<>("timesFactor");
        s.add(new Symbol<TypeOrType<Axiom, Term>, QueryBuilder>('*').discard());
        s.add(factor());
        s.setAssembler(new ArithmeticAssembler('*'));
        return s;
    }

    /*
     * Recognizes any word.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> variable() {
        return new Word<TypeOrType<Axiom, Term>, QueryBuilder>()
                .setAssembler(new VariableAssembler(speller));
    }

    private static final Logger LOG
            = Logger.getLogger(ComparisonParser.class.getName());

}
