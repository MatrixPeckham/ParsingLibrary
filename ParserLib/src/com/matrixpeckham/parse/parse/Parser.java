package com.matrixpeckham.parse.parse;

import com.matrixpeckham.parse.utensil.PubliclyCloneable;
import java.util.ArrayList;
import java.util.Iterator;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * A <code>Parser</code> is an object that recognizes the elements of a
 * language.
 * <p>
 * Each <code>Parser</code> object is either a <code>
 * Terminal</code> or a composition of other parsers. The <code>Terminal</code>
 * class is a subclass of <code>
 * Parser</code>, and is itself a hierarchy of parsers that recognize specific
 * patterns of text. For example, a <code>Word</code> recognizes any word, and a
 * <code>Literal</code> matches a specific string.
 * <p>
 * In addition to <code>Terminal</code>, other subclasses of <code>Parser</code>
 * provide composite parsers, describing sequences, alternations, and
 * repetitions of other parsers. For example, the following <code>
 * Parser</code> objects culminate in a <code>good
 * </code> parser that recognizes a description of good coffee.
 *
 * <blockquote><pre>
 *     Alternation adjective = new Alternation();
 *     adjective.add(new Literal("steaming"));
 *     adjective.add(new Literal("hot"));
 *     Sequence good = new Sequence();
 *     good.add(new Repetition(adjective));
 *     good.add(new Literal("coffee"));
 *     String s = "hot hot steaming hot coffee";
 *     Assembly a = new TokenAssembly(s);
 *     System.out.println(good.bestMatch(a));
 * </pre></blockquote>
 *
 * This prints out:
 *
 * <blockquote><pre>
 *     [hot, hot, steaming, hot, coffee]
 *     hot/hot/steaming/hot/coffee^
 * </pre></blockquote>
 *
 * The parser does not match directly against a string, it matches against an
 * <code>Assembly</code>. The resulting assembly shows its stack, with four
 * words on it, along with its sequence of tokens, and the index at the end of
 * these. In practice, parsers will do some work on an assembly, based on the
 * text they recognize.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 * @param <Tok>
 * @param <Val>
 * @param <Tar>
 */
