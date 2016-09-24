package com.matrixpeckham.parse.examples.query;

import com.matrixpeckham.parse.engine.Axiom;
import com.matrixpeckham.parse.engine.Term;
import com.matrixpeckham.parse.examples.track.Track;
import com.matrixpeckham.parse.parse.Alternation;
import com.matrixpeckham.parse.parse.Empty;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Repetition;
import com.matrixpeckham.parse.parse.Sequence;
import com.matrixpeckham.parse.parse.tokens.CaselessLiteral;
import com.matrixpeckham.parse.parse.tokens.Symbol;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.Word;
import com.matrixpeckham.parse.utensil.TypeOrType;
import java.util.logging.Logger;

public class JaqlParser {

    /**
     *
     */
    protected Speller speller;

    /**
     *
     */
    static ComparisonParser comparisonParser;

    /**
     * Construct a query language parser that will use the given speller to find
     * the proper spelling of class and variable names.
     *
     * @param speller
     */
    public JaqlParser(Speller speller) {
        this.speller = speller;
    }
    /*
     * Recognize a class name.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> className() {
        return new Word<TypeOrType<Axiom, Term>, QueryBuilder>().setAssembler(
                new ClassNameAssembler());
    }
    /*
     * Recognize a sequence of class names separated by commas.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> classNames() {
        return commaList(className());
    }
    /*
     * Using the given parser, this method composes a new
     * parser with the grammar:
     *
     *     commaList(p) = p (',' p)*;
     *
     * The Jaql language uses this construction several
     * times.
     */

    /**
     *
     * @param p
     * @return
     */
    protected static Sequence<Token, TypeOrType<Axiom, Term>, QueryBuilder> commaList(
            Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> p) {
        Sequence<Token, TypeOrType<Axiom, Term>, QueryBuilder> commaP
                = new Track<>();
        commaP.add(new Symbol<TypeOrType<Axiom, Term>, QueryBuilder>(',').
                discard());
        commaP.add(p);

        Sequence<Token, TypeOrType<Axiom, Term>, QueryBuilder> s
                = new Sequence<>();
        s.add(p);
        s.add(new Repetition<>(commaP));
        return s;
    }
    /*
     * Recognize a comparison -- just use <code>comparison
     * </code> from <code>ComparisonParser</code>.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> comparison() {
        return comparisonParser().comparison();
    }

    /**
     * Return the parser to use for expression and comparison subparsers.
     *
     * @return
     */
    public synchronized ComparisonParser comparisonParser() {
        if (comparisonParser == null) {
            comparisonParser = new ComparisonParser(speller);
        }
        return comparisonParser;
    }
    /*
     * Recognize a comma-separated sequence of comparisons.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> comparisons() {
        return commaList(comparison());
    }
    /*
     * Recognize either nothing or a where clause.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> optionalWhere() {
        Alternation<Token, TypeOrType<Axiom, Term>, QueryBuilder> a
                = new Alternation<>();
        a.add(new Empty<>());
        a.add(where());
        return a;
    }

    /**
     * Returns a parser that will recognize a select statement.
     *
     * @return a parser that will recognize a select statement.
     */
    public Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> select() {
        Sequence<Token, TypeOrType<Axiom, Term>, QueryBuilder> s = new Track<>();
        s.add(new CaselessLiteral<TypeOrType<Axiom, Term>, QueryBuilder>(
                "select").discard());
        s.add(selectTerms());
        s.
                add(new CaselessLiteral<TypeOrType<Axiom, Term>, QueryBuilder>(
                                "from").discard());
        s.add(classNames());
        s.add(optionalWhere());
        return s;
    }
    /*
     * Recognize a select term, which can be any valid
     * expression.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> selectTerm() {
        // wrap expression so we can add an assembler
        Sequence<Token, TypeOrType<Axiom, Term>, QueryBuilder> s
                = new Sequence<>("selectTerm");
        s.add(comparisonParser().expression());
        s.setAssembler(new SelectTermAssembler());
        return s;
    }
    /*
     * Recognize a comma-separated sequence of select terms.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> selectTerms() {
        return commaList(selectTerm());
    }

    /**
     * Returns a parser that will recognize a select statement.
     *
     * @return a parser that will recognize a select statement.
     */
    public Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> start() {
        return select();
    }
    /*
     * Recognize a where clause, which is "where" followed by
     * a comma-separated list of comparisons.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> where() {
        Sequence<Token, TypeOrType<Axiom, Term>, QueryBuilder> s
                = new Sequence<>();
        s.
                add(new CaselessLiteral<TypeOrType<Axiom, Term>, QueryBuilder>(
                                "where").discard());
        s.add(comparisons());
        return s;
    }

    private static final Logger LOG
            = Logger.getLogger(JaqlParser.class.getName());

}
