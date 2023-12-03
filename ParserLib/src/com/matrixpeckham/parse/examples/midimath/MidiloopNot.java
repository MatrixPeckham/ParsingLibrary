package com.matrixpeckham.parse.examples.midimath;

import com.matrixpeckham.parse.examples.arithmetic.*;
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
public class MidiloopNot {

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

            Sequence<Token, Double, NullCloneable> plusTerm = new Sequence<>();
            plusTerm.add(new Symbol<Double, NullCloneable>('+').discard());
            plusTerm.add(term());
            plusTerm.setAssembler(new PlusAssembler());

            expression.add(term());
            expression.add(new Repetition<>(plusTerm));
        }
        return expression;
    }

    /**
     * Returns a parser that will recognize either numbers, or arithmetic
     * expressions in parentheses.
     *
     * @return a parser that will recognize either numbers, or arithmetic
     *         expressions in parentheses
     */
    protected Parser<Token, Double, NullCloneable> factor() {
        Alternation<Token, Double, NullCloneable> factor = new Alternation<>();

        Sequence<Token, Double, NullCloneable> parenExpression
                = new Sequence<>();
        parenExpression.add(new Symbol<Double, NullCloneable>('(').discard());
        parenExpression.add(expression());
        parenExpression.add(new Symbol<Double, NullCloneable>(')').discard());

        factor.add(parenExpression);
        factor.add(new Num<Double, NullCloneable>().setAssembler(
                new NumAssembler()));
        return factor;
    }

    /**
     * Demonstrate that this utility class does not loop.
     *
     * @param args
     */
    public static void main(String args[]) {
        Parser<Token, Double, NullCloneable> e = new MidiloopNot().expression();
        Assembly<Token, Double, NullCloneable> out = e.bestMatch(
                new TokenAssembly<>("(7 + 13) * 5"));
        System.out.println(out.pop());
    }

    /**
     * Returns a parser that will recognize arithmetic expressions containing
     * just multiplication.
     *
     * @return a parser that will recognize arithmetic expressions containing
     *         just multiplication
     */
    protected Parser<Token, Double, NullCloneable> term() {
        Sequence<Token, Double, NullCloneable> term = new Sequence<>();

        Sequence<Token, Double, NullCloneable> timesFactor = new Sequence<>();
        timesFactor.add(new Symbol<Double, NullCloneable>('*').discard());
        timesFactor.add(factor());
        timesFactor.setAssembler(new TimesAssembler());

        term.add(factor());
        term.add(new Repetition<>(timesFactor));
        return term;
    }

    private static final Logger LOG
            = Logger.getLogger(MidiloopNot.class.getName());

}
