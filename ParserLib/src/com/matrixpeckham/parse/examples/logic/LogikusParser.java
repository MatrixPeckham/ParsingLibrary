package com.matrixpeckham.parse.examples.logic;

import com.matrixpeckham.parse.engine.Axiom;
import com.matrixpeckham.parse.engine.Term;
import com.matrixpeckham.parse.examples.mechanics.LowercaseWord;
import com.matrixpeckham.parse.examples.mechanics.UppercaseWord;
import com.matrixpeckham.parse.examples.query.QueryBuilder;
import com.matrixpeckham.parse.examples.track.Track;
import com.matrixpeckham.parse.parse.Alternation;
import com.matrixpeckham.parse.parse.Empty;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Repetition;
import com.matrixpeckham.parse.parse.Sequence;
import com.matrixpeckham.parse.parse.tokens.Literal;
import com.matrixpeckham.parse.parse.tokens.Num;
import com.matrixpeckham.parse.parse.tokens.QuotedString;
import com.matrixpeckham.parse.parse.tokens.Symbol;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.utensil.PubliclyCloneable;
import com.matrixpeckham.parse.utensil.TypeOrType;
import java.util.logging.Logger;

public class LogikusParser {

    /**
     *
     */
    protected Sequence<Token, TypeOrType<Axiom, Term>, QueryBuilder> structure;

    /**
     *
     */
    protected Sequence<Token, TypeOrType<Axiom, Term>, QueryBuilder> expression;

    /**
     *
     */
    protected Sequence<Token, TypeOrType<Axiom, Term>, QueryBuilder> list;
    /*
     * Return a parser that recognizes the grammar:
     *
     *    arg = expression | functor;
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> arg() {
        Alternation<Token, TypeOrType<Axiom, Term>, QueryBuilder> a
                = new Alternation<>();
        a.add(expression());
        a.add(functor().setAssembler(new AtomAssembler()));
        return a;
    }

    /**
     * Return a parser that recognizes the grammar:
     *
     * <blockquote><pre>
     *    axiom = structure (ruleDef | Empty);
     * </pre></blockquote>
     *
     * @return a parser that recognizes an axiom
     */
    public Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> axiom() {
        Sequence<Token, TypeOrType<Axiom, Term>, QueryBuilder> s
                = new Sequence<>("axiom");

        s.add(structure());
        Alternation<Token, TypeOrType<Axiom, Term>, QueryBuilder> a
                = new Alternation<>();
        a.add(ruleDef());
        a.add(new Empty<>());
        s.add(a);

        s.setAssembler(new AxiomAssembler());
        return s;
    }
    /*
     * Using the given parser, this method composes a new
     * parser with the grammar:
     *
     *     commaList(p) = p (',' p)*;
     *
     * The Logikus language uses this construction several
     * times.
     */

    /**
     *
     * @param <Val>
     * @param <Tar>
     * @param p
     * @return
     */
    protected static <Val, Tar extends PubliclyCloneable<Tar>> Sequence<Token, Val, Tar> commaList(
            Parser<Token, Val, Tar> p) {
        Sequence<Token, Val, Tar> commaP = new Track<>();
        commaP.add(new Symbol<Val, Tar>(',').discard());
        commaP.add(p);

        Sequence<Token, Val, Tar> s = new Sequence<>();
        s.add(p);
        s.add(new Repetition<>(commaP));
        return s;
    }

    /**
     * Return a parser that recognizes the grammar:
     *
     * <blockquote><pre>
     *    comparison = operator '(' arg ',' arg ')';
     * </pre></blockquote>
     *
     * @return a parser that recognizes a comparison
     */
    public Sequence<Token, TypeOrType<Axiom, Term>, QueryBuilder> comparison() {
        Track<Token, TypeOrType<Axiom, Term>, QueryBuilder> t = new Track<>(
                "comparison");
        t.add(operator());
        t.add(new Symbol<TypeOrType<Axiom, Term>, QueryBuilder>('(').discard());
        t.add(arg());
        t.add(new Symbol<TypeOrType<Axiom, Term>, QueryBuilder>(',').discard());
        t.add(arg());
        t.add(new Symbol<TypeOrType<Axiom, Term>, QueryBuilder>(')').discard());
        t.setAssembler(new ComparisonAssembler());
        return t;
    }

