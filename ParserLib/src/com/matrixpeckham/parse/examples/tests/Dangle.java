package com.matrixpeckham.parse.examples.tests;

import com.matrixpeckham.parse.parse.Alternation;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Sequence;
import com.matrixpeckham.parse.parse.tokens.Literal;
import com.matrixpeckham.parse.parse.tokens.Num;
import com.matrixpeckham.parse.parse.tokens.Symbol;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.Word;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.logging.Logger;

public class Dangle {

    /**
     *
     */
    static Alternation<Token, NullCloneable, NullCloneable> statement;
    /*
     * Return a parser that recognizes the grammar:
     *
     *     callCustomer = "callCustomer" '(' ')' ';';
     */

    /**
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
    /*
     * Return a parser that recognizes the grammar:
     *
     *     comparison   = '(' expression operator expression ')';
     */

    /**
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
    /*
     * Return a parser that recognizes the grammar:
     *
     *     expression   = Word | Num;
     */

    /**
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
    /*
     * Return a parser that recognizes the grammar:
     *
     *     ifelse = "if" comparison statement "else" statement;
     */

    /**
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
    /*
     * Return a parser that recognizes the grammar:
     *
     *     iff = "if" comparison statement;
     */

    /**
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
    /*
     * Return a parser that recognizes the grammar:
     *
     *     operator     = '<' | '>' | '=' | "<=" | ">=" | "!=";
     */

    /**
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
    /*
     * Return a parser that recognizes the grammar:
     *
     *     sendBill     = "sendBill" '('')' ';';
     */

    /**
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
     *
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
