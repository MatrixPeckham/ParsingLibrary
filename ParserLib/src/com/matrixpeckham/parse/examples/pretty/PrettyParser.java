package com.matrixpeckham.parse.examples.pretty;

import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Parser;
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
 * This class accepts a <code>Parser</code> object in its constructor. The
 * constructor sets each assembler in the parser composite to be one of the
 * "pretty" assemblers in this package. These assemblers build a tree of nodes
 * from the <code>ComponentNode</code> hierarchy that is also in this package.
 * The resulting tree effectively records the order in which the parse proceeds.
 * Printing the tree results in a "pretty print" or a standard formatting of the
 * parse. The tree indents composite nodes (sequences, alternations and
 * repetitions) and prints terminals as they appeared in the input.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class PrettyParser {

    /**
     *
     */
    protected Parser<?, ?, ?> parser;

    boolean showLabels = false;

    /**
     * Construct a pretty parser whose assemblers create a "pretty print" of
     * input text.
     *
     * @param parser
     */
    public PrettyParser(Parser parser) {
        this.parser = parser;
        parser.accept(new PrettyVisitor());
    }
    /*
     * Return a collection of complete parses of the given assembly.
     */

    /**
     *
     * @param inAssembly
     * @return
     */
    protected ArrayList completeMatches(Assembly inAssembly) {
        ArrayList inState = new ArrayList();
        inState.add(inAssembly);
        ArrayList outState = parser.matchAndAssemble(inState);
        ArrayList outComplete = new ArrayList();
        Iterator e = outState.iterator();
        while (e.hasNext()) {
            Assembly a = (Assembly) e.next();
            if (!a.hasNext()) {
                outComplete.add(a);
            }
        }
        return outComplete;
    }

    /**
     * Returns true if this <code>PrettyParser</code> object will show labels
     * for composite nodes.
     *
     * @return true if this <code>PrettyParser</code> object will show labels
     * for composite nodes.
     */
    public boolean getShowLabels() {
        return showLabels;
    }

    /**
     * Returns a collection of strings that show the order of a parse of the
     * given assembly.
     *
     * @param inAssembly the assembly to parse
     *
     * @return a collection of strings that show the order of a parse of the
     * given assembly
     */
    public ArrayList parseTrees(Assembly inAssembly) {
        ArrayList outAssemblies = completeMatches(inAssembly);
        ArrayList outStrings = new ArrayList();
        Iterator e = outAssemblies.iterator();
        while (e.hasNext()) {
            Assembly a = (Assembly) e.next();
            ComponentNode node = (ComponentNode) a.pop();
            outStrings.add(treeString(node));
        }
        return outStrings;
    }

    /**
     * Sets the boolean that determines if this <code>PrettyParser
     * </code> object will show labels for composite nodes.
     *
     * @param showLabels the boolean that determines if this
     * <code>PrettyParser</code> object will show labels for composite nodes.
     */
    public void setShowLabels(boolean showLabels) {
        this.showLabels = showLabels;
    }
    /*
     * Return a string representation of a parse tree. The tree
     * is a component node that typically contains a hierarchy
     * of other nodes. Show labels for composite nodes if we have
     * been instructed to do so.
     */

    /**
     *
     * @param node
     * @return
     */
    protected String treeString(ComponentNode node) {
        if (showLabels) {
            return node.toString();
        } else {
            return node.toStringWithoutLabels();
        }
    }

    private static final Logger LOG
            = Logger.getLogger(PrettyParser.class.getName());

}
