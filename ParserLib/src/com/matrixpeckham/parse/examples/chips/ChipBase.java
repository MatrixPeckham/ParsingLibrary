package com.matrixpeckham.parse.examples.chips;

import java.util.*;
import java.util.logging.Logger;

/**
 * This class contains a small database of chips, customers,
 * and orders.
 */
public class ChipBase {

    /**
     *
     */
    static Map<Integer, Chip> chip = null;

    /**
     *
     */
    static Map<Integer, Customer> customer;

    /**
     *
     */
    static ArrayList<Order> order = null;

    /**
     * Adds a chip to the database.
     *
     * @param c the chip to add
     */
    public static void add(Chip c) {
        chip.put(c.getChipID(), c);
    }

    /**
     * Adds a customer to the database.
     *
     * @param c the customer to add
     */
    public static void add(Customer c) {
        customer.put(c.getCustomerID(), c);
    }

    /**
     * Adds an order to the database.
     *
     * @param o the order to add
     */
    public static void add(Order o) {
        order.add(o);
    }

    /**
     * Returns a Map of chip types.
     *
     * @return a Map of chip types
     */
    public static synchronized Map<Integer, Chip> chip() {
        if (chip == null) {
            chip = new HashMap<>();

            add(new Chip(1_001, "Carson City Silver Dollars",
                    8.95, 12, "Safflower"));

            add(new Chip(1_002, "Coyote Crenellations",
                    9.95, 12, "Coconut"));

            add(new Chip(1_003, "Four Corner Crispitos",
                    8.95, 12, "Coconut"));

            add(new Chip(1_004, "Jim Bob's Jumbo BBQ",
                    12.95, 16, "Safflower"));

            add(new Chip(1_007, "Saddle Horns",
                    9.95, 10, "Sunflower"));
        }
        return chip;
    }

    /**
     * Return a chip, given its ID.
     *
     * @return a chip
     *
     * @param ID a chip ID
     */
    public static Chip chip(int ID) {
        return chip().get(ID);
    }

    /**
     * Returns a Map of customers.
     *
     *
     * @return a Map of customers
     */
    public static synchronized Map<Integer, Customer> customer() {
        if (customer == null) {
            customer = new HashMap<>();
            add(new Customer(11_156, "Hasskins", "Hank"));
            add(new Customer(11_158, "Shumacher", "Carol"));
            add(new Customer(12_116, "Zeldis", "Kim"));
            add(new Customer(12_122, "Houston", "Jim"));

        }
        return customer;
    }

    /**
     * Return a customer, given his or her ID.
     *
     * @return a customer
     *
     * @param ID a customer ID
     */
    public static Customer customer(int ID) {
        return customer().get(ID);
    }

    /**
     * Returns a vector of orders.
     *
     * @return
     *
     * @return a vector of orders
     */
    public static synchronized ArrayList<Order> order() {
        if (order == null) {
            order = new ArrayList<>();
            add(new Order(customer(11_156), chip(1_001), 2));
            add(new Order(customer(11_156), chip(1_004), 1));
            add(new Order(customer(11_158), chip(1_007), 4));
            add(new Order(customer(12_116), chip(1_002), 2));
            add(new Order(customer(12_116), chip(1_003), 2));
            add(new Order(customer(12_122), chip(1_004), 2));
            add(new Order(customer(12_122), chip(1_007), 2));
        }
        return order;
    }

    private static final Logger LOG = Logger.getLogger(ChipBase.class.getName());

}
