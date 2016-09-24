package com.matrixpeckham.parse.examples.query;

import com.matrixpeckham.parse.engine.AxiomSource;
import com.matrixpeckham.parse.engine.Comparison;
import com.matrixpeckham.parse.engine.Query;
import com.matrixpeckham.parse.engine.Structure;
import com.matrixpeckham.parse.engine.Term;
import static com.matrixpeckham.parse.examples.query.ChipSource.queryStructure;
import com.matrixpeckham.parse.utensil.PubliclyCloneable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

public class QueryBuilder implements PubliclyCloneable<QueryBuilder> {

    /**
     *
     */
    protected Speller speller;

    /**
     *
     */
    protected ArrayList<Term> terms = new ArrayList<>();

    /**
     *
     */
    protected ArrayList<String> classNames = new ArrayList<>();

    /**
     *
     */
    protected ArrayList<Comparison> comparisons = new ArrayList<>();

    /**
     * Construct a query builder that will use the given speller.
     *
     * @param speller
     */
    public QueryBuilder(Speller speller) {
        this.speller = speller;
    }

    /**
     * Add the given class name to the query. This method checks that the class
     * name when properly spelled matches a known class name.
     *
     * @param s
     */
    public void addClassName(String s) {
        String properName = speller.getClassName(s);
        if (properName == null) {
            throw new UnrecognizedClassException(
                    "No class named " + s + " in object model");
        }
        classNames.add(properName);
    }

    /**
     * Add a comparison to the query.
     *
     * @param c
     */
    public void addComparison(Comparison c) {
        comparisons.add(c);
    }

    /**
     * Add a term that will appear in the head structure of the query.
     *
     * @param t
     */
    public void addTerm(Term t) {
        terms.add(t);
    }

    /**
     * Create a query from the terms, class names and variables this object has
     * received so far.
     *
     * @param as
     * @return
     */
    public Query build(AxiomSource as) {
        ArrayList<Structure> structures = new ArrayList<>();

        // create the "projection" structure
        Term[] termArray = new Term[terms.size()];
        terms.toArray(termArray);
        Structure s = new Structure("q", termArray);
        structures.add(s);

        // add each queried table
        Iterator<String> e = classNames.iterator();
        while (e.hasNext()) {
            String name = e.next();
            structures.add(queryStructure(name));
        }

        // add each comparison
        Iterator<Comparison> i = comparisons.iterator();
        while (i.hasNext()) {
            Comparison c = i.next();
            structures.add(c);
        }

        // create and return a query
        Structure sarray[] = new Structure[structures.size()];
        sarray = structures.toArray(sarray);
        return new Query(as, sarray);
    }

    @Override
    public QueryBuilder copy() {
        QueryBuilder c = new QueryBuilder(speller);
        c.terms = new ArrayList<>();
        terms.stream().
                forEachOrdered((t) -> {
                    c.terms.add(t);
                });
        c.classNames = new ArrayList<>();
        classNames.stream().
                forEachOrdered((t) -> {
                    c.classNames.add(t);
                });
        c.comparisons = new ArrayList<>();
        comparisons.stream().
                forEachOrdered((t) -> {
                    c.comparisons.add(t);
                });
        return c;
    }

    private static final Logger LOG
            = Logger.getLogger(QueryBuilder.class.getName());

}