public abstract class Parser<Tok, Val, Tar extends PubliclyCloneable<Tar>>
        implements PubliclyCloneable<Parser<Tok, Val, Tar>> {

    /**
     * Adds the elements of one vector to another.
     *
     * @param <T> type parameter
     * @param v1 the vector to add to
     *
     * @param v2 the vector with elements to add
     */
    public static <T> void add(ArrayList<T> v1, ArrayList<? extends T> v2) {
        Iterator<? extends T> e = v2.iterator();
        while (e.hasNext()) {
            v1.add(e.next());
        }
    }

    /**
     * Create a copy of a vector, cloning each element of the vector.
     *
     *
     * @param <T>
     * @param v the vector to copy
     *
     * @return a copy of the input vector, cloning each element of the vector
     */
    public static <T extends PubliclyCloneable<T>> ArrayList<T> elementClone(
            ArrayList<T> v) {
        ArrayList<T> copy = new ArrayList<>();
        Iterator<T> e = v.iterator();
        while (e.hasNext()) {
            PubliclyCloneable<T> a = e.next();
            copy.add(a.copy());
        }
        return copy;
    }

    protected Assembler<Tok, Val, Tar> assembler;

    /*
     * a name to identify this parser
     */
    protected String name;
    /*
     * an object that will work on an assembly whenever this
     * parser successfully matches against the assembly
     */

    /**
     * Constructs a nameless parser.
     */
    public Parser() {
    }

    protected Parser(Parser<Tok, Val, Tar> p) {
        assembler = p.assembler;
        name = p.name;
    }

    /**
     * Constructs a parser with the given name.
     *
     * @param name A name to be known by. For parsers that are deep composites,
     * a simple name identifying its purpose is useful.
     */
    public Parser(String name) {
        this.name = name;
    }

    /**
     * Accepts a "visitor" which will perform some operation on a parser
     * structure. The book, "Design Patterns", explains the visitor pattern.
     *
     * @param pv the visitor to accept
     */
    public void accept(ParserVisitor<Tok, Val, Tar> pv) {
        accept(pv, new ArrayList<>());
    }

    /**
     * Accepts a "visitor" along with a collection of previously visited
     * parsers.
     *
     * @param pv the visitor to accept
     *
     * @param visited a collection of previously visited parsers.
     */
    public abstract void accept(ParserVisitor<Tok, Val, Tar> pv,
            ArrayList<Parser<Tok, Val, Tar>> visited);

    /**
     * Returns the most-matched assembly in a collection.
     *
     * @return the most-matched assembly in a collection.
     *
     * @param v the collection to look through
     *
     */
    public Assembly<Tok, Val, Tar> best(ArrayList<Assembly<Tok, Val, Tar>> v) {
        Assembly<Tok, Val, Tar> best = null;
        Iterator<Assembly<Tok, Val, Tar>> e = v.iterator();
        while (e.hasNext()) {
            Assembly<Tok, Val, Tar> a = e.next();
            if (!a.hasNext()) {
                return a;
            }
            if (best == null) {
                best = a;
            } else if (a.elementsConsumed() > best.elementsConsumed()) {

                best = a;
            }
        }
        return best;
    }

    /**
     * Returns an assembly with the greatest possible number of elements
     * consumed by matches of this parser.
     *
     * @return an assembly with the greatest possible number of elements
     * consumed by this parser
     *
     * @param a an assembly to match against
     *
     */
    public Assembly<Tok, Val, Tar> bestMatch(Assembly<Tok, Val, Tar> a) {
        ArrayList<Assembly<Tok, Val, Tar>> in = new ArrayList<>();
        in.add(a);
        ArrayList<Assembly<Tok, Val, Tar>> out = matchAndAssemble(in);
        return best(out);
    }

    /**
     * Returns either null, or a completely matched version of the supplied
     * assembly.
     *
     * @return either null, or a completely matched version of the supplied
     * assembly
     *
     * @param a an assembly to match against
     *
     */
    public Assembly<Tok, Val, Tar> completeMatch(Assembly<Tok, Val, Tar> a) {
        Assembly<Tok, Val, Tar> best = bestMatch(a);
        if (best != null && !best.hasNext()) {
            return best;
        }
        return null;
    }

    /**
     * Returns the name of this parser.
     *
     * @return the name of this parser
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Given a set (well, a <code>ArrayList</code>, really) of assemblies, this
     * method matches this parser against all of them, and returns a new set
     * (also really a <code>ArrayList</code>) of the assemblies that result from
     * the matches.
     * <p>
     * For example, consider matching the regular expression <code>a*</code>
     * against the string <code>"aaab"</code>. The initial set of states is
     * <code>{^aaab}</code>, where the ^ indicates how far along the assembly
     * is. When <code>a*</code> matches against this initial state, it creates a
     * new set <code>{^aaab, a^aab, aa^ab,
     * aaa^b}</code>.
     *
     * @return a ArrayList of assemblies that result from matching against a
     * beginning set of assemblies
     *
     * @param in a vector of assemblies to match against
     *
     */
    public abstract ArrayList<Assembly<Tok, Val, Tar>> match(
            ArrayList<Assembly<Tok, Val, Tar>> in);

    /**
     * Match this parser against an input state, and then apply this parser's
     * assembler against the resulting state.
     *
     * @return a ArrayList of assemblies that result from matching against a
     * beginning set of assemblies
     *
     * @param in a vector of assemblies to match against
     *
     */
    public ArrayList<Assembly<Tok, Val, Tar>> matchAndAssemble(
            ArrayList<Assembly<Tok, Val, Tar>> in) {
        ArrayList<Assembly<Tok, Val, Tar>> out = match(in);
        if (assembler != null) {
            Iterator<Assembly<Tok, Val, Tar>> e = out.iterator();
            while (e.hasNext()) {
                assembler.workOn(e.next());
            }
        }
        return out;
    }
    /*
     * Create a random expansion for this parser, where a
     * concatenation of the returned collection will be a
     * language element.
     */

    protected abstract ArrayList<String> randomExpansion(int maxDepth, int depth);

    /**
     * Return a random element of this parser's language.
     *
     * @param maxDepth
     * @param separator
     * @return a random element of this parser's language
     */
    public String randomInput(int maxDepth, String separator) {
        StringBuilder buf = new StringBuilder();
        Iterator<String> e = randomExpansion(maxDepth, 0).iterator();
        boolean first = true;
        while (e.hasNext()) {
            if (!first) {
                buf.append(separator);
            }
            buf.append(e.next());
            first = false;
        }
        return buf.toString();
    }

    /**
     * Sets the object that will work on an assembly whenever this parser
     * successfully matches against the assembly.
     *
     * @param assembler the assembler to apply
     *
     * @return Parser this
     */
    public Parser<Tok, Val, Tar> setAssembler(
            Assembler<Tok, Val, Tar> assembler) {
        this.assembler = assembler;
        return this;
    }

    /**
     * Returns a textual description of this parser.
     *
     * @return String a textual description of this parser, taking care to avoid
     * infinite recursion
     */
    @Override
    public String toString() {
        return toString(new ArrayList<>(), 0);
    }

    /**
     * Returns a textual description of this parser. Parsers can be recursive,
     * so when building a descriptive string, it is important to avoid infinite
     * recursion by keeping track of the objects already described. This method
     * keeps an object from printing twice, and uses
     * <code>unvisitedString</code> which subclasses must implement.
     *
     * @param visited a list of objects already printed
     *
     * @return a textual version of this parser, avoiding recursion
     */
    protected String toString(ArrayList<Parser<Tok, Val, Tar>> visited,
            int level) {
        String indent = "";
        for (int i = 0; i < level; i++) {
            indent += "\t";
        }
        level += 1;
        if (name != null) {
            if (visited.contains(this)) {
                return name;
            } else {
                visited.add(this);
                return name + ":\n" + indent + unvisitedString(visited,
                        level) + "\n" + indent;
            }
        } else if (visited.contains(this)) {
            return "...";
        } else {
            visited.add(this);
            return unvisitedString(visited, level);
        }
    }
    /*
     * Returns a textual description of this string.
     */

    protected abstract String unvisitedString(
            ArrayList<Parser<Tok, Val, Tar>> visited, int level);

}
