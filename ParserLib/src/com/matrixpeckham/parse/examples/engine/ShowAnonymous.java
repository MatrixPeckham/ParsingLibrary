package com.matrixpeckham.parse.examples.engine;

import com.matrixpeckham.parse.engine.Anonymous;
import com.matrixpeckham.parse.engine.Fact;
import com.matrixpeckham.parse.engine.Program;
import com.matrixpeckham.parse.engine.Query;
import com.matrixpeckham.parse.engine.Structure;
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
 * Show how to use an anonymous variable.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowAnonymous {

    /**
     * Show how to use an anonymous variable.
     *
     * @param args
     */
    public static void main(String[] args) {

        // marriage(001, balthasar, grimelda, 14560512, 14880711);
        Fact m1 = new Fact("marriage", new Object[]{
            1,
            "balthasar",
            "grimelda", 14_560_512, 14_880_711});

        // marriage(257, kevin, karla, 19790623, present);
        Fact m257 = new Fact("marriage", new Object[]{
            257,
            "kevin",
            "karla", 19_790_623,
            "present"});

        Program p = new Program();
        p.addAxiom(m1);
        p.addAxiom(m257);

        // marriage(Id, Hub, _, _, _);
        Variable id = new Variable("Id");
        Variable hub = new Variable("Hub");
        Anonymous a = new Anonymous();

        Query q = new Query(p, new Structure(
                "marriage", new Term[]{id, hub, a, a, a}));

        // output
        System.out.println("Program: \n" + p + "\n");
        System.out.println("Query:   \n" + q + "\n");
        System.out.println("Results: \n");

        while (q.canFindNextProof()) {
            System.out.println(
                    "Id: " + id + ", Husband: " + hub);
        }
    }

    private static final Logger LOG
            = Logger.getLogger(ShowAnonymous.class.getName());

}
