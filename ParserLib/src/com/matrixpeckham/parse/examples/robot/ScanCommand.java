package com.matrixpeckham.parse.examples.robot;

public class ScanCommand extends RobotCommand {

    @Override
    public RobotCommand copy() {
        return new ScanCommand(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Return a textual description of this object.
     *
     * @return a textual description of this object
     */
    @Override
    public String toString() {
        return "scan " + location;
    }
}
