package com.matrixpeckham.parse.examples.sling;

import com.matrixpeckham.parse.imperative.Command;
import java.util.logging.Logger;

/**
 * This command, when executed, evaluates a function and sets
 * it as the value of a variable.
 *
 * @author Steven J. Metsker
 */
public class AssignFunctionCommand extends Command {

    /**
     *
     */
    protected Variable v;

    /**
     *
     */
    protected SlingFunction f;

    /**
     * Construct a command to assign the supplied function to the supplied
     * variable.
     *
     * @param v the variable to assign to
     * @param f the function to evaluate and assign to the variable at execution
     *          time
     */
    public AssignFunctionCommand(Variable v, SlingFunction f) {
        this.v = v;
        this.f = f;
    }

    /**
     * Evaluate the function and assign the resulting, new function as the value
     * of this object's variable.
     */
    @Override
    public void execute() {
        v.setValue(f.eval());
    }

    /**
     * Returns a string description of this command.
     *
     * @return a string description of this command
     */
    @Override
    public String toString() {
        return v + " = " + f;
    }

    private static final Logger LOG
            = Logger.getLogger(AssignFunctionCommand.class.getName());

}
