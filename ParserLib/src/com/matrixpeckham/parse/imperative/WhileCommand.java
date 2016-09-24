package com.matrixpeckham.parse.imperative;

import com.matrixpeckham.parse.engine.BooleanTerm;
import java.util.logging.Logger;

public class WhileCommand extends Command {

    /**
     *
     */
    protected BooleanTerm condition;

    /**
     *
     */
    protected Command command;

    /**
     * Construct a "while" command from the given condition and command.
     *
     * @param condition the condition to check each time before executing the
     * body
     *
     * @param command the command to repeatedly execute
     */
    public WhileCommand(BooleanTerm condition, Command command) {
        this.condition = condition;
        this.command = command;
    }

    /**
     * Execute this "while" command. This means repeatedly checking the
     * condition, and executing command from this object's command, so long as
     * the condition is true.
     */
    @Override
    @SuppressWarnings("CallToThreadYield")
    public void execute() {
        while (((Boolean) condition.eval()) && !Thread.interrupted()) {
            Thread.yield();
            command.execute();
        }
    }

    /**
     * Returns a string description of this while command.
     *
     * @return a string description of this while command
     */
    @Override
    public String toString() {
        return "while" + "(" + condition + ")" + "{" + command + "}";
    }

    private static final Logger LOG
            = Logger.getLogger(WhileCommand.class.getName());

}
