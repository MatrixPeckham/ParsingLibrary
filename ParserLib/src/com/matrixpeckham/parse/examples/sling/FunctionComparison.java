package com.matrixpeckham.parse.examples.sling;

import com.matrixpeckham.parse.engine.BooleanTerm;
import com.matrixpeckham.parse.engine.Gateway;
import com.matrixpeckham.parse.engine.Term;
import java.util.logging.Logger;

public class FunctionComparison extends Gateway
        implements BooleanTerm {

    /**
     *
     */
    protected String operator;

    /**
     *
     */
    protected SlingFunction f0;

    /**
     *
     */
    protected SlingFunction f1;

    /**
     * Create a comparison with the specified operator and comparison functions.
     *
     * @param operator the comparison operator
     *
     * @param f0 the first function
     * @param f1
     */
    public FunctionComparison(
            String operator, SlingFunction f0, SlingFunction f1) {

        super(operator, new Term[0]);
        this.operator = operator;
        this.f0 = f0;
        this.f1 = f1;
    }

    /**
     * Returns true if the comparison operator holds true between the values of
     * this comparison's terms.
     * <p>
     * This method evaluates the two functions received in the constructor to
     * fix any variable references. Then this method extracts the y components
     * of the functions at time zero, and compares these values.
     *
     * @return <code>true</code> if the comparison operator holds true between
     * the values of this comparison's terms.
     */
    @Override
    public boolean canProveOnce() {
        SlingFunction eval0 = f0.eval();
        SlingFunction eval1 = f1.eval();

        double d0 = eval0.f(0).y;
        double d1 = eval1.f(0).y;

        if (operator.equals(">")) {
            return d0 > d1;
        }
        if (operator.equals("<")) {
            return d0 < d1;
        }
        if (operator.equals("=")) {
            return d0 == d1;
        }
        if (operator.equals(">=")) {
            return d0 >= d1;
        }
        if (operator.equals("<=")) {
            return d0 <= d1;
        }
        if (operator.equals("!=")) {
            return d0 != d1;
        }
        return false;
    }

    /**
     * Returns <code>Boolean.TRUE</code> if the comparison operator holds true
     * between the values of the two terms.
     *
     * @return <code>Boolean.TRUE</code> if the comparison operator holds true
     * between the values of the two terms.
     */
    @Override
    public Object eval() {
        return canProveOnce();
    }

    /**
     * Returns a string representation of this comparison.
     *
     * @return a string representation of this comparison
     */
    @Override
    public String toString() {
        return operator + "(" + f0 + ", " + f1 + ")";
    }

    private static final Logger LOG
            = Logger.getLogger(FunctionComparison.class.getName());

}
