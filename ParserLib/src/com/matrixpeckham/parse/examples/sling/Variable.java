package com.matrixpeckham.parse.examples.sling;

import java.util.logging.Logger;

public class Variable extends SlingFunction {

    /**
     *
     */
    protected String name;

    /**
     * Create a new variable with the given name.
     *
     * @param name
     */
    public Variable(String name) {
        this.name = name;
        // a place to store a term
        this.source = new SlingFunction[1];
    }

    @Override
    public SlingFunction copy() {
        return new Variable(name);
    }

    /**
     * Return the value of the contents of this variable.
     *
     * @return
     * @exception UnassignedVariableException if the variable has no value
     */
    @Override
    public SlingFunction eval() {
        if (source[0] == null) {
            throw new UnassignedVariableException(
                    "> Program uses " + name + " before assigning "
                    + "it a value.");
        }
        return source[0].eval();
    }

    /**
     * Throws an <code>InternalError</code>. Variables themselves cannot be
     * plotted. A function that contains a variable must be evaluated before it
     * is plotted.
     *
     * @return
     */
    @Override
    public Point f(double t) {
        throw new InternalError();
    }

    /**
     * Sets the value of the variable to the given function.
     *
     * @param f
     */
    public void setValue(SlingFunction f) {
        source[0] = f;
    }

    /**
     * Returns a string representation of this variable.
     *
     * @return
     */
    @Override
    public String toString() {
        if (source[0] == null) {
            return name;
        } else {
            return name + " = " + source[0].toString();
        }
    }

    private static final Logger LOG = Logger.getLogger(Variable.class.getName());

}
