package com.matrixpeckham.parse.examples.sling;

import java.util.HashMap;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * This class holds a collection of variables.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class Scope {

    /**
     *
     */
    protected HashMap<String, Variable> map = new HashMap<>();

    /**
     * Returns true if a variable of the given name appears in this scope.
     *
     * @param name the variable name
     *
     * @return true, if a variable of the given name appears in this scope.
     */
    public boolean isDefined(String name) {
        return map.containsKey(name);
    }

    /**
     * Returns a variable of the given name from this scope.
     *
     * If the so-named variable is not already in this scope, the scope will
     * create it and add the variable to itself.
     *
     * @param name the variable name
     *
     * @return a variable of the given name from this scope
     */
    public Variable lookup(String name) {
        Variable v = map.get(name);
        if (v == null) {
            v = new Variable(name);
            map.put(v.name, v);
        }
        return v;
    }

    private static final Logger LOG = Logger.getLogger(Scope.class.getName());

}
