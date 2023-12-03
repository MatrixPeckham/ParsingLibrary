package com.matrixpeckham.parse.examples.sling;

import java.util.logging.Logger;

/**
 * A variable is a named place that can store another
 * function. The <code>Variable</code> class is a subclass of
 * <code>SlingFunction</code>, which allows other functions to
 * compose themselves from other functions. For example, if
 * <code>r</code> is a variable, then <code>sling(r, 1)</code>
 * is a valid function. This function can be plotted after it
 * is evaluated for some value of <code>r</code>.
 * The following code creates a function that contains the
 * variable <code>r</code>. The code sets a value for r,
 * evaluates the function, and shows the new function's value
 * for a few different times:
 * <p>
 * <blockquote><pre>
 *     // s = sling(r, 1);
 *     Variable r = new Variable("r");
 *     SlingFunction s = new Sling(r, new Point(0, 1));
 *     System.out.println("s: " + s);
 *
 *     // set r to 10 and evaluate s into a new function s10
 *     r.setValue(new Point(0, 10));
 *     SlingFunction s10 = s.eval();
 *     System.out.println("s10: " + s10);
 *
 *     // show s10 a few times
 *     for (double time = 0; time &lt; 0.25; time += .05) {
 *         System.out.println(s10.f(time));
 *     }
 * </pre></blockquote>
 * <p>
 * This code prints out:
 * </p>
 * <blockquote><pre>
 *     s: sling(r, (0.0, 1.0))
 *     s10: sling((0.0, 10.0), (0.0, 1.0))
 *     (10.0, 0.0)
 *     (9.510565162951535, 3.090169943749474)
 *     (8.090169943749475, 5.877852522924732)
 *     (5.87785252292473, 8.090169943749475)
 *     (3.0901699437494745, 9.510565162951535)
 * </pre></blockquote>
 * <p>
 * The printout shows: a function that contains a variable; an
 * evaluated function; the evaluated function's value for a few
 * times in the first quarter of a plot of the function.
 */
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
     *
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
