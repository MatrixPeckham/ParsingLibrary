package com.matrixpeckham.parse.examples.regular;

import static com.matrixpeckham.parse.examples.regular.RegularParser.value;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Repetition;
import com.matrixpeckham.parse.parse.Sequence;
import com.matrixpeckham.parse.parse.chars.CharacterAssembly;
import com.matrixpeckham.parse.parse.chars.Letter;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Show how to use the <code>RegularParser</code> class.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowRegularParser {

    /**
     * Show some examples of matching regular expressions.
     *
     * @param args
     * @throws
     * com.matrixpeckham.parse.examples.regular.RegularExpressionException
     */
    public static void main(String args[])
            throws RegularExpressionException {

        // a*
        Parser<Character, NullCloneable, NullCloneable> aStar = value("a*");
        showMatch(aStar, "");
        showMatch(aStar, "a");
        showMatch(aStar, "aa");
        showMatch(aStar, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        // (a|b)*
        Parser<Character, NullCloneable, NullCloneable> abStar = value("(a|b)*");
        showMatch(abStar, "aabbaabaabba");
        showMatch(abStar, "aabbaabaabbaZ");

        // a few other examples
        showMatch(value("a*a*"), "aaaa");
        showMatch(value("a|bc"), "bc");
        showMatch(value("a|bc|d"), "bc");

        // four letters
        Parser<Character, NullCloneable, NullCloneable> L = new Letter<>();
        Parser<Character, NullCloneable, NullCloneable> L4
                = new Sequence<Character, NullCloneable, NullCloneable>("LLLL").
                add(L).add(L).add(L).add(L);
        showMatch(L4, "java");
        showMatch(L4, "joe");
        showMatch(new Repetition<>(L), "coffee");
    }
    /*
     * Just a little help for main().
     */

    private static void showMatch(
            Parser<Character, NullCloneable, NullCloneable> p, String s) {
        System.out.print(p);
        Assembly<Character, NullCloneable, NullCloneable> a = p.completeMatch(
                new CharacterAssembly<>(s));
        if (a != null) {
            System.out.print(" matches ");
        } else {
            System.out.print(" does not match ");
        }
        System.out.println(s);
    }

    private static final Logger LOG
            = Logger.getLogger(ShowRegularParser.class.getName());

}
