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
 * Class two types.
 *
 * @author William Matrix Peckham
 * @param <T>
 * @param <V>
 */
public class TypeOrType<T, V> {

    private T asT = null;

    private V asV = null;

    private Object asObject = null;

    public static <T, V> TypeOrType<T, V> fromT(T t) {
        TypeOrType<T, V> tt = new TypeOrType<>();
        tt.asT = t;
        tt.asObject = t;
        return tt;
    }

    public static <T, V> TypeOrType<T, V> fromV(V v) {
        TypeOrType<T, V> tt = new TypeOrType<>();
        tt.asV = v;
        tt.asObject = v;
        return tt;
    }

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

    public static <T, V> TypeOrType<T, V> fromObject(Object o) {
        TypeOrType<T, V> tt = new TypeOrType<>();
        tt.asObject = o;
        return tt;
    }

    public T asT() {
        if (asT == null) {
            throw new RuntimeException("Attempted to access incorrect type!");
        }
        return asT;
    }

    public V asV() {
        if (asV == null) {
            throw new RuntimeException("Attempted to access incorrect type!");
        }
        return asV;
    }

    public Object asObject() {
        return asObject;
    }

    public boolean isT() {
        return asT != null;
    }

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
