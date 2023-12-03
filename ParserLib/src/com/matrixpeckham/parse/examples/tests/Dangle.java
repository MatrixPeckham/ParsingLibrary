package com.matrixpeckham.parse.examples.tests;

import com.matrixpeckham.parse.parse.*;
import com.matrixpeckham.parse.parse.tokens.*;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.logging.Logger;

/**
 * This class provides an ambiguous parser in its <code>
 * statement</code> method, which serves to show that
 * the test classes can find ambiguity.
 * <p>
 * The grammar this class supports is:
 * <blockquote><pre>
 *
 *     statement     = iff | ifelse | callCustomer | sendBill;
 *     iff           = "if" comparison statement;
 *     ifelse        = "if" comparison statement
 *                     "else" statement;
 *     comparison    = '(' expression operator expression ')';
 *     expression    = Word | Num;
 *     operator      = '&lt;' | '&gt;' | '=' | "&lt;=" | "&gt;=" | "!=";
 *     optionalElse  = "else" statement | Empty;
 *     callCustomer  = "callCustomer" '('')' ';';
 *     sendBill      = "sendBill" '('')' ';';
 * </pre></blockquote>
 */
public class Dangle {

    /**
     *
     */
    static Alternation<Token, NullCloneable, NullCloneable> statement;

    /**
     * Return a parser that recognizes the grammar:
     * <p>
     * callCustomer = "callCustomer" '(' ')' ';';
     *
     * @return
     */
    public static Parser<Token, NullCloneable, NullCloneable> callCustomer() {
        Sequence<Token, NullCloneable, NullCloneable> s = new Sequence<>(
                "<callCustomer>");
        s.add(new Literal<>("callCustomer"));
        s.add(new Symbol<>('('));
        s.add(new Symbol<>(')'));
        s.add(new Symbol<>(';'));
        return s;
    }

    /**
     * Return a parser that recognizes the grammar:
     * <p>
     * comparison = '(' expression operator expression ')';
     *
     * @return
     */
    public static Parser<Token, NullCloneable, NullCloneable> comparison() {
        Sequence<Token, NullCloneable, NullCloneable> s = new Sequence<>(
                "<comparison>");
        s.add(new Symbol<>('('));
        s.add(expression());
        s.add(operator());
        s.add(expression());
        s.add(new Symbol<>(')'));
        return s;
    }

    /**
     * Return a parser that recognizes the grammar:
     * <p>
     * expression = Word | Num;
     *
     * @return
     */
    public static Parser<Token, NullCloneable, NullCloneable> expression() {
        Alternation<Token, NullCloneable, NullCloneable> a = new Alternation<>(
                "<expression>");
        a.add(new Word<>());
        a.add(new Num<>());
        return a;
    }

    /**
     * Return a parser that recognizes the grammar:
     * <p>
     * ifelse = "if" comparison statement "else" statement;
     *
     * @return
     */
    public static Parser<Token, NullCloneable, NullCloneable> ifelse() {
        Sequence<Token, NullCloneable, NullCloneable> s = new Sequence<>(
                "<ifelse>");
        s.add(new Literal<>("if"));
        s.add(comparison());
        s.add(statement());
        s.add(new Literal<>("else"));
        s.add(statement());
        return s;
    }

    /**
     * Return a parser that recognizes the grammar:
     * <p>
     * iff = "if" comparison statement;
     *
     * @return
     */
    public static Parser<Token, NullCloneable, NullCloneable> iff() {
        Sequence<Token, NullCloneable, NullCloneable> s
                = new Sequence<>("<iff>");
        s.add(new Literal<>("if"));
        s.add(comparison());
        s.add(statement());
        return s;
    }

    /**
     * Return a parser that recognizes the grammar:
     * <p>
     * operator = '<' | '>' | '=' | "<=" | ">=" | "!=";
     *
     * @return
     */
    public static Parser<Token, NullCloneable, NullCloneable> operator() {
        Alternation<Token, NullCloneable, NullCloneable> a = new Alternation<>(
                "<operator>");
        a.add(new Symbol<>('<'));
        a.add(new Symbol<>('>'));
        a.add(new Symbol<>('='));
        a.add(new Symbol<>("<="));
        a.add(new Symbol<>(">="));
        a.add(new Symbol<>("!="));
        return a;
    }

    /**
     * Return a parser that recognizes the grammar:
     * <p>
     * sendBill = "sendBill" '('')' ';';
     *
     * @return
     */
    public static Parser<Token, NullCloneable, NullCloneable> sendBill() {
        Sequence<Token, NullCloneable, NullCloneable> s = new Sequence<>(
                "<sendBill>");
        s.add(new Literal<>("sendBill"));
        s.add(new Symbol<>('('));
        s.add(new Symbol<>(')'));
        s.add(new Symbol<>(';'));
        return s;
    }

    /**
     * Return a parser that recognizes the grammar:
     * <p>
     * <blockquote><pre>
     *     statement    = "if" comparison statement optionalElse |
     *                     callCustomer | sendBill;
     * </pre></blockquote>
     *
     *
     * @return a parser that recognizes a statement
     */
    public synchronized static Parser<Token, NullCloneable, NullCloneable> statement() {
        if (statement == null) {
            statement = new Alternation<>("<statement>");
            statement.add(iff());
            statement.add(ifelse());
            statement.add(callCustomer());
            statement.add(sendBill());
        }
        return statement;
    }

    private static final Logger LOG = Logger.getLogger(Dangle.class.getName());

}
