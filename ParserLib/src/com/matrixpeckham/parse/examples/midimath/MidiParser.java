package com.matrixpeckham.parse.examples.midimath;

import com.matrixpeckham.parse.examples.arithmetic.MinusAssembler;
import com.matrixpeckham.parse.examples.arithmetic.NumAssembler;
import com.matrixpeckham.parse.parse.*;
import com.matrixpeckham.parse.parse.tokens.*;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.logging.Logger;

/**
 * This class creates and uses a parser that aims
 * to recognize simple arithmetic expressions, but gets
 * caught in a loop. Allowable expressions include the
 * use of multiplication, addition and parentheses. The
 * grammar for this language is:
 * <p>
 * <blockquote><pre>
 *     expression = term ('+' term)*;
 *     term       = factor ('*' factor)*;
 *     factor     = '(' expression ')' | Num;
 * </pre></blockquote>
 * <p>
 * This class implements this grammar as a utility class,
 * and avoids looping while building the subparsers.
 */
public class MidiParser {

    /**
     *
     */
    static Sequence<Token, Double, NullCloneable> expression;

    /**
     * Returns a parser that will recognize a Midimath expression.
     *
     * @return a parser that will recognize a Midimath expression
     */
    public synchronized Parser<Token, Double, NullCloneable> expression() {
        if (expression == null) {
            expression = new Sequence<>();
            expression.add(term());
            expression.add(new Repetition<>(minusTerm()));
        }
        return expression;
    }

    /**
     * Demonstrate that this utility class does not loop.
     *
     * @param args
     */
    public static void main(String args[]) {
        Parser<Token, Double, NullCloneable> e = new MidiParser().expression();
        Assembly<Token, Double, NullCloneable> out = e.bestMatch(
                new TokenAssembly<>("111 - (11 - 1)"));
        System.out.println(out.popVal());
    }

    /*
     * Returns a parser that for the grammar rule:
     *
     * minusTerm = '-' term;
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
     * term = '(' expression ')' | Num;
     *
     * This parser adds an assembler to Num, that will
     * replace the top token in the stack with the token's
     * Double value.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, Double, NullCloneable> term() {

        Sequence<Token, Double, NullCloneable> s = new Sequence<>();
        s.add(new Symbol<Double, NullCloneable>('(').discard());
        s.add(expression());
        s.add(new Symbol<Double, NullCloneable>(')').discard());

        Alternation<Token, Double, NullCloneable> a = new Alternation<>();
        a.add(s);
        a.add(new Num<Double, NullCloneable>().setAssembler(new NumAssembler()));
        return a;
    }

    private static final Logger LOG
            = Logger.getLogger(MidiParser.class.getName());

}
