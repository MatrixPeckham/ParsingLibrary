package com.matrixpeckham.parse.imperative;

import com.matrixpeckham.parse.engine.ArithmeticOperator;
import com.matrixpeckham.parse.engine.BooleanTerm;
import com.matrixpeckham.parse.engine.Comparison;
import com.matrixpeckham.parse.engine.Evaluation;
import com.matrixpeckham.parse.engine.NumberFact;
import com.matrixpeckham.parse.engine.Variable;
import java.util.logging.Logger;

public class ForCommand extends Command {

    /**
     *
     */
    protected Command setupCommand;

    /**
     *
     */
    protected BooleanTerm condition;

    /**
     *
     */
    protected Command endCommand;

    /**
     *
     */
    protected Command bodyCommand;

    /**
     * Construct a "for" command that iterates the supplied variable over the
     * doubles from the "from" parameter to the "to" parameter, stepping by the
     * "step" parameter. This command will iterate, adding step to the initial
     * value, ceasing the loop when the value is greater than the "to" limit.
     *
     * @param v the variable to iterate
     *
     * @param from the initial value for the variable
     *
     * @param to a value not to exceed in the loop
     *
     * @param step the amount to increment by
     *
     * @param bodyCommand the command to repeatedly execute
     */
    public ForCommand(
            Variable v, double from, double to, double step,
            Command bodyCommand) {

        Evaluation setupEv = new Evaluation(v, new NumberFact(from));
        this.setupCommand = new AssignmentCommand(setupEv);

        this.condition = new Comparison("<=", v, new NumberFact(to));

        ArithmeticOperator ao = new ArithmeticOperator('+', v, new NumberFact(
                step));
        Evaluation endEv = new Evaluation(v, ao);
        this.endCommand = new AssignmentCommand(endEv);

        this.bodyCommand = bodyCommand;
    }

    /**
     * Construct a "for" command that iterates the supplied variable over the
     * integers from the "from" parameter to the "to" parameter. For example,
     * the following code segment prints out <code>"my\n"</code> five times.
     *
     * <blockquote><pre>
     *
     *     Variable i = new Variable("i");
     *     Fact f = new Fact("my");
     *     PrintCommand p = new PrintCommand(f); *
     *     new ForCommand(i, 1, 5, p).execute();
     *
     * </pre></blockquote>
     *
     * @param v the variable to iterate
     *
     * @param from the initial value for the variable
     *
     * @param to the final value for the variable
     * @param bodyCommand the command to repeatedly execute
     */
    public ForCommand(
            Variable v, int from, int to, Command bodyCommand) {

        this(v, from, to, 1, bodyCommand);
    }

    /**
     * Construct a "for" command from the given setup command, condition,
     * endCommand, and bodyCommand.
     *
     * @param setupCommand the command to execute before the "for" loop begins
     *
     * @param condition the condition to check before executing the body
     *
     * @param endCommand the condition to check after executing the body
     *
     * @param bodyCommand the command to repeatedly execute
     */
    public ForCommand(
            Command setupCommand, BooleanTerm condition,
            Command endCommand, Command bodyCommand) {

        this.setupCommand = setupCommand;
        this.condition = condition;
        this.endCommand = endCommand;
        this.bodyCommand = bodyCommand;
    }

    /**
     * Execute this "for" command. This means executing the setup command and
     * then looping. The loop checks that the condition it true, executes the
     * body command, and executes the end command.
     */
    @Override
    public void execute() {
        for (setupCommand.execute();
                ((Boolean) condition.eval());
                endCommand.execute()) {
            if (Thread.interrupted()) {
                break;
            }
            bodyCommand.execute();
        }
    }

    /**
     * Returns a string description of this for command.
     *
     * @return a string description of this for command
     */
    @Override
    public String toString() {
        return "for, setUpCommand: " + setupCommand + "\n"
                + "     condition:    " + condition + "\n"
                + "     endCommand:   " + endCommand + "\n"
                + "     bodyCommand:  " + bodyCommand;
    }

    private static final Logger LOG
            = Logger.getLogger(ForCommand.class.getName());

}
