package com.matrixpeckham.parse.imperative;

import java.util.logging.Logger;


/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * This command does nothing, which can simplify coding in some cases. For
 * example, an "if" command with no given "else" uses a <code>NullCommand</code>
 * for its else command.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class NullCommand extends Command {

    /**
     * Does nothing.
     */
    @Override
    public void execute() {
    }

    /**
     * Returns a string description of this null command.
     *
     * @return a string description of this null command
     */
    @Override
    public String toString() {
        return "NullCommand";
    }

    private static final Logger LOG
            = Logger.getLogger(NullCommand.class.getName());
}
