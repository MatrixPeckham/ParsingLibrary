package com.matrixpeckham.parse.examples.midimath;

import com.matrixpeckham.parse.examples.arithmetic.NumAssembler;
import com.matrixpeckham.parse.examples.arithmetic.PlusAssembler;
import com.matrixpeckham.parse.examples.arithmetic.TimesAssembler;
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

public class Midimath {

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
     * Demonstrate a parser for Midimath.
     *
     * @param args
     */
    public static void main(String args[]) {
        Assembly<Token, Double, NullCloneable> out
                = new Midimath().expression().bestMatch(
                        new TokenAssembly<>("2 + 3 * 7 + 19"));
        System.out.println(out.popVal());
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

        Num<Double, NullCloneable> n = new Num<>();
        n.setAssembler(new NumAssembler());

        Sequence<Token, Double, NullCloneable> timesNum = new Sequence<>();
        timesNum.add(new Symbol<Double, NullCloneable>('*').discard());
        timesNum.add(n);
        timesNum.setAssembler(new TimesAssembler());

        term.add(n);
        term.add(new Repetition<>(timesNum));
        return term;
    }

    private static final Logger LOG = Logger.getLogger(Midimath.class.getName());

}
