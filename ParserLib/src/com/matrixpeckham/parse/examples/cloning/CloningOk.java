package com.matrixpeckham.parse.examples.cloning;

import java.util.logging.Logger;

public class CloningOk implements Cloneable {

    /**
     * Just a demo, this will compile and run fine.
     *
     * @param args
     */
    public static void main(String args[]) {

        CloningOk co = new CloningOk();
        try {
            CloningOk co2 = (CloningOk) co.clone();
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError();
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    private static final Logger LOG
            = Logger.getLogger(CloningOk.class.getName());

}
