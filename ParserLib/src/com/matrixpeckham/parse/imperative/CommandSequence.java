package com.matrixpeckham.parse.imperative;

import static java.lang.Thread.yield;
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
 * This class contains a sequence of other commands.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class CommandSequence extends Command {

    /**
     *
     */
    protected ArrayList<Command> commands;

    /**
     * Add a command to the sequence of commands to which this object will
     * cascade an <code>execute</code> command.
     *
     * @param c a command to add to this command sequence
     */
    public void addCommand(Command c) {
        commands().add(c);
    }

    /**
     * Lazy-initialize the <code>commands</code> vector.
     *
     * @return
     */
    protected ArrayList<Command> commands() {
        if (commands == null) {
            commands = new ArrayList<>();
        }
        return commands;
    }

    /**
     * Ask each command in the sequence to <code>execute</code>.
     */
    @Override
    @SuppressWarnings("CallToThreadYield")
    public void execute() {
        Iterator<Command> e = commands().iterator();
        while (e.hasNext() && !Thread.interrupted()) {
            yield();
            (e.next()).execute();
        }
    }

    /**
     * Returns a string description of this command sequence.
     *
     * @return a string description of this command sequence
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        boolean needLine = false;
        Iterator<Command> e = commands().iterator();
        while (e.hasNext()) {
            if (needLine) {
                buf.append("\n");
            }
            buf.append(e.next());
            needLine = true;
        }
        return buf.toString();
    }

    private static final Logger LOG
            = Logger.getLogger(CommandSequence.class.getName());

}
