package com.matrixpeckham.parse.examples.pretty;

import com.matrixpeckham.parse.parse.Repetition;
import com.matrixpeckham.parse.parse.Sequence;
import com.matrixpeckham.parse.parse.tokens.TokenAssembly;
import com.matrixpeckham.parse.parse.tokens.Word;
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
 * Show that the pretty printer will find all the parses that result from
 * applying the parser <code>Word* Word*</code> against a string with four
 * words.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ShowPrettyRepetitions {

    /**
     * Show that the pretty printer will find all the parses that result from
     * applying the parser <code>Word* Word*</code> against a string with four
     * words.
     *
     * @param args
     */
    public static void main(String[] args) {
        PrettyParser p = new PrettyParser(seq());
        p.setShowLabels(true);
        TokenAssembly ta = new TokenAssembly(
                "belfast cork dublin limerick");
        Iterator e = p.parseTrees(ta).iterator();
        while (e.hasNext()) {
            System.out.println("The input parses as:");
            System.out.println("---------------------------");
            System.out.println(e.next());
        }
    }

    /**
     * The parser to try:
     *
     * <blockquote><pre>
     *     seq  = rep1 rep2;
     *     rep1 = Word*;
     *     rep2 = Word*;
     * </pre></blockquote>
     *
     * @return
     */
    public static Sequence seq() {
        Sequence seq = new Sequence("<seq>");
        seq.add(new Repetition(new Word(), "<rep1>"));
        seq.add(new Repetition(new Word(), "<rep2>"));
        return seq;
    }

    private static final Logger LOG
            = Logger.getLogger(ShowPrettyRepetitions.class.getName());

}
