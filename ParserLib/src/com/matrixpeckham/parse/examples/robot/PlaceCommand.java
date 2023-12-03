package com.matrixpeckham.parse.examples.robot;

import java.util.logging.Logger;

/**
 * Just for demonstration.
 */
public class PlaceCommand extends RobotCommand {

    @Override
    public RobotCommand copy() {
        return new PlaceCommand();
    }

    /**
     * Return a textual description of this object.
     *
     * @return a textual description of this object
     */
    @Override
    public String toString() {
        return "place " + location;
    }

    private static final Logger LOG
            = Logger.getLogger(PlaceCommand.class.getName());

}
