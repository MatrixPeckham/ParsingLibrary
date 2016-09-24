package com.matrixpeckham.parse.examples.string;

import java.util.logging.Logger;


public class Substring extends StringFunction {

    /**
     *
     */
    protected int from;

    /**
     *
     */
    protected int to;

    /**
     *
     */
    protected boolean rest = false;

    /**
     * Construct a <code>Substring</code> function that will wrap itself around
     * the supplied source, returning the portion of a string from the given
     * index to the string's end.
     * @param from
     */
    public Substring(StringFunction source, int from) {
        this(source, from, 0);
        this.rest = true;
    }

    /**
     * Construct a <code>Substring</code> function that will wrap itself around
     * the supplied source, returning the portion of a string from the given
     * <code>from</code> index to the given <code>to</code> index.
     * @param to
     */
    public Substring(StringFunction source, int from, int to) {
        super(source);
        this.from = from;
        this.to = to;
    }

    /**
     * Return a substring of the value of <code>source.f(s)
     * </code>, where <code>s</code> is the supplied string, and
     * <code>source</code> is this function's source function.
     * @return 
     */
    @Override
    public String f(String s) {
        if (rest) {
            return source.f(s).substring(from);
        } else {
            return source.f(s).substring(from, to);
        }
    }

    private static final Logger LOG
            = Logger.getLogger(Substring.class.getName());
}
