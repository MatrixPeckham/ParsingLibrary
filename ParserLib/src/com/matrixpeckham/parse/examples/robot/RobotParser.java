package com.matrixpeckham.parse.examples.robot;

import com.matrixpeckham.parse.parse.*;
import com.matrixpeckham.parse.parse.tokens.*;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.logging.Logger;

/**
 * This class's start() method provides a parser that
 * will recognize a command for a track robot and build a
 * corresponding command object.
 * <p>
 * The grammar for the language that this class recognizes
 * is:
 * <p>
 * <blockquote><pre>
 *     command      = pickCommand | placeCommand |
 *                    scanCommand;
 *     pickCommand  = "pick" "carrier" "from" location;
 *     placeCommand = "place" "carrier" "at" location;
 *     scanCommand  = "scan" location;
 *     location     = Word;
 * </pre></blockquote>
 */
public class RobotParser {

    /**
     * Returns a parser that will recognize a command for a track robot and
     * build a corresponding command object.
     * <p>
     * (This method returns the same value as <code>start()</code>).
     *
     * @return a parser that will recognize a track robot command
     */

    /* The parser this method returns recognizes the grammar:
     *
     * command = pickCommand | placeCommand | scanCommand;
     */
    public Parser<Token, NullCloneable, RobotCommand> command() {
        Alternation<Token, NullCloneable, RobotCommand> a = new Alternation<>();
        a.add(pickCommand());
        a.add(placeCommand());
        a.add(scanCommand());
        return a;
    }

    /**
     * Returns a parser that will recognize the grammar:
     * <p>
     * location = Word;
     *
     * @return
     */
    protected Parser<Token, NullCloneable, RobotCommand> location() {
        return new Word<>();
    }

    /**
     * Returns a parser that will recognize the grammar:
     * <p>
     * pickCommand = "pick" "carrier" "from" location;
     *
     * @return
     */
    protected Parser<Token, NullCloneable, RobotCommand> pickCommand() {
        Sequence<Token, NullCloneable, RobotCommand> s = new Sequence<>();
        s.add(new CaselessLiteral<>("pick"));
        s.add(new CaselessLiteral<>("carrier"));
        s.add(new CaselessLiteral<>("from"));
        s.add(location());
        s.setAssembler(new PickAssembler());
        return s;
    }

    /**
     * Returns a parser that will recognize the grammar:
     * <p>
     * placeCommand = "place" "carrier" "at" location;
     *
     * @return
     */
    protected Parser<Token, NullCloneable, RobotCommand> placeCommand() {
        Sequence<Token, NullCloneable, RobotCommand> s = new Sequence<>();
        s.add(new CaselessLiteral<>("place"));
        s.add(new CaselessLiteral<>("carrier"));
        s.add(new CaselessLiteral<>("at"));
        s.add(location());
        s.setAssembler(new PlaceAssembler());
        return s;
    }

    /**
     * Returns a parser that will recognize the grammar:
     * <p>
     * scanCommand = "scan" location;
     *
     * @return
     */
    protected Parser<Token, NullCloneable, RobotCommand> scanCommand() {
        Sequence<Token, NullCloneable, RobotCommand> s = new Sequence<>();
        s.add(new CaselessLiteral<>("scan"));
        s.add(location());
        s.setAssembler(new ScanAssembler());
        return s;
    }

    /**
     * Returns a parser that will recognize a command for a track robot and
     * build a corresponding command object.
     *
     * @return a parser that will recognize a track robot command
     */
    public static Parser<Token, NullCloneable, RobotCommand> start() {
        return new RobotParser().command();
    }

    private static final Logger LOG
            = Logger.getLogger(RobotParser.class.getName());

}
