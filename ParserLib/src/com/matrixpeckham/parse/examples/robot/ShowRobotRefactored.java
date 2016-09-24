package com.matrixpeckham.parse.examples.robot;

import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.TokenAssembly;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.logging.Logger;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Show how to use a parser class that arranges its subparsers as methods.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowRobotRefactored {

    /**
     * Show how to use a parser class that arranges its subparsers as methods.
     *
     * @param args
     */
    public static void main(String[] args) {
        Parser<Token, NullCloneable, RobotCommand> p = new RobotRefactored().
                command();
        String s = "place carrier at WB500_IN";
        System.out.println(p.bestMatch(
                new TokenAssembly<>(s)));
    }

    private static final Logger LOG
            = Logger.getLogger(ShowRobotRefactored.class.getName());

}
