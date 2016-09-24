package com.matrixpeckham.parse.examples.sling;

import com.matrixpeckham.parse.utensil.PubliclyCloneable;
import static java.lang.Math.max;
import static java.lang.Math.min;


/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * <code>SlingFunction</code> is an abstract class that requires subclasses to
 * implement the method <code>Point
 * f(double t)</code>. Subclasses typically accept other functions in their
 * constructors, and wrap some base function around these subfunctions. The
 * <code>SlingFunction</code> class stores the subfunctions in a
 * <code>SlingFunction</code> array it calls <code>source</code>. The function
 * that a subclass supplies should correspond to the name of the class, such as
 * <code>Sin</code>.
 * <p>
 * This class provides two methods that subclasses typically will not override:
 * <code>fresh</code> and <code>eval</code>. The <code>fresh</code> method
 * creates a special type of copy. The <code>eval</code> method creates a new
 * version of an object, evaluating any variables in the object's subfunctions
 * to non-variable values.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public abstract class SlingFunction implements PubliclyCloneable<SlingFunction> {

    /**
     *
     */
    protected SlingFunction[] source;

    /**
     * Construct a function that wraps nothing.
     */
    public SlingFunction() {
        this(new SlingFunction[0]);
    }

    /**
     * Construct a function that wraps the provided functions.
     *
     * @param source
     */
    public SlingFunction(SlingFunction[] source) {
        this.source = source;
    }

    /**
     * Construct a function that wraps the provided function.
     *
     * @param source
     */
    public SlingFunction(SlingFunction source) {
        this(new SlingFunction[]{source});
    }

    /**
     * Construct a function that wraps the provided functions.
     *
     * @param source0
     * @param source1
     */
    public SlingFunction(
            SlingFunction source0, SlingFunction source1) {

        this(new SlingFunction[]{source0, source1});
    }

    /**
     * Creates a new version of an object, evaluating any variables in the
     * object's subfunctions to non-variable values.
     * <p>
     * For example, an object of subclass <code>Arithmetic
     * </code> might contain <code>r*cos(theta)</code>. If <code>r</code> has a
     * value of 2 and <code>theta</code> has a value of, say,
     * <code>2*pi*t</code>, then the object will evaluate to
     * <code>2*cos(2*pi*t)</code>.
     *
     * @return
     */
    public SlingFunction eval() {
        SlingFunction f = fresh();
        for (int i = 0; i < source.length; i++) {
            f.source[i] = source[i].eval();
        }
        return f;
    }

    /**
     * Return the extreme values of this function will reach when rendered with
     * the given number of points.
     *
     * @param nPoint the number of points to consider in rendering the function
     * @return the extreme values of this function will reach when rendered with
     * the given number of points
     */
    public Extrema extrema(int nPoint) {
        Point min = new Point(0, 0), max = new Point(0, 0);
        for (int i = 0; i < nPoint; i++) {
            double t = ((double) i) / (nPoint - 1);
            Point p = this.f(t);
            if (i == 0) {
                min.x = p.x;
                min.y = p.y;
                max.x = p.x;
                max.y = p.y;
            } else {
                min.x = min(min.x, p.x);
                min.y = min(min.y, p.y);
                max.x = max(max.x, p.x);
                max.y = max(max.y, p.y);
            }
        }
        return new Extrema(min, max);
    }

    /**
     * This is the function that all subclasses implement, so that each function
     * class provides a two-dimensional function of time. Time, by definition,
     * goes from 0 to 1 as a plot unfolds.
     *
     * @param t a number that represents how far along a plot is, and thus tells
     * which point to return
     *
     * @return the value of the function at the given time
     */
    public abstract Point f(double t);

    @Override
    public abstract SlingFunction copy();

    /**
     * Creates a copy of the object and initializes the <code>
     * source</code> array to an array of the right length, but leaves the
     * elements of the array empty.
     *
     * @return
     */
    public SlingFunction fresh() {
        return copy();
    }
}
