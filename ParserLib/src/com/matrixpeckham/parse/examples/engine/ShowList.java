package com.matrixpeckham.parse.examples.engine;

import com.matrixpeckham.parse.engine.Structure;
import static com.matrixpeckham.parse.engine.Structure.list;
import com.matrixpeckham.parse.engine.Term;
import com.matrixpeckham.parse.engine.Variable;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose, 
 * including the implied warranty of merchantability.
 */
/**
 * Show some lists.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowList {

    /**
     * Show some lists.
     *
     * @param args
     */
    public static void main(String[] args) {

        Structure snakes = list(
                new Object[]{"cobra", "garter", "python"});
        System.out.println("A list of three snakes: " + snakes);

        // unify this list with a list of three variables
        Variable a = new Variable("A");
        Variable b = new Variable("B");
        Variable c = new Variable("C");

        Structure abc = list(new Term[]{a, b, c});
        System.out.println(
                "\n... unifies with: " + abc);

        abc.unify(snakes);
        System.out.println(
                "\n... giving: \n" + "A: " + a + "\n" + "B: " + b + "\n" + "C: "
                + c + "\n");

        // unify this list with a list of three variables;
        // note the change in the List constructor
        Variable head = new Variable("Head");
        Variable tail = new Variable("Tail");
        Structure ht = list(new Term[]{head}, tail);
        System.out.println(
                "\n... and unifies with: " + ht);

        ht.unify(snakes);
        System.out.println(
                "\n... giving: \n" + "Head: " + head + "\n" + "Tail: " + tail
                + "\n");
    }

    private static final Logger LOG = Logger.getLogger(ShowList.class.getName());

}
