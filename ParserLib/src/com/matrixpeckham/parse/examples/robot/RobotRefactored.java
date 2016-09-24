package com.matrixpeckham.parse.examples.robot;

import com.matrixpeckham.parse.parse.Alternation;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Sequence;
import com.matrixpeckham.parse.parse.tokens.CaselessLiteral;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.Word;
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
 * Provide an example of a class that affords a parser for the "robot" command
 * language. This class is a refactored version of the
 * <code>RobotMonolithic</code> class, with one method for each subparser in the
 * robot language.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class RobotRefactored {

    /**
     *
     * @return
     */
    public Parser<Token, NullCloneable, RobotCommand> command() {
        Alternation<Token, NullCloneable, RobotCommand> a = new Alternation<>();
        a.add(pickCommand());
        a.add(placeCommand());
        a.add(scanCommand());
        return a;
    }

    /**
     *
     * @return
     */
    protected Parser<Token, NullCloneable, RobotCommand> location() {
        return new Word<>();
    }

    /**
     *
     * @return
     */
    protected Parser<Token, NullCloneable, RobotCommand> pickCommand() {
        Sequence<Token, NullCloneable, RobotCommand> s = new Sequence<>();
        s.add(new CaselessLiteral<>("pick"));
        s.add(new CaselessLiteral<>("carrier"));
        s.add(new CaselessLiteral<>("from"));
        s.add(location());
        return s;
    }

    /**
     *
     * @return
     */
    protected Parser<Token, NullCloneable, RobotCommand> placeCommand() {
        Sequence<Token, NullCloneable, RobotCommand> s = new Sequence<>();
        s.add(new CaselessLiteral<>("place"));
        s.add(new CaselessLiteral<>("carrier"));
        s.add(new CaselessLiteral<>("at"));
        s.add(location());
        return s;
    }

    /**
     *
     * @return
     */
    protected Parser<Token, NullCloneable, RobotCommand> scanCommand() {
        Sequence<Token, NullCloneable, RobotCommand> s = new Sequence<>();
        s.add(new CaselessLiteral<>("scan"));
        s.add(location());
        return s;
    }

    private static final Logger LOG
            = Logger.getLogger(RobotRefactored.class.getName());

}
