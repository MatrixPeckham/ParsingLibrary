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
 * Replace a given number of nodes on the stack with a new composite that holds
 * the popped nodes as its children.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class PrettySequenceAssembler extends Assembler<Token, ComponentNode, NullCloneable> {

    /**
     *
     */
    protected String name;

    /**
     *
     */
    protected int numberNodes;

    /**
     * Construct an assembler that will replace a given number of nodes on the
     * stack with a new composite that holds the popped nodes as its children.
     *
     * @param name
     * @param numberNodes
     */
    public PrettySequenceAssembler(String name, int numberNodes) {
        this.name = name;
        this.numberNodes = numberNodes;
    }

    /**
     * Replace a given number of nodes on the stack with a new composite that
     * holds the popped nodes as its children.
     *
     * @param a the assembly to work on
     */
    @Override
    public void workOn(Assembly<Token, ComponentNode, NullCloneable> a) {
        CompositeNode newNode = new CompositeNode(name);
        for (int i = 0; i < numberNodes; i++) {
            ComponentNode node = a.popVal();
            newNode.insert(node);
        }
        a.pushVal(newNode);
    }

    private static final Logger LOG
            = Logger.getLogger(PrettySequenceAssembler.class.getName());

}
