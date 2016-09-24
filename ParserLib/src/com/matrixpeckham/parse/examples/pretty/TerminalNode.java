package com.matrixpeckham.parse.examples.pretty;

import java.util.logging.Logger;

/**
 *
 * @author Owner
 */
public class TerminalNode extends ComponentNode {

    /**
     * Create a node that holds the given value.
     *
     * @param value
     */
    public TerminalNode(String value) {
        this.value = value;
    }

    @Override
    public ComponentNode copy() {
        return new TerminalNode(value);
    }

    /*
     * Return a textual description of this node, indenting the
     * string based on the depth of the node.
     */
    /**
     *
     * @param depth
     * @param label
     * @param ignored
     * @return
     */
    @Override
    protected String toString(
            int depth, boolean label, java.util.ArrayList<ComponentNode> ignored) {

        return indent(depth) + value + "\n";
    }

    private static final Logger LOG
            = Logger.getLogger(TerminalNode.class.getName());

}
