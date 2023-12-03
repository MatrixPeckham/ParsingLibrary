package com.matrixpeckham.parse.examples.chips;

import java.util.logging.Logger;

/**
 * An order is a standing request from a customer for a
 * monthly supply of a number of bags of a type of chip.
 * <p>
 */
public class Order {

    /**
     *
     */
    protected Customer customer;

    /**
     *
     */
    protected Chip chip;

    /**
     *
     */
    protected Integer bagsPerMonth;

    /**
     * Create an order given a customer, a chip, and a number of bags to ship
     * per month.
     *
     * @param customer
     * @param chip
     * @param bagsPerMonth
     */
    public Order(Customer customer, Chip chip, int bagsPerMonth) {
        this(customer, chip, Integer.valueOf(bagsPerMonth));
    }

    /**
     * Create an order given a customer, a chip, and a number of bags to ship
     * per month.
     *
     * @param customer
     * @param chip
     * @param bagsPerMonth
     */
    public Order(Customer customer, Chip chip, Integer bagsPerMonth) {

        this.customer = customer;
        this.chip = chip;
        this.bagsPerMonth = bagsPerMonth;
    }

    /**
     * Return the number of bags per month to ship.
     *
     * @return the number of bags per month to ship
     */
    public Integer getBagsPerMonth() {
        return bagsPerMonth;
    }

    /**
     * Return this order's chip type.
     *
     * @return this order's chip type
     */
    public Chip getChip() {
        return chip;
    }

    /**
     * Return this order's customer.
     *
     * @return this order's customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Return a textual description of this order.
     *
     * @return a textual description of this order
     */
    @Override
    public String toString() {
        return "order(" + customer.getCustomerID() + ", " + chip.getChipID()
                + ", " + bagsPerMonth + ")";
    }

    private static final Logger LOG = Logger.getLogger(Order.class.getName());

}
