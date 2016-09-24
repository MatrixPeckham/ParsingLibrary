package com.matrixpeckham.parse.examples.pretty;

import com.matrixpeckham.parse.parse.Alternation;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.tokens.Literal;
import com.matrixpeckham.parse.parse.tokens.TokenAssembly;
import java.util.Iterator;
import java.util.logging.Logger;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Show how the pretty printer displays a deep match of alternations. The
 * grammar this class shows is:
 *
 * <blockquote><pre>
 *     reptile     = crocodilian | squamata;
 *     crocodilian = crocodile | alligator;
 *     squamata    = snake | lizard;
 *     crocodile   = "nileCroc" | "cubanCroc";
 *     alligator   = "chineseGator" | "americanGator";
 *     snake       = "cobra" | "python";
 *     lizard      = "gecko" | "iguana";
 * </pre></blockquote>
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ShowPrettyAlternations {

    /**
     * Returns a parser that recognizes some alligators.
     *
     * @return
     */
    public static Parser alligator() {
        Alternation a = new Alternation("<alligator>");
        a.add(new Literal("chineseGator"));
        a.add(new Literal("americanGator"));
        return a;
    }

    /**
     * Returns a parser that recognizes some crocs.
     *
     * @return
     */
    public static Parser crocodile() {
        Alternation a = new Alternation("<crocodile>");
        a.add(new Literal("nileCroc"));
        a.add(new Literal("cubanCroc"));
        return a;
    }

    /**
     * Returns a parser that recognizes members of the crocodilian order.
     *
     * @return
     */
    public static Parser crocodilian() {
        Alternation a = new Alternation("<crocodilian>");
        a.add(crocodile());
        a.add(alligator());
        return a;
    }

    /**
     * Returns a parser that recognizes some lizards.
     *
     * @return
     */
    public static Parser lizard() {
        Alternation a = new Alternation("<lizard>");
        a.add(new Literal("gecko"));
        a.add(new Literal("iguana"));
        return a;
    }

    /**
     * Show how a series of alternations appear when pretty- printed.
     *
     * @param args
     */
    public static void main(String[] args) {
        PrettyParser p = new PrettyParser(reptile());
        p.setShowLabels(true);
        TokenAssembly ta = new TokenAssembly("gecko");
        Iterator e = p.parseTrees(ta).iterator();
        while (e.hasNext()) {
            System.out.println("The input parses as:");
            System.out.println("---------------------------");
            System.out.println(e.next());
        }
    }

    /**
     * Returns a parser that recognizes some reptiles.
     *
     * @return
     */
    public static Parser reptile() {
        Alternation a = new Alternation("<reptile>");
        a.add(crocodilian());
        a.add(squamata());
        return a;
    }

    /**
     * Returns a parser that recognizes some snakes.
     *
     * @return
     */
    public static Parser snake() {
        Alternation a = new Alternation("<snake>");
        a.add(new Literal("cobra"));
        a.add(new Literal("python"));
        return a;
    }

    /**
     * Returns a parser that recognizes some members of the squamata order.
     *
     * @return
     */
    public static Parser squamata() {
        Alternation a = new Alternation("<squamata>");
        a.add(snake());
        a.add(lizard());
        return a;
    }

    private static final Logger LOG
            = Logger.getLogger(ShowPrettyAlternations.class.getName());

}
