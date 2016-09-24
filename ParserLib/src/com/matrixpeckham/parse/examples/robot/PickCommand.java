package com.matrixpeckham.parse.examples.robot;

import java.util.logging.Logger;

public class PickCommand extends RobotCommand {

    @Override
    public RobotCommand copy() {
        return new PickCommand();
    }

    /**
     * Return a textual description of this object.
     *
     * @return a textual description of this object
     */
    @Override
    public String toString() {
        return "pick " + location;
    }

    private static final Logger LOG
            = Logger.getLogger(PickCommand.class.getName());

}
