package com.matrixpeckham.parse.examples.robot;

import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * A <code>RobotCommand</code> encapsulates the work that lies behind a high
 * level command like "pick carrier from input1". In this package, the commands
 * are just example targets of a parser; their <code>execute()</code> methods
 * are not implemented.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class RobotCommand
        implements
        com.matrixpeckham.parse.utensil.PubliclyCloneable<RobotCommand> {

    /**
     *
     */
    protected String location;

    /**
     * Return a copy of this object. If the location attribute becomes something
     * more complicated than a String, then this method will become insufficient
     * if location is not immutable.
     *
     * @return a copy of this object
     */
    @Override
    public RobotCommand copy() {
        RobotCommand c = new RobotCommand();
        c.location = location;
        return c;
    }

    /**
     * If we were really driving a factory, this is where we would turn high
     * level commands into the protocols that various machines would understand.
     * For example, a pick command might send messages to both a conveyor and to
     * a track robot.
     */
    public void execute() {
    }

    /**
     * Return the location that this command is for.
     *
     * @return the location that this command is for
     */
    public String getLocation() {
        return location;
    }

    /**
     * Set the location for this command.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    private static final Logger LOG
            = Logger.getLogger(RobotCommand.class.getName());

}
