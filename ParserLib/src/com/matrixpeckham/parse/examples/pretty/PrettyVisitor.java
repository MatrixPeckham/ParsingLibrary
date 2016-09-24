package com.matrixpeckham.parse.examples.pretty;

import com.matrixpeckham.parse.parse.Alternation;
import com.matrixpeckham.parse.parse.Empty;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.ParserVisitor;
import com.matrixpeckham.parse.parse.Repetition;
import com.matrixpeckham.parse.parse.Sequence;
import com.matrixpeckham.parse.parse.Terminal;
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
 * An object of this class visits the parsers in a parser composite and sets
 * each subparser's assembler to be one of the "pretty" assemblers in this
 * package. These assemblers build a tree of nodes from the
 * <code>ComponentNode</code> hierarchy that is also in this package. The
 * resulting tree effectively records the order in which the parse proceeds.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class PrettyVisitor extends ParserVisitor {

    /**
     * Set an <code>Alternation</code> parser's assembler to be a
     * <code>PrettyAlternationAssembler</code> object and visit this parser's
     * children.
     *
     * @param visited
     */
    @Override
    public void visitAlternation(Alternation a, ArrayList visited) {
        if (visited.contains(a)) {
            return;
        }
        visited.add(a);
        a.setAssembler(
                new PrettyAlternationAssembler(a.getName()));

        Iterator e = a.getSubparsers().iterator();
        while (e.hasNext()) {
            Parser child = (Parser) e.next();
            child.accept(this, visited);
        }
    }

    /**
     * Set an <code>Empty</code> parser's assembler to be a
     * <code>PrettyEmptyAssembler</code> object.
     *
     * @param visited
     */
    @Override
    public void visitEmpty(Empty e, ArrayList visited) {
        e.setAssembler(new PrettyEmptyAssembler());
    }

    /**
     * Set a <code>Repetition</code> parser's pre-assembler to push a "fence",
     * and set the parser's post-assembler to be a
     * <code>PrettyRepetitionAssembler</code> object. The latter assembler will
     * pop results down to the fence. Also visit the repetition parser's
     * subparser.
     *
     * @param visited
     */
    @Override
    public void visitRepetition(Repetition r, ArrayList visited) {
        if (visited.contains(r)) {
            return;
        }
        visited.add(r);
        Object fence = new Object();
        r.setPreAssembler(new FenceAssembler(fence));
        r.setAssembler(
                new PrettyRepetitionAssembler(r.getName(), fence));
        r.getSubparser().accept(this, visited);
    }

    /**
     * Set a <code>Sequence</code> parser's assembler to be a
     * <code>PrettySequenceAssembler</code> object and visit the parser's
     * children.
     *
     * @param visited
     */
    @Override
    public void visitSequence(Sequence s, ArrayList visited) {
        if (visited.contains(s)) {
            return;
        }
        visited.add(s);
        s.setAssembler(
                new PrettySequenceAssembler(
                        s.getName(), s.getSubparsers().size()));
        Iterator e = s.getSubparsers().iterator();
        while (e.hasNext()) {
            Parser child = (Parser) e.next();
            child.accept(this, visited);
        }
    }

    /**
     * Set a <code>Terminal</code> object's assembler to be a
     * <code>PrettyTerminalAssembler</code> object.
     *
     * @param visited
     */
    @Override
    public void visitTerminal(Terminal t, ArrayList visited) {
        t.setAssembler(new PrettyTerminalAssembler());
    }

    private static final Logger LOG
            = Logger.getLogger(PrettyVisitor.class.getName());

}
