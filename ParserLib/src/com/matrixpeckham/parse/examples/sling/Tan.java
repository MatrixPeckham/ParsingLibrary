package com.matrixpeckham.parse.examples.sling;

import static java.lang.Math.tan;
import java.util.logging.Logger;

public class Tan extends SlingFunction {

    /**
     * Constructs <code>tan(t)</code>.
     */
    public Tan() {
        super(new T());
    }

    /**
     * Constructs a function object that wraps a tan function around the given
     * source function.
     *
     * @param source
     */
    public Tan(SlingFunction source) {
        super(source);
    }

    @Override
    public Tan copy() {
        return new Tan(source[0].copy()); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Returns, essentially, <code>tan(source.f(t))</code>.
     *
     * @param t a number that represents how far along a plot is, and thus tells
     * which point to return
     *
     * @return a new point: <code>(t, tan(source.f(t).y))</code>
     *
     * @see com.matrixpeckham.parse.examples.sling.Abs
     * @see com.matrixpeckham.parse.examples.sling.Abs#f(double)
     */
    @Override
    public Point f(double t) {
        return new Point(t, tan(source[0].f(t).y));
    }

    /**
     * Returns a string representation of this function.
     *
     * @return
     */
    @Override
    public String toString() {
        return "tan(" + source[0] + ")";
    }

    private static final Logger LOG = Logger.getLogger(Tan.class.getName());

}
