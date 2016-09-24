package com.matrixpeckham.parse.imperative;

import com.matrixpeckham.parse.engine.BooleanTerm;
import java.util.logging.Logger;


public class IfCommand extends Command {

    /**
     *
     */
    protected BooleanTerm condition;

    /**
     *
     */
    protected Command ifCommand;

    /**
     *
     */
    protected Command elseCommand;

    /**
     * Construct an "if" command from the given condition and command.
     *
     * @param condition the condition to check
     *
     * @param ifCommand the command to execute if the condition evaluates to
     * true
     */
    public IfCommand(BooleanTerm condition, Command ifCommand) {
        this.condition = condition;
        this.ifCommand = ifCommand;
        this.elseCommand = new NullCommand();
    }

    /**
     * Construct an "if" command from the given condition and command.
     *
     * @param condition the condition to check
     *
     * @param ifCommand the command to execute if the condition evaluates to
     * true
     *
     * @param elseCommand the command to execute if the condition evaluates to
     * false
     */
    public IfCommand(
            BooleanTerm condition, Command ifCommand,
            Command elseCommand) {

        this.condition = condition;
        this.ifCommand = ifCommand;
        this.elseCommand = elseCommand;
    }

    /**
     * Execute this "if" command. Evaluate the condition. If it is true, execute
     * this object's <code>ifCommand</code>. Otherwise, execute the
     * <code>elseCommand</code>, which may be a <code>NullCommand</code> object.
     */
    @Override
    public void execute() {
        Boolean b = (Boolean) condition.eval();
        if (b) {
            ifCommand.execute();
        } else {
            elseCommand.execute();
        }
    }

    /**
     * Returns a string description of this if command.
     *
     * @return a string description of this if command
     */
    @Override
    public String toString() {
        return "if" + "(" + condition + ")"
                + "{" + ifCommand + "}" + "else" + "{" + elseCommand + "}";
    }

    private static final Logger LOG
            = Logger.getLogger(IfCommand.class.getName());
}
