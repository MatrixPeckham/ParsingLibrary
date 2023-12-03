package com.matrixpeckham.parse.examples.chips;

import java.util.logging.Logger;

/**
 * A customer has an ID, and a last and first name.
 */
public class Customer {

    /**
     *
     */
    protected Integer customerID;

    /**
     *
     */
    protected String lastName;

    /**
     *
     */
    protected String firstName;

    /**
     * Create a customer given his or her ID, last name, and first name.
     *
     * @param customerID
     * @param lastName
     * @param firstName
     */
    public Customer(int customerID, String lastName,
            String firstName) {

        this(Integer.valueOf(customerID), lastName, firstName);
    }

    /**
     * Create a customer given his or her ID, last name, and first name.
     *
     * @param customerID
     * @param lastName
     * @param firstName
     */
    public Customer(Integer customerID, String lastName,
            String firstName) {

        this.customerID = customerID;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    /**
     * Return the ID of this customer.
     *
     * @return the ID of this customer
     */
    public Integer getCustomerID() {
        return customerID;
    }

    /**
     * Return the first name of this customer.
     *
     * @return the first name of this customer
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Return the last name of this customer.
     *
     * @return the last name of this customer
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Return a textual description of this customer.
     *
     * @return a textual description of this customer
     */
    @Override
    public String toString() {
        return "customer(" + customerID + ", " + lastName + ", " + firstName
                + ")";
    }

    private static final Logger LOG = Logger.getLogger(Customer.class.getName());

}
