package com.matrixpeckham.parse.examples.cloning;

import java.util.logging.Logger;

/**
 *
 * This class has a properly functioning, public clone()
 * method.
 */
public class Customer implements Cloneable {

    /**
     *
     */
    protected String name;

    /**
     *
     */
    protected int IQ;

    /**
     * Construct a customer.
     *
     * @param name
     * @param IQ
     */
    public Customer(String name, int IQ) {
        this.name = name;
        this.IQ = IQ;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError();
        }
    }

    /**
     * Check this customer's IQ.
     *
     * @return int
     */
    public int getIQ() {
        return IQ;
    }

    /**
     * Set this customer's IQ.
     *
     * @param IQ int
     */
    public void setIQ(int IQ) {
        this.IQ = IQ;
    }

    private static final Logger LOG = Logger.getLogger(Customer.class.getName());

}
