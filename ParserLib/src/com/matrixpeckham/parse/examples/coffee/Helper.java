package com.matrixpeckham.parse.examples.coffee;

import java.util.logging.Logger;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * This class is the top of a hierarchy of classes that help to build a coffee
 * object, based on recognizing elements of an XML markup file.
 * <p>
 * An application that uses a SAX parser can pass control to helpers upon
 * receiving SAX events.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 * @param <Tar>
 */
public class Helper<Tar> {

    /**
     * An application that uses a SAX parser should call this method upon
     * receiving a <code>characters</code> event.
     *
     * @param s
     * @param target
     */
    public void characters(String s, Tar target) {
    }

    /**
     * An application that uses a SAX parser should call this method upon
     * receiving a <code>startElement</code> event.
     *
     * @param target
     */
    public void startElement(Tar target) {
    }

    private static final Logger LOG = Logger.getLogger(Helper.class.getName());

}
