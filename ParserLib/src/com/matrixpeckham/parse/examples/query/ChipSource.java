package com.matrixpeckham.parse.examples.query;

import static com.matrixpeckham.parse.examples.chips.ChipBase.*;

import com.matrixpeckham.parse.engine.*;
import com.matrixpeckham.parse.examples.chips.*;
import java.util.*;
import java.util.logging.Logger;

/**
 * This class draws on data from the class ChipBase in
 * sjm.examples.chips, to supplies facts about chips,
 * customers, and orders.
 *
 * @author Steven J. Metsker
 */
public class ChipSource implements AxiomSource {

    /**
     *
     */
    static Map<String, Structure> queries;

    /**
     *
     */
    public static final ChipSpeller speller = new ChipSpeller();

    /**
     * Returns all the data in the chip database.
     *
     * @return all the data in the chip database.
     */
    @Override
    public AxiomIterator axioms() {
        return new ProgramEnumerator(program());
    }

    /**
     * Returns all the data in the chip database.
     * <p>
     * If there were thousands of chip facts, this method could use the querying
     * structure to limit the number of facts it returns.
     *
     * @param s
     *
     * @return all the data in the chip database.
     */
    @Override
    public AxiomIterator axioms(Structure s) {
        return axioms();
    }

    /**
     * Create a chip fact from a chip object.
     *
     * @param c
     *
     * @return a chip fact, given a chip object.
     */
    public static Fact fact(Chip c) {

        return new Fact("chip", new Object[]{
            c.getChipID(),
            c.getChipName(),
            c.getPrice(),
            c.getOunces(),
            c.getOil()});

    }

    /**
     * Create a customer fact from a customer object.
     *
     * @param c
     *
     * @return a customer fact, given a customer object
     */
    public static Fact fact(Customer c) {

        return new Fact("customer", new Object[]{
            c.getCustomerID(),
            c.getLastName(),
            c.getFirstName()});
    }

    /**
     * Create an order fact from an order object.
     *
     * @param o
     *
     * @return an order fact, given an order object
     */
    public static Fact fact(Order o) {

        return new Fact("order", new Object[]{
            o.getCustomer().getCustomerID(),
            o.getChip().getChipID(),
            o.getBagsPerMonth()});
    }

    /**
     * Returns all the data in the chip database.
     *
     * @return all the data in the chip database.
     */
    public static Program program() {
        Program p = new Program();
        Map<Integer, Chip> chips;
        ArrayList<Order> v;
        Iterator<Chip> ce;

        // chips
        chips = chip();
        ce = chips.values().iterator();
        while (ce.hasNext()) {
            p.addAxiom(fact(ce.next()));
        }

        // customers
        Map<Integer, Customer> d = customer();
        Iterator<Customer> e = d.values().iterator();
        while (e.hasNext()) {
            p.addAxiom(fact(e.next()));
        }

        // orders
        v = order();
        Iterator<Order> oe = v.iterator();
        while (oe.hasNext()) {
            p.addAxiom(fact(oe.next()));
        }
        return p;
    }

    /**
     * Returns a Map of query structures for the chip
     * classes, keyed by the class name.
     *
     * @return
     */
    public synchronized static Map<String, Structure> queries() {
        if (queries == null) {
            queries = new HashMap<>();
            queries.put("chip", queryChip());
            queries.put("customer", queryCustomer());
            queries.put("order", queryOrder());
        }
        return queries;
    }

    /**
     * Returns a query that matches chip facts.
     *
     * @return
     */
    public static Structure queryChip() {
        return new Structure("chip", new Term[]{
            new Variable("ChipID"),
            new Variable("ChipName"),
            new Variable("PricePerBag"),
            new Variable("Ounces"),
            new Variable("Oil")});
    }

    /**
     * Returns a query that matches chip facts.
     *
     * @return
     */
    public static Structure queryCustomer() {
        return new Structure("customer", new Term[]{
            new Variable("CustomerID"),
            new Variable("LastName"),
            new Variable("FirstName")});
    }

    /**
     * Returns a query that matches chip facts.
     *
     * @return
     */
    public static Structure queryOrder() {
        return new Structure("order", new Term[]{
            new Variable("CustomerID"),
            new Variable("ChipID"),
            new Variable("BagsPerMonth")});
    }

    /**
     * Given the name of a class, return a query that will match against facts
     * that represent objects of the class.
     *
     * @return a query structure
     *
     * @param className the class name (from the chip object model) for which to
     *                  return a query
     *
     * @exception UnrecognizedClassException if the class name is not part of
     *                                       the chip object model
     */
    public static Structure queryStructure(String className) {
        if (className.equals("chip")) {
            return queryChip();
        }
        if (className.equals("customer")) {
            return queryCustomer();
        }
        if (className.equals("order")) {
            return queryOrder();
        }
        throw new UnrecognizedClassException(
                className + " is not a recognized class name");
    }

    private static final Logger LOG
            = Logger.getLogger(ChipSource.class.getName());

}
