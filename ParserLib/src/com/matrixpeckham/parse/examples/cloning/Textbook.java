package com.matrixpeckham.parse.examples.cloning;

import com.matrixpeckham.parse.utensil.PubliclyCloneable;
import java.util.logging.Logger;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * This class just supports the <code>ThisClass</code> example of a typical
 * copy.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class Textbook implements PubliclyCloneable<Textbook> {

    /**
     * Return a copy of this object.
     *
     * @return a copy of this object
     */
    @Override
    public Textbook copy() {
        return new Textbook();
    }

    private static final Logger LOG = Logger.getLogger(Textbook.class.getName());

}
