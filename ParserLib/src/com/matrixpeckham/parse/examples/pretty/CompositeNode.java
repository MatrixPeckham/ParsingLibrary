package com.matrixpeckham.parse.examples.pretty;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * This class provides a composite node that can contain other nodes.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class CompositeNode extends ComponentNode {

    /**
     *
     */
    protected ArrayList<ComponentNode> children = new ArrayList<>();

    /**
     * Create a node that can contain other nodes, and that holds the given
     * value.
     *
     * @param v
     */
    public CompositeNode(String v) {
        this.value = v;
    }

    @Override
    public ComponentNode copy() {
        CompositeNode node = new CompositeNode(value);
        children.stream().
                forEach((ComponentNode n) -> {
                    node.add(n.copy());
                });
        return node;
    }

    /**
     * Add a node after the currently held nodes.
     *
     *
     * @param node another node, either a composite or a terminal node
     */
    public void add(ComponentNode node) {
        children.add(node);
    }

    /**
     * Add a node before the currently held nodes.
     *
     * @param n another node, either a composite or a terminal node
     */
    public void insert(ComponentNode n) {
        children.add(0, n);
    }
    /*
     * Return a textual description of this node. We take care
     * to print a node only once, since a composite may contain
     * cycles. We may or may not want to print the object this
     * composite contains -- the identation indicates the presence
     * of the composite and can obviate the need for printing the
     * composite's value. ShowDangle gives an example of not
     * needing to see the composite's value.
     */

    /**
     *
     * @param depth
     * @param label
     * @param visited
     * @return
     */
    @Override
    protected String toString(
            int depth, boolean label, ArrayList<ComponentNode> visited) {

        if (visited.contains(this)) {
            return "...";
        }
        visited.add(this);
        StringBuilder buf = new StringBuilder();
        if (label) {
            buf.append(indent(depth));
            buf.append(value);
            buf.append("\n");
        }
        Iterator<ComponentNode> e = children.iterator();
        while (e.hasNext()) {
            ComponentNode child = e.next();
            buf.append(child.toString(depth + 1, label, visited));
        }
        return buf.toString();
    }

    private static final Logger LOG
            = Logger.getLogger(CompositeNode.class.getName());

}
