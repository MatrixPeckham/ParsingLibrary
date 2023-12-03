package com.matrixpeckham.parse.examples.regular;

import com.matrixpeckham.parse.parse.*;
import com.matrixpeckham.parse.parse.chars.*;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.logging.Logger;

/**
 * This class provides a parser that recognizes regular
 * expressions.
 * <p>
 * Regular expressions are a "metalanguage", which means they
 * form a language for describing languages. For example,
 * <code>a*</code> is a regular expression that describes a
 * simple language whose elements are strings composed of 0
 * or more <code>a's</code>. Thus the result of parsing
 * <code>a*</code> is a new parser, namely a
 * parser that will match strings of <code>a's</code>.
 * <p>
 * This class exists to show how a simple regular expression
 * parser works. It recognizes expressions according to
 * the following rules.
 * <p>
 * <blockquote><pre>
 *     expression    = term orTerm*;
 *     term          = factor nextFactor*;
 *     orTerm        = '|' term;
 *     factor        = phrase | phraseStar;
 *     nextFactor    = factor;
 *     phrase        = letterOrDigit | '(' expression ')';
 *     phraseStar    = phrase '*';
 *     letterOrDigit = Letter | Digit;
 * </pre></blockquote>
 * <p>
 * These rules recognize conventional operator precedence.
 * They also avoid the problem of left recursion, and their
 * implementation avoids problems with the infinite loop
 * inherent in the cyclic dependencies of the rules.
 */
public class RegularParser {

    /**
     *
     */
    protected Sequence<Character, Parser<Character, NullCloneable, NullCloneable>, NullCloneable> expression;

    /**
     * Returns a parser that will recognize a regular expression. (Identical to
     * <code>start()</code>).
     *
     * @return a parser that will recognize a regular expression
     */
    public Parser<Character, Parser<Character, NullCloneable, NullCloneable>, NullCloneable> expression() {
        if (expression == null) {

            // expression = term orTerm*;
            expression = new Sequence<>();
            expression.add(term());
            expression.add(new Repetition<>(orTerm()));
        }
        return expression;
    }

    /**
     * Returns a parser that for the grammar rule:
     * <p>
     * factor = phrase | phraseStar;
     *
     * @return
     */
    protected Parser<Character, Parser<Character, NullCloneable, NullCloneable>, NullCloneable> factor() {
        Alternation<Character, Parser<Character, NullCloneable, NullCloneable>, NullCloneable> a
                = new Alternation<>();
        a.add(phrase());
        a.add(phraseStar());
        return a;
    }

    /**
     * Returns a parser that for the grammar rule:
     * <p>
     * letterOrDigit = Letter | Digit;
     * <p>
     * This parser has an assembler that will pop a
     * character and push a SpecificChar parser in its
     * place.
     *
     * @return
     */
    protected Parser<Character, Parser<Character, NullCloneable, NullCloneable>, NullCloneable> letterOrDigit() {
        Alternation<Character, Parser<Character, NullCloneable, NullCloneable>, NullCloneable> a
                = new Alternation<>();
        a.add(new Letter<>());
        a.add(new Digit<>());
        a.setAssembler(new CharAssembler());
        return a;
    }

    /**
     * Returns a parser that for the grammar rule:
     * <p>
     * nextFactor = factor;
     * <p>
     * This parser has an assembler that will pop two
     * parsers and push a Sequence of them.
     *
     * @return
     */
    protected Parser<Character, Parser<Character, NullCloneable, NullCloneable>, NullCloneable> nextFactor() {
        Parser<Character, Parser<Character, NullCloneable, NullCloneable>, NullCloneable> p
                = factor();
        p.setAssembler(new AndAssembler());
        return p;
    }

    /**
     * Returns a parser that for the grammar rule:
     * <p>
     * orTerm = '|' term;
     * <p>
     * This parser has an assembler that will pop two
     * parsers and push an Alternation of them.
     *
     * @return
     */
    protected Parser<Character, Parser<Character, NullCloneable, NullCloneable>, NullCloneable> orTerm() {
        Sequence<Character, Parser<Character, NullCloneable, NullCloneable>, NullCloneable> s
                = new Sequence<>();
        s.
                add(new SpecificChar<Parser<Character, NullCloneable, NullCloneable>, NullCloneable>(
                        '|').discard());
        s.add(term());
        s.setAssembler(new OrAssembler());
        return s;
    }

