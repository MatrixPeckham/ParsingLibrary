package com.matrixpeckham.parse.examples.sling;

import com.matrixpeckham.parse.imperative.Command;
import java.util.logging.Logger;

/**
 * This command, when executed, evaluates a renderable
 * function and adds it to a renderable collection.
 */
public class AddFunctionCommand extends Command {

    /**
     *
     */
    protected RenderableCollection renderables;

    /**
     *
     */
    protected SlingFunction f;

    /**
     *
     */
    protected Variable nLine;

    /**
     * Construct a command to add the supplied function to the supplied function
     * collection.
     *
     * @param renderables the collection
     *
     * @param f           the function to evaluate and add at execution time
     *
     * @param nLine       a varialbe representing the number of lines to plot
     *                    when
     *                    rendering the function
     */
    public AddFunctionCommand(
            RenderableCollection renderables,
            SlingFunction f,
            Variable nLine) {

        this.renderables = renderables;
        this.f = f;
        this.nLine = nLine;
    }

    /**
     * Evaluate the function and add it to the collection.
     */
    @Override
    public void execute() {
        renderables.add(new Renderable(f.eval(), nLine.eval()));
    }

    /**
     * Returns a string description of this command.
     *
     * @return a string description of this command
     */
    @Override
    public String toString() {
        return "add(" + f + ", " + renderables + ")";
    }

    private static final Logger LOG
            = Logger.getLogger(AddFunctionCommand.class.getName());

}
