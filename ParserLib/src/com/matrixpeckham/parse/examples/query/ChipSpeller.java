package com.matrixpeckham.parse.examples.query;

import com.matrixpeckham.parse.engine.Structure;
import com.matrixpeckham.parse.engine.Unification;
import com.matrixpeckham.parse.engine.Variable;
import static com.matrixpeckham.parse.examples.query.ChipSource.queries;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * This class maintains dictionaries of the proper spelling of class and
 * variable names in the chip object model.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ChipSpeller implements Speller {

    /**
     *
     */
    protected Map<String, String> classNames;

    /**
     *
     */
    protected Map<String, String> variableNames;

    /**
     * Initialize the ChipSpeller.
     */
    public ChipSpeller() {
        loadClassNames();
        loadVariableNames();
    }
    /*
     * Add one class name.
     */

    /**
     *
     * @param s
     */
    protected void addClassName(String s) {
        classNames.put(s.toLowerCase(Locale.ROOT), s);
    }
    /*
     * Add one variable name.
     */

    /**
     *
     * @param s
     */
    protected void addVariableName(String s) {
        variableNames.put(s.toLowerCase(Locale.ROOT), s);
    }

    /**
     * Return the properly capitalized spelling of a class name, given any
     * capitalization of the name.
     *
     * @param s
     * @return the properly capitalized spelling of a class name, given any
     * capitalization of the name.
     */
    @Override
    public String getClassName(String s) {
        return classNames.get(s.toLowerCase(Locale.ROOT));
    }

    /**
     * Return the properly capitalized spelling of a variable name, given any
     * capitalization of the name.
     *
     * @param s
     * @return the properly capitalized spelling of a variable name, given any
     * capitalization of the name.
     */
    @Override
    public String getVariableName(String s) {
        return variableNames.get(s.toLowerCase(Locale.ROOT));
    }
    /*
     * Load all the class names from ChipData into the
     * class name Map.
     */

    /**
     *
     */
    protected final void loadClassNames() {
        classNames = new HashMap<>();
        addClassName("chip");
        addClassName("customer");
        addClassName("order");
    }
    /*
     * Load all the variable names from ChipData into the
     * class name Map.
     */

    /**
     *
     */
    protected final void loadVariableNames() {
        variableNames = new HashMap<>();
        /*
         * Use the query templates to detect the variable
         * names.
         */
        Iterator<Structure> e = queries().values().iterator();
        while (e.hasNext()) {
            Structure s = e.next();
            Unification u = s.variables();
            /*
             * Add each variable from each query template
             */
            Iterator<Variable> e2 = u.iterator();
            while (e2.hasNext()) {
                Variable v = e2.next();
                addVariableName(v.name);
            }
        }
    }

    private static final Logger LOG
            = Logger.getLogger(ChipSpeller.class.getName());

}
