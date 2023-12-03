package com.matrixpeckham.parse.examples.reserved;

import com.matrixpeckham.parse.parse.*;
import com.matrixpeckham.parse.parse.tokens.*;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.logging.Logger;

/**
 * This class shows the use of a customized tokenizer, and
 * the use of a terminal that looks for the new token type.
 */
public class VolumeQuery2 {

    /**
     * Return a parser that recognizes the grammar:
     * <p>
     * query = (Word | volume)* '?';
     *
     * @return a parser that recognizes queries containing volumes and random
     *         words.
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
     *         WordState
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

    /**
     * Return a parser that recognizes the grammar:
     * <p>
     * volume = "cups" | "gallon" | "liter";
     * <p>
     * This parser stacks the recognized word as an
     * argument to "VOL()".
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
