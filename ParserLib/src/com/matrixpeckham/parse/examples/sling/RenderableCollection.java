package com.matrixpeckham.parse.examples.sling;

import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 */
/**
 * This class holds a collection of renderable Sling functions.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class RenderableCollection {

    /**
     *
     */
    protected ArrayList<Renderable> renderables = new ArrayList<>();

    /**
     * Adds a renderable function to the collection.
     *
     * @param r
     */
    public void add(Renderable r) {
        renderables.add(r);
    }

    /**
     * Returns an enumeration of the renderable functions in this collection.
     *
     * @return
     */
    public Iterator<Renderable> iterator() {
        return renderables.iterator();
    }

    /**
     * Find the extreme points the functions will reach.
     *
     * @return the extreme points the functions will reach
     */
    public Extrema getExtrema() {
        if (isEmpty()) {
            return new Extrema(new Point(-1, -1), new Point(1, 1));
        }
        Point min = new Point(0, 0), max = new Point(0, 0);
        Iterator<Renderable> e = iterator();
        boolean first = true;
        while (e.hasNext()) {
            Renderable r = e.next();
            Extrema ex = r.getExtrema();
            if (first) {
                first = false;
                min.x = ex.min.x;
                min.y = ex.min.y;
                max.x = ex.max.x;
                max.y = ex.max.y;
            } else {
                min.x = min(min.x, ex.min.x);
                min.y = min(min.y, ex.min.y);
                max.x = max(max.x, ex.max.x);
                max.y = max(max.y, ex.max.y);
            }
        }
        return new Extrema(min, max);
    }

    /**
     * Returns true if this collection contains no renderables.
     *
     * @return
     */
    public boolean isEmpty() {
        return renderables.isEmpty();
    }

    private static final Logger LOG
            = Logger.getLogger(RenderableCollection.class.getName());

}
