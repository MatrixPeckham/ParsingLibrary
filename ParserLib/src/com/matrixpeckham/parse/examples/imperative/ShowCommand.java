package com.matrixpeckham.parse.examples.imperative;

import com.matrixpeckham.parse.engine.Fact;
import com.matrixpeckham.parse.engine.Variable;
import com.matrixpeckham.parse.imperative.ForCommand;
import com.matrixpeckham.parse.imperative.PrintlnCommand;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * This class shows a simple composition of commands from
 * <code>com.matrixpeckham.parse.imperative</code>.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowCommand {

    /**
     * Show a simple composition of commands from <code>
     * com.matrixpeckham.parse.imperative</code>.
     * @param args
     */
    public static void main(String[] args) {
        Fact go = new Fact("go!");
        PrintlnCommand p = new PrintlnCommand(go);

        Variable i = new Variable("i");
        ForCommand f = new ForCommand(i, 1, 5, p);

        f.execute();
    }

    private static final Logger LOG
            = Logger.getLogger(ShowCommand.class.getName());
}
