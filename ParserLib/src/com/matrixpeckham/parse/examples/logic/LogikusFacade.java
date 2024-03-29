package com.matrixpeckham.parse.examples.logic;

import static java.lang.Character.isUpperCase;

import com.matrixpeckham.parse.engine.*;
import com.matrixpeckham.parse.examples.query.QueryBuilder;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.tokens.*;
import com.matrixpeckham.parse.utensil.TypeOrType;
import java.util.logging.Logger;

/**
 * This class provides utility methods that simplify the use
 * of the Logikus parser.
 */
public class LogikusFacade {

    /**
     * Translate one axiom string into an Axiom object.
     *
     * @param s
     *
     * @return
     */
    public static Axiom axiom(String s) {
        return axiom(new TokenString(s));
    }

    /**
     * Translate the tokens for one axiom into an Axiom
     * object (either a Fact or a Rule);
     *
     * @param ts
     *
     * @return
     */
    protected static Axiom axiom(TokenString ts) {
        Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> p
                = new LogikusParser().axiom();
        Object o = parse(ts, p, "axiom");
        if (o instanceof Axiom) {
            return (Axiom) o;
        } else {
            throw new LogikusException("Axiom not found");
        }
    }

    /**
     * Throws an informative runtime exception if the provided
     * string begins with an uppercase letter.
     *
     * @param ts
     * @param type
     */
    protected static void checkForUppercase(
            TokenString ts, String type) {

        if (ts.length() > 0) {
            Token t = ts.tokenAt(0);
            String s = t.sval();
            if (s.length() > 0 && isUpperCase(s.charAt(0))) {

                throw new LogikusException(
                        "> Uppercase " + s
                        + " indicates a variable and cannot begin a " + type
                        + ".\n");
            }
        }
    }

    /**
     * Parse the given token string with the given parser,
     * throwing runtime exceptions if parsing fails
     * or is incomplete.
     *
     * @param ts
     * @param p
     * @param type
     *
     * @return
     */
    protected static Object parse(
            TokenString ts,
            Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> p, String type) {

        TokenAssembly<TypeOrType<Axiom, Term>, QueryBuilder> ta
                = new TokenAssembly<>(ts);
        Assembly<Token, TypeOrType<Axiom, Term>, QueryBuilder> out = p.
                bestMatch(ta);
        if (out == null) {
            reportNoMatch(ts, type);
            return null;//we will never excecute this because report throws an exception
        }
        if (out.hasNext()) {
            // allow an extra semicolon
            if (!out.remainder("").equals(";")) {
                reportLeftovers(out, type);
            }
        }
        return out.popVal().asObject();
    }

    /**
     * Parse the text of a Logikus program and return a <code>Program</code>
     * object.
     *
     * @param s the text of the program
     *
     * @return a <code>Program</code> object
     *
     * @exception RuntimeException if parsing fails
     */
    public static Program program(String s) {
        Program p = new Program();
        TokenStringSource tss = new TokenStringSource(
                new Tokenizer(s), ";");
        while (true) {
            TokenString ts = tss.nextTokenString();
            if (ts == null) { // no more token strings
                break;
            }
            p.addAxiom(axiom(ts));
        }
        return p;
    }

    /**
     * Parse the text of a Logikus query and return a <code>Query</code> object.
     *
     * @param s
     * @param as the text of the query
     *
     *
     * @return a <code>Query</code> object
     *
     * @exception RuntimeException if parsing fails
     */
    public static Query query(String s, AxiomSource as) {
        Object o = parse(
                new TokenString(s), LogikusParser.query(), "query");
        if (o instanceof Fact) {
            Fact f = (Fact) o;
            return new Query(as, f);
        }
        return new Query(as, (Rule) o);
    }

    /**
     * Throws a runtime exception reporting an incomplete
     * parse.
     *
     * @param out
     * @param type
     *
     * @return
     */
    protected static Object reportLeftovers(
            Assembly<Token, TypeOrType<Axiom, Term>, QueryBuilder> out,
            String type) {

        throw new LogikusException(
                "> Input for " + type + " appears complete after : \n> " + out.
                        consumed(" ") + "\n");
    }

    /*
     * Throws a runtime exception reporting failed parse.
     */

    /**
     *
     * @param ts
     * @param type
     */
    protected static void reportNoMatch(
            TokenString ts, String type) {

        checkForUppercase(ts, type);
        throw new LogikusException(
                "> Cannot parse " + type + " : " + ts + "\n");
    }

    private static final Logger LOG
            = Logger.getLogger(LogikusFacade.class.getName());

}
