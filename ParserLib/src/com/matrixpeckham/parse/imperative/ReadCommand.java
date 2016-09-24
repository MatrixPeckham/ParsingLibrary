package com.matrixpeckham.parse.imperative;

import com.matrixpeckham.parse.engine.Atom;
import com.matrixpeckham.parse.engine.Evaluation;
import com.matrixpeckham.parse.engine.Variable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.System.in;
import java.util.logging.Logger;

public class ReadCommand extends Command {

    /**
     *
     */
    protected Variable variable;

    /**
     *
     */
    protected BufferedReader reader;

    /**
     * Construct a "read" command to read a value, assigning it to the supplied
     * variable.
     *
     * This constructor sets the command to read from <code>
     * System.in</code>.
     *
     * @param variable the variable to assign to
     */
    public ReadCommand(Variable variable) {
        this(variable, in);

    }

    /**
     * Construct a "read" command to read a value from the supplied reader,
     * assigning the value to the supplied variable.
     *
     * @param variable the variable to assign to
     * @param reader where to read from
     */
    public ReadCommand(Variable variable, BufferedReader reader) {
        this.variable = variable;
        this.reader = reader;
    }

    /**
     * Construct a "read" command to read a value from the supplied input
     * stream, assigning the value to the supplied variable.
     *
     * @param variable the variable to assign to
     * @param in where to read from
     *
     */
    public ReadCommand(Variable variable, InputStream in) {
        this(
                variable,
                new BufferedReader(new InputStreamReader(in)));
    }

    /**
     * Read in a string from this object's input reader, and assign the string
     * to this object's variable.
     */
    @Override
    public void execute() {
        String s;
        try {
            s = reader.readLine();
        } catch (IOException e) {
            s = "";
        }
        Evaluation e = new Evaluation(variable, new Atom(s));
        AssignmentCommand ac = new AssignmentCommand(e);
        ac.execute();
    }

    /**
     * Returns a string description of this read command.
     *
     * @return a string description of this read command
     */
    @Override
    public String toString() {
        return "read(" + variable.name + ")";
    }

    private static final Logger LOG
            = Logger.getLogger(ReadCommand.class.getName());

}
