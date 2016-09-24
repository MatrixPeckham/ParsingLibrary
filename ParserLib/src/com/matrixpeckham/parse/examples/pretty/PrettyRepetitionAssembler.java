package com.matrixpeckham.parse.examples.pretty;

import com.matrixpeckham.parse.parse.Assembler;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.utensil.NullCloneable;
import com.matrixpeckham.parse.utensil.TypeOrType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

public class PrettyRepetitionAssembler extends Assembler<Token, ComponentNode, NullCloneable> {

    /**
     *
     */
    protected String name;

    /**
     *
     */
    protected Object fence;

    /**
     * Construct an assembler that will replace the nodes above the supplied
     * "fence" object with a new composite that will hold the popped nodes as
     * its children.
     *
     * @param name
     * @param fence
     */
    public PrettyRepetitionAssembler(String name, Object fence) {
        this.name = name;
        this.fence = fence;
    }

    /**
     * Replace the nodes above a given "fence" object with a new composite that
     * holds the popped nodes as its children.
     *
     * @param a the assembly to work on
     */
    @Override
    public void workOn(Assembly<Token, ComponentNode, NullCloneable> a) {
        CompositeNode newNode = new CompositeNode(name);
        ArrayList<TypeOrType<Token, ComponentNode>> v = elementsAbove(a, fence);
        Iterator<TypeOrType<Token, ComponentNode>> e = v.iterator();
        while (e.hasNext()) {
            newNode.add(e.next().asV());
        }
        a.pushVal(newNode);
    }

    private static final Logger LOG
            = Logger.getLogger(PrettyRepetitionAssembler.class.getName());

}