    /**
     * Return a parser that recognizes the grammar:
     *
     * <blockquote><pre>
     *    condition = structure | not | evaluation | comparison |
     *                list;
     * </pre></blockquote>
     *
     * @return a parser that recognizes a condition
     */
    public Alternation<Token, TypeOrType<Axiom, Term>, QueryBuilder> condition() {
        Alternation<Token, TypeOrType<Axiom, Term>, QueryBuilder> a
                = new Alternation<>("condition");
        a.add(structure());
        a.add(not());
        a.add(evaluation());
        a.add(comparison());
        a.add(list());
        return a;
    }
    /*
     * Return a parser that recognizes the grammar:
     *
     *    divideFactor = '/' factor;
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> divideFactor() {
        Sequence<Token, TypeOrType<Axiom, Term>, QueryBuilder> s
                = new Sequence<>("divideFactor");
        s.add(new Symbol<TypeOrType<Axiom, Term>, QueryBuilder>('/').discard());
        s.add(factor());
        s.setAssembler(new ArithmeticAssembler('/'));
        return s;
    }
    /*
     * Return a parser that recognizes the grammar:
     *
     *     evaluation = '#' '(' arg ',' arg ')';
     *
     * For example, this parser will recognize
     * "#(X, 12321/111)", translating it to an Evaluation
     * object. When asked to prove itself, the Evaluation
     * object will unify its first term with the value of
     * its second term.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> evaluation() {

        Track<Token, TypeOrType<Axiom, Term>, QueryBuilder> t = new Track<>(
                "evaluation");
        t.add(new Symbol<TypeOrType<Axiom, Term>, QueryBuilder>('#').discard());
        t.add(new Symbol<TypeOrType<Axiom, Term>, QueryBuilder>('(').discard());
        t.add(arg());
        t.add(new Symbol<TypeOrType<Axiom, Term>, QueryBuilder>(',').discard());
        t.add(arg());
        t.add(new Symbol<TypeOrType<Axiom, Term>, QueryBuilder>(')').discard());
        t.setAssembler(new EvaluationAssembler());
        return t;
    }
    /*
     * Return a parser that recognizes the grammar:
     *
     *    expression = phrase ('+' phrase | '-' phrase)*;
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> expression() {
        /*
         * This use of a static variable avoids the infinite
         * recursion inherent in the language definition.
         */
        if (expression == null) {
            expression = new Sequence<>("expression");
            expression.add(phrase());
            Alternation<Token, TypeOrType<Axiom, Term>, QueryBuilder> a
                    = new Alternation<>();
            a.add(plusPhrase());
            a.add(minusPhrase());
            expression.add(new Repetition<>(a));
        }
        return expression;
    }
    /*
     * Return a parser that recognizes the grammar:
     *
     *    factor = '(' expression ')' | Num | variable;
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> factor() {
        Alternation<Token, TypeOrType<Axiom, Term>, QueryBuilder> a
                = new Alternation<>("factor");
        Sequence<Token, TypeOrType<Axiom, Term>, QueryBuilder> s
                = new Sequence<>();
        s.add(new Symbol<TypeOrType<Axiom, Term>, QueryBuilder>('(').discard());
        s.add(expression());
        s.add(new Symbol<TypeOrType<Axiom, Term>, QueryBuilder>(')').discard());
        a.add(s);
        a.add(num());
        a.add(variable());
        return a;
    }
    /*
     * Return a parser that recognizes the grammar:
     *
     *    functor = '.' | LowercaseWord | QuotedString;
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> functor() {
        Alternation<Token, TypeOrType<Axiom, Term>, QueryBuilder> a
                = new Alternation<>("functor");
        a.add(new Symbol<>('.'));
        a.add(new LowercaseWord<>());
        a.add(new QuotedString<>());
        return a;
    }

    /**
     * Return a parser that recognizes the grammar:
     *
     * <blockquote><pre>
     *    list = '[' (listContents | Empty) ']';
     * </pre></blockquote>
     *
     * The class comment gives the complete grammar for lists, as part of the
     * Logikus grammar.
     *
     * @return a parser that recognizes a list
     */
    public Sequence<Token, TypeOrType<Axiom, Term>, QueryBuilder> list() {
        if (list == null) {
            list = new Track<>("list");
            list.add(new Symbol<>('[')); // push this, as a fence

            Alternation<Token, TypeOrType<Axiom, Term>, QueryBuilder> a
                    = new Alternation<>();
            a.add(listContents());
            a.add(new Empty<Token, TypeOrType<Axiom, Term>, QueryBuilder>().
                    setAssembler(
                            new ListAssembler()));

            list.add(a);
            list.add(new Symbol<TypeOrType<Axiom, Term>, QueryBuilder>(']').
                    discard());
        }
        return list;
    }
    /*
     * Return a parser that recognizes the grammar:
     *
     *    listContents = commaList(term) listTail;
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> listContents() {
        Sequence<Token, TypeOrType<Axiom, Term>, QueryBuilder> s = commaList(
                term());
        s.add(listTail());
        return s;
    }
    /*
     * Return a parser that recognizes the grammar:
     *
     *    listTail = ('|' (variable | list)) | Empty;
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> listTail() {
        Alternation<Token, TypeOrType<Axiom, Term>, QueryBuilder> tail
                = new Alternation<>();
        tail.add(variable());
        tail.add(list());

        Track<Token, TypeOrType<Axiom, Term>, QueryBuilder> barTail
                = new Track<>("bar tail");
        barTail.add(new Symbol<TypeOrType<Axiom, Term>, QueryBuilder>('|').
                discard());
        barTail.add(tail);
        barTail.setAssembler(new ListWithTailAssembler());

        Alternation<Token, TypeOrType<Axiom, Term>, QueryBuilder> a
                = new Alternation<>();
        a.add(barTail);
        a.add(new Empty<Token, TypeOrType<Axiom, Term>, QueryBuilder>().
                setAssembler(new ListAssembler()));
        return a;
    }
    /*
     * Return a parser that recognizes the grammar:
     *
     *    minusPhrase = '-' phrase;
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> minusPhrase() {
        Sequence<Token, TypeOrType<Axiom, Term>, QueryBuilder> s
                = new Sequence<>("minusPhrase");
        s.add(new Symbol<TypeOrType<Axiom, Term>, QueryBuilder>('-').discard());
        s.add(phrase());
        s.setAssembler(new ArithmeticAssembler('-'));
        return s;
    }
    /*
     * Return a parser that recognizes the grammar:
     *
     *     not = "not" structure;
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> not() {
        Track<Token, TypeOrType<Axiom, Term>, QueryBuilder> t = new Track<>(
                "not");
        t.add(new Literal<TypeOrType<Axiom, Term>, QueryBuilder>("not").
                discard());
        t.add(structure());
        t.setAssembler(new NotAssembler());
        return t;
    }
    /*
     * Return a parser that recognizes a number and
     * stacks a corresponding atom.
     */

    /**
     *
     * @return
     */
    public Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> num() {
        Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> n = new Num<>();
        n.setAssembler(new AtomAssembler());
        return n;
    }
    /*
     * Return a parser that recognizes the grammar:
     *
     *     operator = '<' | '>' | '=' | "<=" | ">=" | "!=" ;
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> operator() {
        Alternation<Token, TypeOrType<Axiom, Term>, QueryBuilder> a
                = new Alternation<>("operator");
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
     *    phrase = factor ('*' factor | '/' factor)*;
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> phrase() {
        Sequence<Token, TypeOrType<Axiom, Term>, QueryBuilder> phrase
                = new Sequence<>("phrase");
        phrase.add(factor());
        Alternation<Token, TypeOrType<Axiom, Term>, QueryBuilder> a
                = new Alternation<>();
        a.add(timesFactor());
        a.add(divideFactor());
        phrase.add(new Repetition<>(a));
        return phrase;
    }
    /*
     * Return a parser that recognizes the grammar:
     *
     *    plusPhrase = '+' phrase;
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> plusPhrase() {
        Sequence<Token, TypeOrType<Axiom, Term>, QueryBuilder> s
                = new Sequence<>("plusPhrase");
        s.add(new Symbol<TypeOrType<Axiom, Term>, QueryBuilder>('+').discard());
        s.add(phrase());
        s.setAssembler(new ArithmeticAssembler('+'));
        return s;
    }

    /**
     * Return a parser that recognizes the grammar:
     *
     * <blockquote><pre>
     *    query = commaList(condition);
     * </pre></blockquote>
     *
     * @return a parser that recognizes a query
     */
    public static Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> query() {
        Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> p = commaList(
                new LogikusParser().condition());
        p.setAssembler(new AxiomAssembler());
        return p;
    }
    /*
     * Return a parser that recognizes the grammar:
     *
     *    ruleDef = ":-" commaList(condition);
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> ruleDef() {
        Track<Token, TypeOrType<Axiom, Term>, QueryBuilder> t = new Track<>(
                "rule definition");
        t.
                add(new Symbol<TypeOrType<Axiom, Term>, QueryBuilder>(":-").
                        discard());
        t.add(commaList(condition()));
        return t;
    }

    /**
     * Return a parser that recognizes the grammar:
     *
     * <blockquote><pre>
     *    axiom = condition (ruleDefinition | empty);
     * </pre></blockquote>
     *
     * @return a parser that recognizes an axiom
     */
    public static Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> start() {
        return new LogikusParser().axiom();
    }
    /*
     * Return a parser that recognizes the grammar:
     *
     *    structure = functor ( '(' commaList(term) ')' | Empty);
     *
     * This definition of structure accounts for normal-looking
     * structures that have a string as a functor. Strictly
     * speaking, numbers and lists are also structures. The
     * definition for <code>term</code> includes these.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> structure() {
        if (structure == null) {
            structure = new Sequence<>("structure");

            structure.add(functor());

            Track<Token, TypeOrType<Axiom, Term>, QueryBuilder> t
                    = new Track<>("list in parens");
            t.add(new Symbol<>('(')); // push this as a fence
            t.add(commaList(term()));
            t.add(new Symbol<TypeOrType<Axiom, Term>, QueryBuilder>(')').
                    discard());

            Alternation<Token, TypeOrType<Axiom, Term>, QueryBuilder> a
                    = new Alternation<>();
            a.add(
                    t.setAssembler(
                            new StructureWithTermsAssembler()));
            a.add(
                    new Empty<Token, TypeOrType<Axiom, Term>, QueryBuilder>().
                    setAssembler(
                            new AtomAssembler()));

            structure.add(a);
        }
        return structure;
    }
    /*
     * Return a parser that recognizes the grammar:
     *
     *    term = structure | Num | list | variable;
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> term() {
        Alternation<Token, TypeOrType<Axiom, Term>, QueryBuilder> a
                = new Alternation<>("term");
        a.add(structure());
        a.add(num());
        a.add(list());
        a.add(variable());
        return a;
    }
    /*
     * Return a parser that recognizes the grammar:
     *
     *    timesFactor = '*' factor;
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> timesFactor() {
        Sequence<Token, TypeOrType<Axiom, Term>, QueryBuilder> s
                = new Sequence<>("timesFactor");
        s.add(new Symbol<TypeOrType<Axiom, Term>, QueryBuilder>('*').discard());
        s.add(factor());
        s.setAssembler(new ArithmeticAssembler('*'));
        return s;
    }
    /*
     * Return a parser that recognizes the grammar:
     *
     *    variable = UppercaseWord | '_';
     *
     * The underscore represents and will translate to an
     * anonymous variable.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> variable() {
        Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> v
                = new UppercaseWord<>();
        v.setAssembler(new VariableAssembler());

        Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> anon
                = new Symbol<TypeOrType<Axiom, Term>, QueryBuilder>('_').
                discard();
        anon.setAssembler(new AnonymousAssembler());

        Alternation<Token, TypeOrType<Axiom, Term>, QueryBuilder> a
                = new Alternation<>();
        a.add(v);
        a.add(anon);
        return a;
    }

    private static final Logger LOG
            = Logger.getLogger(LogikusParser.class.getName());

}
