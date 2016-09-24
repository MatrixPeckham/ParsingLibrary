package com.matrixpeckham.parse.examples.pretty;

import static com.matrixpeckham.parse.examples.tests.Dangle.statement;
import com.matrixpeckham.parse.parse.tokens.TokenAssembly;
import java.util.ArrayList;
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
 * Show that the <code>Dangle.statement()</code> parser is ambiguous.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ShowDangle {

    /**
     * Show that the <code>Dangle.statement()</code> parser is ambiguous.
     *
     * @param args
     */
    public static void main(String[] args) {
        String s;
        s = "if (overdueDays > 90)    \n";
        s += "    if (balance >= 1000) \n";
        s += "        callCustomer();  \n";
        s += "else sendBill();";

        TokenAssembly ta = new TokenAssembly(s);

        PrettyParser p = new PrettyParser(statement());

        ArrayList out = p.parseTrees(ta);
        Iterator e = out.iterator();
        while (e.hasNext()) {
            System.out.println("The input parses as:");
            System.out.println("---------------------------");
            System.out.println(e.next());
        }
    }

    private static final Logger LOG
            = Logger.getLogger(ShowDangle.class.getName());

}
