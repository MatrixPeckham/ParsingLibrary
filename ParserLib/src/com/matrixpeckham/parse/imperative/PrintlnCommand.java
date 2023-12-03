package com.matrixpeckham.parse.imperative;

import com.matrixpeckham.parse.engine.Term;
import java.io.PrintWriter;
import java.util.logging.Logger;

/**
 * This command, when executed, prints out the
 * value of a term provided in the constructor.
 */
public class PrintlnCommand extends Command {

    /**
     *
     */
    protected Term term;

    /**
     *
     */
    protected PrintWriter out;

    /**
     * Construct a "print" command to print the supplied term.
     *
     * @param term the term to print
     */
    public PrintlnCommand(Term term) {
        this(term, new PrintWriter(System.out));
    }

    /**
     * Construct a "print" command to print the supplied term, printing to the
     * supplied <code>PrintWriter</code> object.
     *
     * @param term the term to print
     * @param out  where to print
     */
    public PrintlnCommand(Term term, PrintWriter out) {
        this.term = term;
        this.out = out;
    }

    /**
     * Print the value of this object's term onto the output writer.
     */
    @Override
    public void execute() {
        System.out.print(term.eval() + "\n");
        out.flush();
    }

    /**
     * Returns a string description of this print command.
     *
     * @return a string description of this print command
     */
    @Override
    public String toString() {
        return "println(" + term + ")";
    }

    private static final Logger LOG
            = Logger.getLogger(PrintlnCommand.class.getName());

}
