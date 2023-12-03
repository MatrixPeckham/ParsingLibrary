/*
 * DEFAULT LICENSE
 * Do not make illegal copies
 * This software is provided as is, without any warranty at all
 * Not responsible for any damage to anything that may occur
 * Copyright © 2012 William Peckham
 */
package com.matrixpeckham.parse.utensil;

import java.util.logging.Logger;

/**
 * Basic Clonable object for use in some demonstration classes.
 *
 * @author William Matrix Peckham
 */
public class NullCloneable implements PubliclyCloneable<NullCloneable> {

    private static final NullCloneable inst = new NullCloneable();

    private NullCloneable() {
    }

    public static NullCloneable get() {
        return inst;
    }

    @Override
    public NullCloneable copy() {
        return this;
    }

    private static final Logger LOG
            = Logger.getLogger(NullCloneable.class.getName());

}
