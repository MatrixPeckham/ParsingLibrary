package com.matrixpeckham.parse.utensil;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Defines a type of object which anybody can copy.
 * <p>
 */
public interface PubliclyCloneable<T> {

    /**
     * A PubliclyCloneable object is one to which any object can send
     * <code>copy()</code>.
     *
     * @return a copy of the receiving object
     */
    public T copy();

}
