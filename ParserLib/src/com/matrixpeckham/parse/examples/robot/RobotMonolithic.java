package com.matrixpeckham.parse.examples.robot;

import com.matrixpeckham.parse.parse.Alternation;
import com.matrixpeckham.parse.parse.Sequence;
import com.matrixpeckham.parse.parse.tokens.CaselessLiteral;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.TokenAssembly;
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
 * Show how to create a parser and use it in a single method.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class RobotMonolithic {

    /**
     * Show how to create a parser and use it in a single method.
     *
     * @param args
     */
    public static void main(String[] args) {
        Alternation<Token, NullCloneable, RobotCommand> command
                = new Alternation<>();
        Sequence<Token, NullCloneable, RobotCommand> pickCommand
                = new Sequence<>();
        Sequence<Token, NullCloneable, RobotCommand> placeCommand
                = new Sequence<>();
        Sequence<Token, NullCloneable, RobotCommand> scanCommand
                = new Sequence<>();
        Word<NullCloneable, RobotCommand> location = new Word<>();

        command.add(pickCommand);
        command.add(placeCommand);
        command.add(scanCommand);

        pickCommand.add(new CaselessLiteral<>("pick"));
        pickCommand.add(new CaselessLiteral<>("carrier"));
        pickCommand.add(new CaselessLiteral<>("from"));
        pickCommand.add(location);

        placeCommand.add(new CaselessLiteral<>("place"));
        placeCommand.add(new CaselessLiteral<>("carrier"));
        placeCommand.add(new CaselessLiteral<>("at"));
        placeCommand.add(location);

        scanCommand.add(new CaselessLiteral<>("scan"));
        scanCommand.add(location);

        String s = "pick carrier from DB101_IN";

        System.out.println(
                command.bestMatch(new TokenAssembly<>(s)));
    }

    private static final Logger LOG
            = Logger.getLogger(RobotMonolithic.class.getName());

}
