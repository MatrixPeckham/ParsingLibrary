package com.matrixpeckham.parse.examples.reserved;

import com.matrixpeckham.parse.parse.Alternation;
import com.matrixpeckham.parse.parse.Assembler;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Repetition;
import com.matrixpeckham.parse.parse.Sequence;
import com.matrixpeckham.parse.parse.tokens.Symbol;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.Tokenizer;
import com.matrixpeckham.parse.parse.tokens.Word;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.logging.Logger;

public class VolumeQuery2 {

    /**
     * Return a parser that recognizes the grammar:
     *
     * query = (Word | volume)* '?';
     *
     * @return a parser that recognizes queries containing volumes and random
     * words.
     */
    public static Parser<Token, String, NullCloneable> query() {

        Parser<Token, String, NullCloneable> a
                = new Alternation<Token, String, NullCloneable>()
                .add(new Word<>())
                .add(volume());

        Parser<Token, String, NullCloneable> s
                = new Sequence<Token, String, NullCloneable>()
                .add(new Repetition<>(a))
                .add(new Symbol<>('?'));

        return s;
    }

    /**
     * Return a customized tokenizer that uses WordOrReservedState in place of
     * WordState.
     *
     * @return a custom tokenizer that uses WordOrReservedState in place of
     * WordState
     */
    public static Tokenizer tokenizer() {

        Tokenizer t = new Tokenizer();

        WordOrReservedState wors = new WordOrReservedState();
        wors.addReservedWord("cups");
        wors.addReservedWord("gallon");
        wors.addReservedWord("liter");

        t.setCharacterState('a', 'z', wors);
        t.setCharacterState('A', 'Z', wors);
        t.setCharacterState(0xc0, 0xff, wors);

        return t;
    }
    /*
     * Return a parser that recognizes the grammar:
     *
     *    volume = "cups" | "gallon" | "liter";
     *
     * This parser stacks the recognized word as an
     * argument to "VOL()".
     */

    /**
     *
     * @return
     */
    protected static Parser<Token, String, NullCloneable> volume() {

        Parser<Token, String, NullCloneable> p
                = new ReservedWord<>();

        // an anonymous Assembler subclass notes volume matches
        p.setAssembler(new Assembler<Token, String, NullCloneable>() {
            @Override
            public void workOn(Assembly<Token, String, NullCloneable> a) {
                String o = a.popVal();
                a.push("VOL(" + o + ")");
            }
        });

        return p;
    }

    private static final Logger LOG
            = Logger.getLogger(VolumeQuery2.class.getName());

}
