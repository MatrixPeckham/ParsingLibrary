package com.matrixpeckham.parse.engine;

import com.matrixpeckham.parse.utensil.PubliclyCloneable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * A scope is a repository for variables. A dynamic rule has a scope, which
 * means that variables with the same name are the same variable. Consider a
 * rule that describes big cities:
 * <p>
 * <blockquote><pre>
 *     bigCity(Name) :- city(Name, Pop), &gt;(Pop, 1000000);
 * </pre></blockquote>
 * <p>
 * This example follows the Prolog convention of using capitalized words for
 * variables. The variables <code>Name
 * </code> and <code>Pop</code> have scope throughout the rule.
 * <p>
 * The <code>bigCity</code> rule proves itself by finding a city and checking
 * its population. When the <code>city
 * </code> structure binds with a city fact in a program, its variables take on
 * the values of the fact. For example, <code>city</code> might bind with the
 * fact <code>
 * city(bigappolis, 8733352) </code>. Then <code>Name</code> and
 * <code>Pop</code> will bind to the values "bigappolis" and 8733352.
 * <p>
 * When the comparison proves itself, it will compare <code>
 * Pop</code> to 1000000. This is the same variable as <code>
 * Pop</code> in the <code>city</code> structure of the rule. With this
 * successful comparison, the rule completes a successful proof, with
 * <code>Name</code> bound to "bigappolis".
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class Scope implements PubliclyCloneable<Scope> {

    HashMap<String, Variable> map = new HashMap<>();

    /**
     * Create an empty scope.
     */
    public Scope() {
    }

    /**
     * Create a scope that uses the variables in the supplied terms.
     *
     * @param terms the terms to seed this scope with
     */
    public Scope(Term terms[]) {
        for (Term term : terms) {
            Unification u = term.variables();
            Iterator<Variable> e = u.iterator();
            while (e.hasNext()) {
                Variable v = e.next();
                map.put(v.name, v);
            }
        }
    }

    /**
     * Remove all variables from this scope.
     */
    public void clear() {
        map.clear();
    }

    /**
     * Return a copy of this object.
     *
     * @return a copy of this object
     */
    @Override
    public Scope copy() {
        Scope clone = new Scope();
        clone.map.clear();
        clone.map.putAll(map);
        return clone;
    }

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
     * <p>
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
