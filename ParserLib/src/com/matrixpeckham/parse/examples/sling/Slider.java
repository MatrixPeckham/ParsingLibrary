package com.matrixpeckham.parse.examples.sling;

//import com.sun.java.swing.*;
import java.util.logging.Logger;
import javax.swing.JSlider;

public class Slider extends SlingFunction {

    /**
     *
     */
    protected JSlider slider;

    /**
     * Constructs a function that wraps the given slider.
     *
     * @param slider
     */
    public Slider(JSlider slider) {
        this.slider = slider;
    }

    @Override
    public Slider copy() {
        return new Slider(slider); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Returns, essentially, a slider's value, which should be a number between
     * 0 and 1.
     *
     * @param t a number that represents how far along a plot is, and thus tells
     * which point to return
     *
     * @return a new point: <code>(t, slider value)</code>
     *
     * @see com.matrixpeckham.parse.examples.sling.Abs
     * @see com.matrixpeckham.parse.examples.sling.Abs#f(double)
     */
    @Override
    public Point f(double t) {
        return new Point(t, slider.getValue() / 100.0);
    }

    /**
     * Return a string representation of this slider.
     *
     * @return
     */
    @Override
    public String toString() {
        return "slider";
    }

    private static final Logger LOG = Logger.getLogger(Slider.class.getName());

}
