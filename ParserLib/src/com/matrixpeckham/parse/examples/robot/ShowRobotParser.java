package com.matrixpeckham.parse.examples.robot;

import static com.matrixpeckham.parse.examples.robot.RobotParser.start;
import com.matrixpeckham.parse.parse.Assembly;
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
 * Show how to use the <code>RobotParser</code> class.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowRobotParser {

    /**
     * Show how to use the <code>RobotParser</code> class.
     *
     * @param args
     */
    public static void main(String[] args) {
        Parser<Token, NullCloneable, RobotCommand> p = start();

        String[] tests = new String[]{
            "pick carrier from LINE_IN",
            "place carrier at DB101_IN",
            "pick carrier from DB101_OUT",
            "place carrier at WB500_IN",
            "pick carrier from WB500_OUT",
            "place carrier at LINE_OUT",
            "scan DB101_OUT"};

        for (String test : tests) {
            TokenAssembly<NullCloneable, RobotCommand> ta = new TokenAssembly<>(
                    test);
            Assembly<Token, NullCloneable, RobotCommand> out = p.bestMatch(ta);
            System.out.println(out.getTarget());
        }
    }

    private static final Logger LOG
            = Logger.getLogger(ShowRobotParser.class.getName());

}