    /**
     * Returns a parser that for the grammar rule:
     * <p>
     * phrase = letterOrDigit | '(' expression ')';
     *
     * @return
     */
    protected Parser<Character, Parser<Character, NullCloneable, NullCloneable>, NullCloneable> phrase() {
        Alternation<Character, Parser<Character, NullCloneable, NullCloneable>, NullCloneable> a
                = new Alternation<>();
        a.add(letterOrDigit());

        Sequence<Character, Parser<Character, NullCloneable, NullCloneable>, NullCloneable> s
                = new Sequence<>();
        s.
                add(new SpecificChar<Parser<Character, NullCloneable, NullCloneable>, NullCloneable>(
                        '(').discard());
        s.add(expression());
        s.
                add(new SpecificChar<Parser<Character, NullCloneable, NullCloneable>, NullCloneable>(
                        ')').discard());

        a.add(s);
        return a;
    }

    /**
     * Returns a parser that for the grammar rule:
     * <p>
     * phraseStar = phrase '*';
     * <p>
     * This parser has an assembler that will pop a
     * parser and push a Repetition of it.
     *
     * @return
     */
    protected Parser<Character, Parser<Character, NullCloneable, NullCloneable>, NullCloneable> phraseStar() {
        Sequence<Character, Parser<Character, NullCloneable, NullCloneable>, NullCloneable> s
                = new Sequence<>();
        s.add(phrase());
        s.
                add(new SpecificChar<Parser<Character, NullCloneable, NullCloneable>, NullCloneable>(
                        '*').discard());
        s.setAssembler(new StarAssembler());
        return s;
    }

    /**
     * Returns a parser that will recognize a regular expression.
     *
     * @return a parser that will recognize a regular expression
     */
    public static Parser<Character, Parser<Character, NullCloneable, NullCloneable>, NullCloneable> start() {
        return new RegularParser().expression();
    }

    /**
     * Returns a parser that for the grammar rule:
     * <p>
     * term = factor nextFactor*;
     *
     * @return
     */
    protected Parser<Character, Parser<Character, NullCloneable, NullCloneable>, NullCloneable> term() {
        Sequence<Character, Parser<Character, NullCloneable, NullCloneable>, NullCloneable> term
                = new Sequence<>();
        term.add(factor());
        term.add(new Repetition<>(nextFactor()));
        return term;
    }

    /**
     * Return a parser that will match a <code>
     * CharacterAssembly</code>, according to the value of a regular expression
     * given in a string.
     * <p>
     * For example, given the string <code>a*</code>, this method will return a
     * parser which will match any element of the set
     * <code>{"", "a", "aa", "aaa", ...}</code>.
     *
     * @return a parser that will match a <code>
     *         CharacterAssembly</code>, according to the value of a regular expression
     *         in the given string
     *
     * @param s the string to evaluate
     *
     * @exception RegularExpressionException if this parser does not recognize
     *                                       the given string as a valid expression
     */
    public static Parser<Character, NullCloneable, NullCloneable> value(String s)
            throws RegularExpressionException {

        CharacterAssembly<Parser<Character, NullCloneable, NullCloneable>, NullCloneable> c
                = new CharacterAssembly<>(s);
        Assembly<Character, Parser<Character, NullCloneable, NullCloneable>, NullCloneable> a
                = start().completeMatch(c);
        if (a == null) {
            throw new RegularExpressionException(
                    "Improperly formed regular expression");
        }
        Parser<Character, NullCloneable, NullCloneable> p;
        try {
            p = a.popVal();
        } catch (Exception e) {
            throw e;//new RegularExpressionException(
            //"Internal error in RegularParser");
        }
        return p;
    }

    private static final Logger LOG
            = Logger.getLogger(RegularParser.class.getName());

}
