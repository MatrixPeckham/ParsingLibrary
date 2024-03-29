package com.matrixpeckham.parse.parse.tokens;

import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 * A <code>SymbolNode</code> object is a member of a tree that
 * contains all possible prefixes of allowable symbols. Multi-
 * character symbols appear in a <code>SymbolNode</code> tree
 * with one node for each character.
 * <p>
 * For example, the symbol <code>=:~</code> will appear in a
 * tree as three nodes. The first node contains an equals sign,
 * and has a child; that child contains a colon and has a
 * child; this third child contains a tilde, and has no
 * children of its own. If the colon node had another child
 * for a dollar sign character, then the tree would contain
 * the symbol <code>=:$</code>.
 * <p>
 * A tree of <code>SymbolNode</code> objects collaborate to
 * read a (potentially multi-character) symbol from an input
 * stream. A root node with no character of its own finds an
 * initial node that represents the first character in the
 * input. This node looks to see if the next character in the
 * stream matches one of its children. If so, the node
 * delegates its reading task to its child. This approach
 * walks down the tree, pulling symbols from the input that
 * match the path down the tree.
 * <p>
 * When a node does not have a child that matches the next
 * character, we will have read the longest possible symbol
 * prefix. This prefix may or may not be a valid symbol.
 * Consider a tree that has had <code>=:~</code> added and has
 * not had <code>=:</code> added. In this tree, of the three
 * nodes that contain <code>=:~</code>, only the first and
 * third contain complete symbols. If, say, the input contains
 * <code>=:a</code>, the colon node will not have a child that
 * matches the 'a' and so it will stop reading. The colon node
 * has to "unread": it must push back its character, and ask
 * its parent to unread. Unreading continues until it reaches
 * an ancestor that represents a valid symbol.
 */
public class SymbolNode {

    /**
     *
     */
    protected char myChar;

    /**
     *
     */
    protected ArrayList<SymbolNode> children = new ArrayList<>(); // of Node

    /**
     *
     */
    protected boolean valid = false;

    /**
     *
     */
    protected SymbolNode parent;

    /**
     * Constructs a SymbolNode with the given parent, representing the given
     * character.
     *
     * @param parent this node's parent
     *
     *
     * @param myChar this node's character
     */
    public SymbolNode(SymbolNode parent, char myChar) {
        this.parent = parent;
        this.myChar = myChar;
    }

    /**
     * Add a line of descendants that represent the characters
     * in the given string.
     *
     * @param s
     */
    protected void addDescendantLine(String s) {
        if (s.length() > 0) {
            char c = s.charAt(0);
            SymbolNode n = ensureChildWithChar(c);
            n.addDescendantLine(s.substring(1));
        }
    }

    /**
     * Show the symbol this node represents.
     *
     * @return the symbol this node represents
     */
    public String ancestry() {
        return parent.ancestry() + myChar;
    }

    /**
     * Find the descendant that takes as many characters as
     * possible from the input.
     *
     * @param r
     *
     * @return
     *
     * @throws IOException
     */
    protected SymbolNode deepestRead(PushbackReader r)
            throws IOException {

        char c = (char) r.read();
        SymbolNode n = findChildWithChar(c);
        if (n == null) {
            r.unread(c);
            return this;
        }
        return n.deepestRead(r);
    }

    /**
     * Find or create a child for the given character.
     *
     * @param c
     *
     * @return
     */
    protected SymbolNode ensureChildWithChar(char c) {
        SymbolNode n = findChildWithChar(c);
        if (n == null) {
            n = new SymbolNode(this, c);
            children.add(n);
        }
        return n;
    }

    /**
     * Find a child with the given character.
     *
     * @param c
     *
     * @return
     */
    protected SymbolNode findChildWithChar(char c) {
        Iterator<SymbolNode> e = children.iterator();
        while (e.hasNext()) {
            SymbolNode n = e.next();
            if (n.myChar == c) {
                return n;
            }
        }
        return null;
    }

    /**
     * Find a descendant which is down the path the given string
     * indicates.
     *
     * @param s
     *
     * @return
     */
    protected SymbolNode findDescendant(String s) {
        char c = s.charAt(0);
        SymbolNode n = findChildWithChar(c);
        if (s.length() == 1) {
            return n;
        }
        return n.findDescendant(s.substring(1));
    }

    /**
     * Mark this node as valid, which means its ancestry is a
     * complete symbol, not just a prefix.
     *
     * @param b
     */
    protected void setValid(boolean b) {
        valid = b;
    }

    /**
     * Give a string representation of this node.
     *
     * @return a string representation of this node
     */
    @Override
    public String toString() {
        return "" + myChar + '(' + valid + ')';
    }

    /*
     * Unwind to a valid node; this node is "valid" if its
     * ancestry represents a complete symbol. If this node is
     * not valid, put back the character and ask the parent to
     * unwind.
     */

    /**
     *
     * @param r
     *
     * @return
     *
     * @throws IOException
     */
    protected SymbolNode unreadToValid(PushbackReader r)
            throws IOException {

        if (valid) {
            return this;
        }
        r.unread(myChar);
        return parent.unreadToValid(r);
    }

    private static final Logger LOG
            = Logger.getLogger(SymbolNode.class.getName());

}
