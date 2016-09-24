package com.matrixpeckham.parse.examples.regular;

import com.matrixpeckham.parse.parse.Alternation;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Repetition;
import com.matrixpeckham.parse.parse.Sequence;
import com.matrixpeckham.parse.parse.chars.CharacterAssembly;
import com.matrixpeckham.parse.parse.chars.Digit;
import com.matrixpeckham.parse.parse.chars.Letter;
import com.matrixpeckham.parse.parse.chars.SpecificChar;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.logging.Logger;

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
    /*
     * Returns a parser that for the grammar rule:
     *
     *    factor = phrase | phraseStar;
     */

    /**
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
    /*
     * Returns a parser that for the grammar rule:
     *
     *    letterOrDigit = Letter | Digit;
     *
     * This parser has an assembler that will pop a
     * character and push a SpecificChar parser in its
     * place.
     */

    /**
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
    /*
     * Returns a parser that for the grammar rule:
     *
     *    nextFactor = factor;
     *
     * This parser has an assembler that will pop two
     * parsers and push a Sequence of them.
     */

    /**
     *
     * @return
     */
    protected Parser<Character, Parser<Character, NullCloneable, NullCloneable>, NullCloneable> nextFactor() {
        Parser<Character, Parser<Character, NullCloneable, NullCloneable>, NullCloneable> p
                = factor();
        p.setAssembler(new AndAssembler());
        return p;
    }
    /*
     * Returns a parser that for the grammar rule:
     *
     *    orTerm = '|' term;
     *
     * This parser has an assembler that will pop two
     * parsers and push an Alternation of them.
     */

    /**
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
    /*
     * Returns a parser that for the grammar rule:
     *
     *     phrase = letterOrDigit | '(' expression ')';
     */

    /**
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
    /*
     * Returns a parser that for the grammar rule:
     *
     *    phraseStar = phrase '*';
     *
     * This parser has an assembler that will pop a
     * parser and push a Repetition of it.
     */

    /**
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
    /*
     * Returns a parser that for the grammar rule:
     *
     *    term = factor nextFactor*;
     */

    /**
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
     *
     * For example, given the string <code>a*</code>, this method will return a
     * parser which will match any element of the set
     * <code>{"", "a", "aa", "aaa", ...}</code>.
     *
     * @return a parser that will match a <code>
     *         CharacterAssembly</code>, according to the value of a regular expression
     * in the given string
     *
     * @param s the string to evaluate
     *
     * @exception RegularExpressionException if this parser does not recognize
     * the given string as a valid expression
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
