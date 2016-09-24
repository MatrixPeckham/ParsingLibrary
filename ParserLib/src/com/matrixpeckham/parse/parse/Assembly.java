package com.matrixpeckham.parse.parse;

import com.matrixpeckham.parse.utensil.PubliclyCloneable;
import com.matrixpeckham.parse.utensil.TypeOrType;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Stack;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * An assembly maintains a stream of language elements along with stack and
 * target objects.
 *
 * Parsers use assemblers to record progress at recognizing language elements
 * from assembly's string.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 * @param <Tok>
 * @param <Val>
 * @param <Tar>
 *
 */
public abstract class Assembly<Tok, Val, Tar extends PubliclyCloneable<Tar>>
        implements Iterator<Tok>, PubliclyCloneable<Assembly<Tok, Val, Tar>> {

    /*
     * which element is next
     */
    protected int index = 0;

    /*
     * a place to keep track of consumption progress
     */
    protected Stack<TypeOrType<Tok, Val>> stack = new Stack<>();

    /* Another place to record progress; this is just an object.
     * If a parser were recognizing an HTML page, for
     * example, it might create a Page object early, and store it
     * as an assembly's "target". As its recognition of the HTML
     * progresses, it could use the stack to build intermediate
     * results, like a heading, and then apply them to the target
     * object.
     */
    protected Tar target;

    public Assembly() {
    }

    public Assembly(Assembly<Tok, Val, Tar> a) {
        this.stack.addAll(a.stack);
        if (a.target != null) {
            this.target = a.target.copy();
        }
        index = a.index;
    }

    /**
     * Returns the elements of the assembly that have been consumed, separated
     * by the specified delimiter.
     *
     * @param delimiter the mark to show between consumed elements
     *
     * @return the elements of the assembly that have been consumed
     */
    public abstract String consumed(String delimiter);

    /**
     * Returns the default string to show between elements.
     *
     * @return the default string to show between elements
     */
    public abstract String defaultDelimiter();

    /**
     * Returns the number of elements that have been consumed.
     *
     * @return the number of elements that have been consumed
     */
    public int elementsConsumed() {
        return index;
    }

    /**
     * Returns the number of elements that have not been consumed.
     *
     * @return the number of elements that have not been consumed
     */
    public int elementsRemaining() {
        return length() - elementsConsumed();
    }

    /**
     * Removes this assembly's stack.
     *
     * @return this assembly's stack
     */
    public Stack<TypeOrType<Tok, Val>> getStack() {
        return stack;
    }

    /**
     * Returns the object identified as this assembly's "target". Clients can
     * set and retrieve a target, which can be a convenient supplement as a
     * place to work, in addition to the assembly's stack. For example, a parser
     * for an HTML file might use a web page object as its "target". As the
     * parser recognizes markup commands like &lt;head&gt;, it could apply its
     * findings to the target.
     *
     * @return the target of this assembly
     */
    public Tar getTarget() {
        return target;
    }

    /**
     * Returns true if this assembly has unconsumed elements.
     *
     * @return true, if this assembly has unconsumed elements
     */
    @Override
    public boolean hasNext() {
        return elementsConsumed() < length();
    }

    /**
     * Returns the number of elements in this assembly.
     *
     * @return the number of elements in this assembly
     */
    public abstract int length();

    /**
     * Shows the next object in the assembly, without removing it
     *
     * @return the next object
     *
     */
    public abstract Object peek();

    /**
     * Shows the next object in the assembly, without removing it
     *
     * @return the next object
     *
     */
    public abstract Tok peekTok();

    /**
     * Removes the object at the top of this assembly's stack and returns it.
     *
     * @return the object at the top of this assembly's stack
     *
     * @exception EmptyStackException if this stack is empty
     */
    public Object pop() {
        return stack.pop().asObject();
    }

    public TypeOrType<Tok, Val> popTypeOrType() {
        return stack.pop();
    }

    /**
     * Removes the object at the top of this assembly's stack and returns it.
     *
     * @return the object at the top of this assembly's stack
     *
     * @exception EmptyStackException if this stack is empty
     */
    public Val popVal() {
        return stack.pop().asV();
    }

    /**
     * Removes the object at the top of this assembly's stack and returns it.
     *
     * @return the object at the top of this assembly's stack
     *
     * @exception EmptyStackException if this stack is empty
     */
    public Tok popTok() {
        return stack.pop().asT();
    }

    /**
     * Pushes an object onto the top of this assembly's stack.
     *
     * @param o the object to be pushed
     */
    public void push(Object o) {
        stack.push(TypeOrType.<Tok, Val>fromObject(o));
    }

    /**
     * pushes a token object onto this assembly's stack.
     *
     * @param o
     */
    public void pushTok(Tok o) {
        stack.push(TypeOrType.<Tok, Val>fromT(o));
    }

    /**
     * pushes a value object onto this assembly's stack.
     *
     * @param o
     */
    public void pushVal(Val o) {
        stack.push(TypeOrType.<Tok, Val>fromV(o));
    }

    /**
     * Returns the elements of the assembly that remain to be consumed,
     * separated by the specified delimiter.
     *
     * @param delimiter the mark to show between unconsumed elements
     *
     * @return the elements of the assembly that remain to be consumed
     */
    public abstract String remainder(String delimiter);

    /**
     * Sets the target for this assembly. Targets must implement
     * <code>copy()</code> as a public method.
     *
     * @param target a publicly cloneable object
     */
    public void setTarget(Tar target) {
        this.target = target;
    }

    /**
     * Returns true if this assembly's stack is empty.
     *
     * @return true, if this assembly's stack is empty
     */
    public boolean stackIsEmpty() {
        return stack.isEmpty();
    }

    /**
     * Returns a textual description of this assembly.
     *
     * @return a textual description of this assembly
     */
    @Override
    public String toString() {
        String delimiter = defaultDelimiter();
        return stack + consumed(delimiter) + "^" + remainder(delimiter);
    }

    /**
     * Put back n objects
     *
     * @param n
     */
    public void unget(int n) {
        index -= n;
        if (index < 0) {
            index = 0;
        }
    }
}
