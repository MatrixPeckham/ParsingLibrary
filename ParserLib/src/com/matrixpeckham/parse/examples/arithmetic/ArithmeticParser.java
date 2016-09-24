package com.matrixpeckham.parse.examples.arithmetic;

import com.matrixpeckham.parse.parse.Alternation;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Repetition;
import com.matrixpeckham.parse.parse.Sequence;
import com.matrixpeckham.parse.parse.tokens.Num;
import com.matrixpeckham.parse.parse.tokens.Symbol;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.TokenAssembly;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.logging.Logger;

public class ArithmeticParser {

    /**
     *
     */
    protected Sequence<Token, Double, NullCloneable> expression;

    /**
     *
     */
    protected Alternation<Token, Double, NullCloneable> factor;

    /*
     * Returns a parser that for the grammar rule:
     *
     *     divideFactor = '/' factor;
     *
     * This parser has an assembler that will pop two
     * numbers from the stack and push their quotient.
     */
    /**
     *
     * @return
     */
    protected Parser<Token, Double, NullCloneable> divideFactor() {
        Sequence<Token, Double, NullCloneable> s = new Sequence<>();
        s.add(new Symbol<Double, NullCloneable>('/').discard());
        s.add(factor());
        s.setAssembler(new DivideAssembler());
        return s;
    }
    /*
     * Returns a parser that for the grammar rule:
     *
     *     expFactor = '^' factor;
     *
     * This parser has an assembler that will pop two
     * numbers from the stack and push the result of
     * exponentiating the lower number to the upper one.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, Double, NullCloneable> expFactor() {
        Sequence<Token, Double, NullCloneable> s = new Sequence<>();
        s.add(new Symbol<Double, NullCloneable>('^').discard());
        s.add(factor());
        s.setAssembler(new ExpAssembler());
        return s;
    }

    /**
     * Returns a parser that will recognize an arithmetic expression. (Identical
     * to <code>start()</code>).
     *
     * @return a parser that will recognize an arithmetic expression
     */
    public Parser<Token, Double, NullCloneable> expression() {
        /*
         * This use of a static variable avoids the infinite
         * recursion inherent in the grammar.
         */
        if (expression == null) {

            // expression = term (plusTerm | minusTerm)*;
            expression = new Sequence<>("expression");
            expression.add(term());

            Alternation<Token, Double, NullCloneable> a
                    = new Alternation<>();
            a.add(plusTerm());
            a.add(minusTerm());

            expression.add(new Repetition<>(a));
        }
        return expression;
    }
    /*
     * Returns a parser that for the grammar rule:
     *
     *     factor = phrase expFactor | phrase;
     */

    /**
     *
     * @return
     */
    protected Parser<Token, Double, NullCloneable> factor() {
        /*
         * This use of a static variable avoids the infinite
         * recursion inherent in the grammar; factor depends
         * on expFactor, and expFactor depends on factor.
         */
        if (factor == null) {
            factor = new Alternation<>("factor");

            Sequence<Token, Double, NullCloneable> s = new Sequence<>();
            s.add(phrase());
            s.add(expFactor());

            factor.add(s);
            factor.add(phrase());
        }
        return factor;
    }
    /*
     * Returns a parser that for the grammar rule:
     *
     *     minusTerm = '-' term;
     *
     * This parser has an assembler that will pop two
     * numbers from the stack and push their difference.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, Double, NullCloneable> minusTerm() {
        Sequence<Token, Double, NullCloneable> s = new Sequence<>();
        s.add(new Symbol<Double, NullCloneable>('-').discard());
        s.add(term());
        s.setAssembler(new MinusAssembler());
        return s;
    }
    /*
     * Returns a parser that for the grammar rule:
     *
     *    phrase = '(' expression ')' | Num;
     *
     * This parser adds an assembler to Num, that will
     * replace the top token in the stack with the token's
     * Double value.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, Double, NullCloneable> phrase() {
        Alternation<Token, Double, NullCloneable> phrase
                = new Alternation<>("phrase");

        Sequence<Token, Double, NullCloneable> s = new Sequence<>();
        s.add(new Symbol<Double, NullCloneable>('(').discard());
        s.add(expression());
        s.add(new Symbol<Double, NullCloneable>(')').discard());
        phrase.add(s);

        phrase.add(new Num<Double, NullCloneable>().setAssembler(
                new NumAssembler()));
        return phrase;
    }
    /*
     * Returns a parser that for the grammar rule:
     *
     *     plusTerm = '+' term;
     *
     * This parser has an assembler that will pop two
     * numbers from the stack and push their sum.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, Double, NullCloneable> plusTerm() {
        Sequence<Token, Double, NullCloneable> s = new Sequence<>();
        s.add(new Symbol<Double, NullCloneable>('+').discard());
        s.add(term());
        s.setAssembler(new PlusAssembler());
        return s;
    }

    /**
     * Returns a parser that will recognize an arithmetic expression.
     *
     * @return a parser that will recognize an arithmetic expression
     */
    public static Parser<Token, Double, NullCloneable> start() {
        return new ArithmeticParser().expression();
    }
    /*
     * Returns a parser that for the grammar rule:
     *
     *    term = factor (timesFactor | divideFactor)*;
     */

    /**
     *
     * @return
     */
    protected Parser<Token, Double, NullCloneable> term() {
        Sequence<Token, Double, NullCloneable> s = new Sequence<>(
                "term");
        s.add(factor());

        Alternation<Token, Double, NullCloneable> a
                = new Alternation<>();
        a.add(timesFactor());
        a.add(divideFactor());

        s.add(new Repetition<>(a));
        return s;
    }
    /*
     * Returns a parser that for the grammar rule:
     *
     *     timesFactor = '*' factor;
     *
     * This parser has an assembler that will pop two
     * numbers from the stack and push their product.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, Double, NullCloneable> timesFactor() {
        Sequence<Token, Double, NullCloneable> s = new Sequence<>();
        s.add(new Symbol<Double, NullCloneable>('*').discard());
        s.add(factor());
        s.setAssembler(new TimesAssembler());
        return s;
    }

    /**
     * Return the value of an arithmetic expression given in a string. This
     * method is a fa�ade, which provides an example of how to use the parser.
     *
     * @return the value of an arithmetic expression given in a string
     *
     * @param s the string to evaluate.
     *
     * @exception ArithmeticExpressionException if this parser does not
     * recognize the given string as a valid expression
     */
    public static double value(String s)
            throws ArithmeticExpressionException {

        TokenAssembly<Double, NullCloneable> ta
                = new TokenAssembly<>(s);
        Assembly<Token, Double, NullCloneable> a = start().
                completeMatch(ta);
        if (a == null) {
            throw new ArithmeticExpressionException(
                    "Improperly formed arithmetic expression");
        }
        Double d;
        try {
            d = a.popVal();
        } catch (Exception e) {
            throw new ArithmeticExpressionException(
                    "Internal error in ArithmeticParser");
        }
        return d;
    }

    private static final Logger LOG
            = Logger.getLogger(ArithmeticParser.class.getName());

}
