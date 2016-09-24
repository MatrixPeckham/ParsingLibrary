package com.matrixpeckham.parse.examples.midimath;

import com.matrixpeckham.parse.examples.arithmetic.NumAssembler;
import com.matrixpeckham.parse.examples.arithmetic.PlusAssembler;
import com.matrixpeckham.parse.examples.arithmetic.TimesAssembler;
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

public class Midiloop {

    /**
     * Returns a parser that will recognize a Midimath expression.
     *
     * @return a parser that will recognize a Midimath expression
     */
    public Parser<Token, Double, NullCloneable> expression() {
        Sequence<Token, Double, NullCloneable> expression = new Sequence<>();

        Sequence<Token, Double, NullCloneable> plusTerm = new Sequence<>();
        plusTerm.add(new Symbol<Double, NullCloneable>('+').discard());
        plusTerm.add(term());
        plusTerm.setAssembler(new PlusAssembler());

        expression.add(term());
        expression.add(new Repetition<>(plusTerm));
        return expression;
    }

    /**
     * Returns a parser that will recognize either numbers, or arithmetic
     * expressions in parentheses.
     *
     * @return a parser that will recognize either numbers, or arithmetic
     * expressions in parentheses
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
     * Demonstrate an infinite loop!
     *
     * @param args
     */
    public static void main(String args[]) {

        Parser<Token, Double, NullCloneable> e = new Midiloop().expression(); // hang or crash!

        Assembly<Token, Double, NullCloneable> out = e.bestMatch(
                new TokenAssembly<>("(7 + 13) * 5"));
        System.out.println(out.pop());
    }

    /**
     * Returns a parser that will recognize arithmetic expressions containing
     * just multiplication.
     *
     * @return a parser that will recognize arithmetic expressions containing
     * just multiplication
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

    private static final Logger LOG = Logger.getLogger(Midiloop.class.getName());

}
