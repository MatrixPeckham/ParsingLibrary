package com.matrixpeckham.parse.examples.preface;

import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Repetition;
import com.matrixpeckham.parse.parse.Terminal;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.TokenAssembly;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.logging.Logger;

/**
 * This is a "Hello world" program. Once you get this working on your computer,
 * you can get any example in this book to work.
 */
public class ShowHello {

    /**
     * Create a little parser and use it to recognize "Hello world!".
     *
     * @param args
     */
    public static void main(String[] args) {
        Terminal<Token, NullCloneable, NullCloneable> t = new Terminal<>();
        Repetition<Token, NullCloneable, NullCloneable> r = new Repetition<>(t);

        Assembly<Token, NullCloneable, NullCloneable> in = new TokenAssembly<>(
                "Hello world!");
        Assembly<Token, NullCloneable, NullCloneable> out = r.completeMatch(in);

        System.out.println(out.getStack());
    }

    private static final Logger LOG
            = Logger.getLogger(ShowHello.class.getName());

}
