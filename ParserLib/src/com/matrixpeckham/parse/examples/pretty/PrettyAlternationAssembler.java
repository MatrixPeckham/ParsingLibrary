package com.matrixpeckham.parse.examples.pretty;

import com.matrixpeckham.parse.parse.Assembler;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.logging.Logger;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Replace a <code>ComponentNode</code> object on the stack with a new composite
 * that holds the popped node as its only child.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class PrettyAlternationAssembler extends Assembler<Token, ComponentNode, NullCloneable> {

    /**
     *
     */
    protected String name;

    /**
     * Create an assembler that will replace a <code>ComponentNode
     * </code> object on the stack with a new composite that holds the popped
     * node as its only child and whose name is as supplied here.
     *
     * @param name
     */
    public PrettyAlternationAssembler(String name) {
        this.name = name;
    }

    /**
     * Replace a <code>ComponentNode</code> object on the stack with a new
     * composite that holds the popped node as its only child.
     *
     * @param a the assembly to work on
     */
    @Override
    public void workOn(Assembly<Token, ComponentNode, NullCloneable> a) {
        CompositeNode newNode = new CompositeNode(name);
        ComponentNode node = a.popVal();
        newNode.insert(node);
        a.pushVal(newNode);
    }

    private static final Logger LOG
            = Logger.getLogger(PrettyAlternationAssembler.class.getName());

}
