package com.matrixpeckham.parse.examples.query;

import static com.matrixpeckham.parse.examples.query.ChipSource.program;
import java.util.logging.Logger;


/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * This class shows the chip facts that <code>ChipSource</code> makes available.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowChipSource {

    /**
     * Show the chip facts that <code>ChipSource</code> makes available.
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(program());
    }

    private static final Logger LOG
            = Logger.getLogger(ShowChipSource.class.getName());

}
