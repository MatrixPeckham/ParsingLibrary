package com.matrixpeckham.parse.examples.engine;

import com.matrixpeckham.parse.engine.Fact;
import com.matrixpeckham.parse.engine.Program;
import com.matrixpeckham.parse.engine.Query;
import com.matrixpeckham.parse.engine.Structure;
import com.matrixpeckham.parse.engine.Term;
import com.matrixpeckham.parse.engine.Variable;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Show a relational join in a coffee database.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowJoin {

    /**
     * Return a small database of coffee types.
     *
     * @return a small database of coffee types.
     */
    public static Program coffee() {
        Fact[] facts = new Fact[]{
            new Fact("coffee", new Object[]{
                "Launch Mi",
                "french",
                "kenya", 6.95}),
            new Fact("coffee", new Object[]{
                "Simple Best",
                "regular",
                "columbia", 5.95}),
            new Fact("coffee", new Object[]{
                "Revit",
                "italian",
                "guatemala", 7.95}),
            new Fact("coffee", new Object[]{
                "Brimful",
                "regular",
                "kenya", 6.95}),
            new Fact("coffee", new Object[]{
                "Smackin",
                "french",
                "columbia", 7.95})};

        Program p = new Program();
        for (Fact fact : facts) {
            p.addAxiom(fact);
        }
        return p;

    }

    /**
     * Return a small database of coffee customers.
     *
     * @return a small database of coffee customers.
     */
    public static Program customer() {
        Fact[] facts = new Fact[]{
            new Fact("customer", new Object[]{
                "Jim Johnson", 2_024}),
            new Fact("customer", new Object[]{
                "Jane Jerrod", 2_077}),
            new Fact("customer", new Object[]{
                "Jasmine Jones", 2_093})
        };

        Program p = new Program();
        for (Fact fact : facts) {
            p.addAxiom(fact);
        }
        return p;

    }

    /**
     * Show a relational join in a coffee database.
     *
     * @param args
     */
    public static void main(String[] args) {
        Program p = new Program();
        p.append(coffee());
        p.append(customer());
        p.append(order());

        Variable name = new Variable("Name");
        Variable custNum = new Variable("CustNum");
        Variable type = new Variable("Type");
        Variable pounds = new Variable("Pounds");

        Structure s1 = new Structure(
                "customer", new Term[]{name, custNum});

        Structure s2 = new Structure(
                "order", new Term[]{custNum, type, pounds});

        // customer(Name, CustNum), order(CustNum, Type, Pounds)
        Query q = new Query(p, new Structure[]{s1, s2});

        while (q.canFindNextProof()) {
            System.out.println("Customer:     " + name);
            System.out.println("Type:         " + type);
            System.out.println("Pounds/Month: " + pounds);
            System.out.println();
        }
    }

    /**
     * Return a small database of coffee orders.
     *
     * @return a small database of coffee orders.
     */
    public static Program order() {
        Fact[] facts = new Fact[]{
            new Fact("order", new Object[]{
                2_024,
                "Simple Best", 1}),
            new Fact("order", new Object[]{
                2_077,
                "Launch Mi", 3}),
            new Fact("order", new Object[]{
                2_077,
                "Smackin", 3}),
            new Fact("order", new Object[]{
                2_093,
                "Brimful", 2})

        };

        Program p = new Program();
        for (Fact fact : facts) {
            p.addAxiom(fact);
        }
        return p;

    }

    private static final Logger LOG = Logger.getLogger(ShowJoin.class.getName());

}
