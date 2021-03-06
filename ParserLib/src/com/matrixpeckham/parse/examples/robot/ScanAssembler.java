package com.matrixpeckham.parse.examples.robot;

import com.matrixpeckham.parse.parse.Assembler;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Sets an assembly's target to be a ScanCommand and note its location.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ScanAssembler extends Assembler<Token, NullCloneable, RobotCommand> {

    /**
     * Sets an assembly's target to be a <code>ScanCommand</code> object and
     * note its location.
     *
     * @param a the assembly to work on
     */
    @Override
    public void workOn(Assembly<Token, NullCloneable, RobotCommand> a) {
        ScanCommand sc = new ScanCommand();
        Token t = a.popTok();
        sc.setLocation(t.sval());
        a.setTarget(sc);
    }

    private static final Logger LOG
            = Logger.getLogger(ScanAssembler.class.getName());

}
