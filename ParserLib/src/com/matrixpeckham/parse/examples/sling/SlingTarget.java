package com.matrixpeckham.parse.examples.sling;

//import com.sun.java.swing.*;
import com.matrixpeckham.parse.utensil.PubliclyCloneable;
import java.util.logging.Logger;
import javax.swing.JSlider;

public class SlingTarget implements PubliclyCloneable<SlingTarget> {

    /**
     *
     */
    protected final static int DEFAULT_NLINE = 100;

    /**
     *
     */
    protected Scope scope = new Scope();

    /**
     *
     */
    protected JSlider s1;

    /**
     *
     */
    protected JSlider s2;

    /**
     *
     */
    protected RenderableCollection renderables = new RenderableCollection();

    /**
     * Construct a target, given two sliders.
     *
     * @param s1
     * @param s2
     */
    public SlingTarget(JSlider s1, JSlider s2) {

        this.s1 = s1;
        this.s2 = s2;
    }

    @Override
    public SlingTarget copy() {
        return this;
    }

    /**
     * Return the renderables collection which is the destination for the user's
     * renderable functions.
     *
     * @return the renderables collection which is the destination for the
     * user's renderable functions
     */
    public RenderableCollection getRenderables() {
        return renderables;
    }

    /**
     * Return true, if the given variable name exists in this target's scope.
     *
     * @param s
     * @return
     */
    public boolean isDefined(String s) {
        return scope.isDefined(s);
    }

    /**
     * Return a variable of the given name.
     *
     * @param name the name to look up
     * @return a variable of the given name
     *
     *
     */
    public Variable lookup(String name) {
        return scope.lookup(name);
    }

    /**
     * Return the special variable "nLine", which establishes the number of
     * lines to use in rendering a function.
     *
     * @return the special variable "nLine", which establishes the number of
     * lines to use in rendering a function
     */
    public Variable nLine() {
        Variable v;
        if (isDefined("nLine")) {
            v = lookup("nLine");
        } else {
            v = lookup("nLine");
            v.setValue(new Point(0, DEFAULT_NLINE));
        }
        return v;
    }

    /**
     * Return the first slider.
     *
     * @return the first slider
     */
    public JSlider s1() {
        return s1;
    }

    /**
     * Return the second slider.
     *
     * @return the second slider
     */
    public JSlider s2() {
        return s2;
    }

    private static final Logger LOG
            = Logger.getLogger(SlingTarget.class.getName());

}
