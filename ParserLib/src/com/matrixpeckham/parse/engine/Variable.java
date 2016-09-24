package com.matrixpeckham.parse.engine;

import java.util.Objects;
import java.util.logging.Logger;

public class Variable implements ArithmeticTerm, ComparisonTerm {

    /**
     *
     */
    public final String name;

    /**
     *
     */
    protected Term instantiation;

    /**
     * Create a variable with the given name.
     *
     * @param name
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * Create a copy that uses the provided scope.
     *
     * @param ignored
     * @param scope the scope to use for variables in the copy
     *
     * @return a copy that uses the provided scope
     */
    @Override
    public Term copyForProof(AxiomSource ignored, Scope scope) {
        return scope.lookup(name);
    }

    /**
     * Returns string representation of this variable, showing both its name and
     * its value.
     *
     * @return a string representation of this variable, showing both its name
     * and its value.
     */
    public String definitionString() {
        if (instantiation != null) {
            return name + " = " + instantiation;
        }
        return name;
    }

    /**
     * Returns true if the supplied object is an equivalent variable.
     *
     * @param o the object to compare
     *
     * @return true, if the supplied object has the same name, and it the two
     * variables' instantiations are equal
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Variable)) {
            return false;
        }
        Variable v = (Variable) o;
        if (!name.equals(v.name)) {
            return false;
        }
        if (instantiation == null) {
            return v.instantiation == null;
        }
        return instantiation.equals(v.instantiation);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.instantiation);
        return hash;
    }

    /**
     * Returns the value of this variable.
     *
     * @return the value of this variable
     *
     * @exception EvaluationException if this variable's value is undefined
     */
    @Override
    public Object eval() {
        if (instantiation == null) {
            throw new EvaluationException(
                    "Variable " + name + " is undefined");
        }
        return instantiation.eval();
    }

    /**
     * Returns true if this variable is uninstantiated, or if it contains a
     * list.
     *
     * @Returns true if this variable is uninstantiated, or if it contains a
     * list
     */
    @Override
    public boolean isList() {
        if (instantiation != null) {
            return instantiation.isList();
        }
        return true;
    }

    /**
     * Returns a string representation of this variable as the tail of a list.
     *
     * @return a string representation of this variable as the tail of a list
     */
    @Override
    public String listTailString() {
        if (instantiation != null) {
            return instantiation.listTailString();
        }
        return "|" + name;
    }

    /**
     * Returns a string representation of this variable.
     *
     * @return a string representation of this variable
     */
    @Override
    public String toString() {
        if (instantiation != null) {
            return instantiation.toString();
        }
        return name;
    }

    /**
     * Marks this variable as no longer having an instantiated value.
     */
    public void unbind() {
        instantiation = null;
    }

    /**
     * Instantiates this variable with the supplied structure, or forwards the
     * request to its instantiation if it already has one.
     *
     * @param s a structure to unify with
     *
     * @return a unification. If this variable is already instantiated, the
     * unification is the result of unifying with the input structure.
     * Otherwise, the result is a new unification containing just this variable,
     * instantiated to the input structure.
     */
    @Override
    public Unification unify(Structure s) {
        if (instantiation != null) {
            return instantiation.unify(s);
        }
        instantiation = s;
        return new Unification(this);
    }

    /**
     * Unifies this variable with the supplied term.
     * <p>
     * This method dispatches the unify request to either a structure or a
     * variable. The receiver will get a signature match from this object as a
     * Variable, not just a Term.
     *
     * @param t a term to unify with
     *
     * @return the sum of the variables that bind to values to make the
     * unification work; Returns null if the unification fails.
     */
    @Override
    public Unification unify(Term t) {
        return t.unify(this);
    }

    /**
     * Instantiates this variable with the supplied variable, or forwards the
     * request to its instantiation if it already has one.
     *
     * @param v a variable to unify with
     *
     * @return the sum of the variables that bind to values to make the
     * unification work; Returns null if the unification fails.
     */
    @Override
    public Unification unify(Variable v) {
        if (this == v) {
            return new Unification();
        }
        if (instantiation != null) {
            return instantiation.unify(v);
        }
        if (v.instantiation != null) {
            return v.instantiation.unify(this);
        }
        instantiation = v;
        return new Unification(this);
    }

    /**
     * Returns a unification containing just this variable.
     *
     * @return a unification containing just this variable
     */
    @Override
    public Unification variables() {
        return new Unification(this);
    }

    private static final Logger LOG = Logger.getLogger(Variable.class.getName());

}
