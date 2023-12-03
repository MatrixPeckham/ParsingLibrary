/*
 * DEFAULT LICENSE
 * Do not make illegal copies
 * This software is provided as is, without any warranty at all
 * Not responsible for any damage to anything that may occur
 * Copyright © 2012 William Peckham
 */
package com.matrixpeckham.parse.utensil;

import java.util.logging.Logger;

/**
 * Class to allow a generic parameter to have 2 different possible types.
 *
 * @author William Matrix Peckham
 * @param <T>
 * @param <V>
 */
public class TypeOrType<T, V> {

    private T asT = null;

    private V asV = null;

    private Object asObject = null;

    /**
     * Creates an object from the first type this class can hold.
     *
     * @param <T>
     * @param <V>
     * @param t
     *
     * @return
     */
    public static <T, V> TypeOrType<T, V> fromT(T t) {
        TypeOrType<T, V> tt = new TypeOrType<>();
        tt.asT = t;
        tt.asObject = t;
        return tt;
    }

    /**
     * Creates an object from the second type this class can hold.
     *
     * @param <T>
     * @param <V>
     * @param v
     *
     * @return
     */
    public static <T, V> TypeOrType<T, V> fromV(V v) {
        TypeOrType<T, V> tt = new TypeOrType<>();
        tt.asV = v;
        tt.asObject = v;
        return tt;
    }

    /**
     * Creates an object from a two parameters that must be equal. For times
     * when the object can be both types.
     *
     * @param <T>
     * @param <V>
     * @param t
     * @param v
     *
     * @return
     */
    public static <T, V> TypeOrType<T, V> fromBoth(T t, V v) {
        if (!t.equals(v)) {
            throw new IllegalArgumentException(
                    "Both arguments should be the same.");
        }
        TypeOrType<T, V> tt = new TypeOrType<>();
        tt.asT = t;
        tt.asV = v;
        tt.asObject = t;
        return tt;
    }

    /**
     * Allows arbitrary object, but negates the purpose of using
     * this class. The accessors for individual types won't work.
     *
     * @param <T>
     * @param <V>
     * @param o
     *
     * @return
     */
    public static <T, V> TypeOrType<T, V> fromObject(Object o) {
        TypeOrType<T, V> tt = new TypeOrType<>();
        tt.asObject = o;
        return tt;
    }

    /**
     * Returns the object as the first argument type.
     * Throws a runtime exception if the object isn't of that type.
     *
     * @return
     */
    public T asT() {
        if (asT == null) {
            throw new RuntimeException("Attempted to access incorrect type!");
        }
        return asT;
    }

    /**
     * Returns the object as the second argument type.
     * Throws a runtime exception if the object isn't of that type.
     *
     * @return
     */
    public V asV() {
        if (asV == null) {
            throw new RuntimeException("Attempted to access incorrect type!");
        }
        return asV;
    }

    /**
     * Returns a raw object.
     *
     * @return
     */
    public Object asObject() {
        return asObject;
    }

    /**
     * Tests the type.
     *
     * @return
     */
    public boolean isT() {
        return asT != null;
    }

    /**
     * Tests the type.
     *
     * @return
     */
    public boolean isV() {
        return asV != null;
    }

    @Override
    public String toString() {
        if (asT != null) {
            return asT.toString();
        }
        if (asV != null) {
            return asV.toString();
        }
        return asObject.toString();
    }

    private static final Logger LOG
            = Logger.getLogger(TypeOrType.class.getName());

}
