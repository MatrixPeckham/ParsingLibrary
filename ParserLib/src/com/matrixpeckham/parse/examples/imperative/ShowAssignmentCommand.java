package com.matrixpeckham.parse.examples.imperative;

import com.matrixpeckham.parse.engine.*;
import com.matrixpeckham.parse.imperative.AssignmentCommand;
import com.matrixpeckham.parse.imperative.ForCommand;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * This class provides an example of the assignment command.
 * <p>
 * The <code>main</code> method of this class creates a variable "x" and
 * pre-assigns it the value 0. Then the method creates a "for" command that
 * encapsulates:
 * <p>
 * <blockquote><pre>
 *
 *     for (int i = 1; i &lt;= 4; i++) {
 *         x = x * 10 + 1;
 *     }
 *
 * </pre></blockquote>
 * <p>
 * The method executes the "for" command, leaving x with the value 1111.0.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowAssignmentCommand {

    /**
     * Provide an example of the assignment command.
     *
     * @param args
     */
    public static void main(String[] args) {
        Variable x = new Variable("x");
        x.unify(new NumberFact(0));

        // *(x, 10.0)
        ArithmeticOperator op1 = new ArithmeticOperator('*', x, new NumberFact(
                10));

        // +(*(x, 10.0), 1.0)
        ArithmeticOperator op2 = new ArithmeticOperator('+', op1,
                new NumberFact(1));

        // #(x, +(*(x, 10.0), 1.0))
        AssignmentCommand ac = new AssignmentCommand(new Evaluation(x, op2));

        ForCommand f = new ForCommand(new Variable("i"), 1, 4, ac);

        f.execute();
        System.out.println(x);
    }

    private static final Logger LOG
            = Logger.getLogger(ShowAssignmentCommand.class.getName());

}
