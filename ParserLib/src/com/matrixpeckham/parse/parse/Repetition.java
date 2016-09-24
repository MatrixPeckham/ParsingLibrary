package com.matrixpeckham.parse.parse;

import com.matrixpeckham.parse.utensil.PubliclyCloneable;
import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 *
 * @author Owner
 * @param <Tok>
 * @param <Val>
 * @param <Tar>
 */
public class Repetition<Tok, Val, Tar extends PubliclyCloneable<Tar>>
        extends Parser<Tok, Val, Tar> {
    /*
     * the parser this parser is a repetition of
     */

    /**
     *
     */
    protected Parser<Tok, Val, Tar> subparser;

    /*
     * the width of a random expansion
     */
    /**
     *
     */
    protected static final int EXPWIDTH = 4;

    /*
     * an assembler to apply at the beginning of a match
     */
    /**
     *
     */
    protected Assembler<Tok, Val, Tar> preAssembler;

    /**
     * Constructs a repetition of the given parser.
     *
     * @param p the parser to repeat
     */
    public Repetition(Parser<Tok, Val, Tar> p) {
        this(p, null);
    }

    /**
     * Constructs a repetition of the given parser with the given name.
     *
     * @param subparser the parser to repeat
     *
     * @param name a name to be known by
     */
    public Repetition(Parser<Tok, Val, Tar> subparser, String name) {
        super(name);
        this.subparser = subparser;
    }

    /**
     * Accept a "visitor" and a collection of previously visited parsers.
     *
     * @param pv the visitor to accept
     *
     * @param visited a collection of previously visited parsers
     */
    @Override
    public void accept(ParserVisitor<Tok, Val, Tar> pv,
            ArrayList<Parser<Tok, Val, Tar>> visited) {
        pv.visitRepetition(this, visited);
    }

    @Override
    public Parser<Tok, Val, Tar> copy() {
        Repetition<Tok, Val, Tar> r = new Repetition<>(subparser.copy(), name);
        r.preAssembler = preAssembler;
        r.assembler = assembler;
        return r;
    }

    /**
     * Return this parser's subparser.
     *
     * @return Parser this parser's subparser
     */
    public Parser<Tok, Val, Tar> getSubparser() {
        return subparser;
    }

    /**
     * Given a set of assemblies, this method applies a preassembler to all of
     * them, matches its subparser repeatedly against each of them, applies its
     * post-assembler against each, and returns a new set of the assemblies that
     * result from the matches.
     * <p>
     * For example, matching the regular expression <code>a*
     * </code> against <code>{^aaab}</code> results in <code>
     * {^aaab, a^aab, aa^ab, aaa^b}</code>.
     *
     * @return a ArrayList of assemblies that result from matching against a
     * beginning set of assemblies
     *
     * @param in a vector of assemblies to match against
     *
     */
    @Override
    public ArrayList<Assembly<Tok, Val, Tar>> match(
            ArrayList<Assembly<Tok, Val, Tar>> in) {
        if (preAssembler != null) {
            Iterator<Assembly<Tok, Val, Tar>> e = in.iterator();
            while (e.hasNext()) {
                preAssembler.workOn(e.next());
            }
        }
        ArrayList<Assembly<Tok, Val, Tar>> out = elementClone(in);
        ArrayList<Assembly<Tok, Val, Tar>> s = in; // a working state
        while (!s.isEmpty()) {
            s = subparser.matchAndAssemble(s);
            add(out, s);
        }
        return out;
    }

    /**
     * Create a collection of random elements that correspond to this
     * repetition.
     *
     * @param maxDepth
     * @param depth
     * @return
     */
    @Override
    protected ArrayList<String> randomExpansion(int maxDepth, int depth) {
        ArrayList<String> v = new ArrayList<>();
        if (depth >= maxDepth) {
            return v;
        }

        int n = (int) (EXPWIDTH * random());
        for (int j = 0; j < n; j++) {
            ArrayList<String> w = subparser.randomExpansion(maxDepth, depth++);
            Iterator<String> e = w.iterator();
            while (e.hasNext()) {
                v.add(e.next());
            }
        }
        return v;
    }

    /**
     * Sets the object that will work on every assembly before matching against
     * it.
     *
     * @param preAssembler the assembler to apply
     *
     * @return Parser this
     */
    public Parser<Tok, Val, Tar> setPreAssembler(
            Assembler<Tok, Val, Tar> preAssembler) {
        this.preAssembler = preAssembler;
        return this;
    }
    /*
     * Returns a textual description of this parser.
     */

    /**
     *
     * @param visited
     * @return
     */
    @Override
    protected String unvisitedString(ArrayList<Parser<Tok, Val, Tar>> visited,
            int level) {
        return subparser.toString(visited, level) + "*";
    }

    private static final Logger LOG
            = Logger.getLogger(Repetition.class.getName());

}
