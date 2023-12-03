package com.matrixpeckham.parse.examples.sling;

import com.matrixpeckham.parse.examples.reserved.WordOrReservedState;
import com.matrixpeckham.parse.examples.track.Track;
import com.matrixpeckham.parse.imperative.Command;
import com.matrixpeckham.parse.parse.*;
import com.matrixpeckham.parse.parse.tokens.*;
import com.matrixpeckham.parse.utensil.TypeOrType;
import java.util.logging.Logger;

/**
 *
 * This class provides a parser for Sling, an imperative
 * language that plots the path of a sling stone.
 * <p>
 * <p>
 * The grammar this class supports is:
 * <blockquote><pre>
 *
 * statements    = statement statement*;
 * statement     = assignment | forStatement | plotStatement;
 * assignment    = variable '=' expression ';' ;
 * plotStatement = "plot" expression ';';
 * forStatement  =
 *     "for" '(' variable ',' expression ',' expression  ')'
 *     '{' statements '}';
 * <br>
 * variable   = Word;
 * <br>
 * expression       = term (plusTerm | minusTerm)*;
 * plusTerm         = '+' term;
 * minusTerm        = '-' term;
 * term             = element (timesElement | divideElement |
 *                             remainderElement)*;
 * timesElement     = '*' element;
 * divideElement    = '/' element;
 * remainderElement = '%' element;
 * element          = '(' expression ')' | baseElement |
 *                    negative;
 * <br>
 * negative    = '-' baseElement;
 * <br>
 * baseElement =
 *     Num | "pi" | "random" | "s1" | "s2" | "t" | variable |
 *     oneArg("abs")    | oneArg("ceil")       |
 *     oneArg("cos")    | oneArg("floor")      |
 *     oneArg("sin")    | oneArg("tan")        |
 *     twoArgs("polar") | twoArgs("cartesian") |
 *     twoArgs("scale") | twoArgs("sling");
 * <br>
 * oneArg(i)  = i '(' expression ')';
 * twoArgs(i) = i '(' expression ',' expression ')';
 *
 * </pre></blockquote>
 * <p>
 * The following program describes about 10,000 interesting
 * plots:
 * <p>
 * <blockquote><pre>
 *     plot sling(1, 1) + sling(s1, 100*s2);
 * </pre></blockquote>
 * <p>
 * <p>
 * The class <code>SlingIde</code> provides an interactive
 * development environment for Sling.
 */
public class SlingParser {

    /**
     *
     */
    protected Sequence<Token, TypeOrType<SlingFunction, Command>, SlingTarget> expression;

    /**
     *
     */
    protected Alternation<Token, TypeOrType<SlingFunction, Command>, SlingTarget> statement;

    /**
     *
     */
    protected Alternation<Token, TypeOrType<SlingFunction, Command>, SlingTarget> baseElement;

    /**
     *
     */
    protected WordOrReservedState wors;

    /**
     *
     */
    protected Tokenizer tokenizer;

    /**
     * Returns a parser that will recognize the grammar:
     * <p>
     * assignment = variable '=' expression ';' ;
     *
     * @return
     */
    protected Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> assignment() {
        Track<Token, TypeOrType<SlingFunction, Command>, SlingTarget> t
                = new Track<>("assignment");
        t.add(variable());
        t.add(new Symbol<TypeOrType<SlingFunction, Command>, SlingTarget>('=').
                discard());
        t.add(expression());
        t.add(new Symbol<TypeOrType<SlingFunction, Command>, SlingTarget>(';').
                discard());
        t.setAssembler(new AssignmentAssembler());
        return t;
    }

    /**
     * Returns a parser that will recognize the grammar:
     * <p>
     * baseElement =
     * Num | "pi" | "random" | "s1" | "s2" | "t" | variable |
     * ("abs" |"ceil" |"cos" | "floor" |"sin" |"tan") oneArg |
     * ("polar" | "cartesian" | "scale" | "sling") twoArgs;
     *
     * @return
     */
    protected synchronized Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> baseElement() {
        if (baseElement == null) {
            baseElement = new Alternation<>("base elements");
            baseElement.add(oneArg("abs", new Abs()));
            baseElement.add(twoArg("cartesian", new Cartesian()));
            baseElement.add(oneArg("ceil", new Ceil()));
            baseElement.add(oneArg("cos", new Cos()));
            baseElement.add(oneArg("floor", new Floor()));
            baseElement.add(num());
            baseElement.add(noArgs("random", new Random()));
            baseElement.add(pi());
            baseElement.add(twoArg("polar", new Polar()));
            baseElement.add(s1());
            baseElement.add(s2());
            baseElement.add(scale());
            baseElement.add(oneArg("sin", new Sin()));
            baseElement.add(twoArg("sling", new Sling()));
            baseElement.add(noArgs("t", new T()));
            baseElement.add(oneArg("tan", new Tan()));
            baseElement.add(variable());
        }
        return baseElement;
    }

    /**
     * Recognize a comma.
     *
     * @return
     */
    protected static Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> comma() {
        return new Symbol<TypeOrType<SlingFunction, Command>, SlingTarget>(',').
                discard();
    }

    /**
     * Returns a parser that will recognize the grammar:
     * <p>
     * divideElement = '/' element;
     *
     * @return
     */
    protected Track<Token, TypeOrType<SlingFunction, Command>, SlingTarget> divideElement() {
        Track<Token, TypeOrType<SlingFunction, Command>, SlingTarget> t
                = new Track<>();
        t.add(new Symbol<TypeOrType<SlingFunction, Command>, SlingTarget>('/').
                discard());
        t.add(element());
        t.setAssembler(new FunctionAssembler(new Arithmetic('/')));
        return t;
    }

    /**
     * Returns a parser that will recognize the grammar:
     * <p>
     * element = '(' expression ')' | baseElement | negative;
     *
     * @return
     */
    protected Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> element() {

        Alternation<Token, TypeOrType<SlingFunction, Command>, SlingTarget> a
                = new Alternation<>("element");
        Sequence<Token, TypeOrType<SlingFunction, Command>, SlingTarget> s
                = new Sequence<>();
        s.add(new Symbol<TypeOrType<SlingFunction, Command>, SlingTarget>('(').
                discard());
        s.add(expression());
        s.add(new Symbol<TypeOrType<SlingFunction, Command>, SlingTarget>(')').
                discard());
        a.add(s);
        a.add(baseElement());
        a.add(negative());
        return a;
    }

    /**
     * Returns a parser that will recognize the grammar:
     * <p>
     * expression = term (plusTerm | minusTerm)*;
     *
     * @return
     */
    protected synchronized Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> expression() {

        if (expression == null) {
            expression = new Sequence<>("expression");
            expression.add(term());
            Alternation<Token, TypeOrType<SlingFunction, Command>, SlingTarget> rest
                    = new Alternation<>();
            rest.add(plusTerm());
            rest.add(minusTerm());
            expression.add(new Repetition<>(rest));
        }
        return expression;
    }

    /**
     * Returns a parser that will recognize the grammar:
     * <p>
     * forStatement =
     * "for" '(' variable ',' expression ',' expression ')'
     * '{' statements '}';
     *
     * @return
     */
    protected Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> forStatement() {
        Track<Token, TypeOrType<SlingFunction, Command>, SlingTarget> t
                = new Track<>();
        t.add(reserve("for"));
        t.add(lParen());

        // variable
        t.add(variable());
        t.add(comma());

        // from
        t.add(expression());
        t.add(comma());

        // to
        t.add(expression());
        t.add(rParen());

        // commands
        t.add(lBrace());
        t.add(statements());
        t.add(rBrace());
        t.setAssembler(new ForAssembler());
        return t;
    }

    /**
     * Recognize a left brace, and leave it on the stack as
     * a fence.
     *
     * @return
     */
    protected static Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> lBrace() {
        return new Symbol<>('{');
    }

    /**
     * Recognize a left parenthesis.
     *
     * @return
     */
    protected static Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> lParen() {
        return new Symbol<TypeOrType<SlingFunction, Command>, SlingTarget>('(').
                discard();
    }

    /**
     * Returns a parser that will recognize the grammar:
     * <p>
     * minusTerm = '-' term;
     *
     * @return
     */
    protected Track<Token, TypeOrType<SlingFunction, Command>, SlingTarget> minusTerm() {
        Track<Token, TypeOrType<SlingFunction, Command>, SlingTarget> t
                = new Track<>();
        t.add(new Symbol<TypeOrType<SlingFunction, Command>, SlingTarget>('-').
                discard());
        t.add(term());
        t.setAssembler(new FunctionAssembler(new Arithmetic('-')));
        return t;
    }

    /**
     * Returns a parser that will recognize the grammar:
     * <p>
     * negative = '-' baseElement;
     *
     * @return
     */
    protected Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> negative() {
        Sequence<Token, TypeOrType<SlingFunction, Command>, SlingTarget> s
                = new Sequence<>("negative baseElement");
        s.add(new Symbol<TypeOrType<SlingFunction, Command>, SlingTarget>('-').
                discard());
        s.add(baseElement());
        s.setAssembler(new NegativeAssembler());
        return s;
    }

    /**
     * Reserves the given name, and creates and returns an
     * parser that recognizes the name. Sets the assembler of
     * the parser to be a <code>FunctionAssembler</code> for
     * the given function.
     *
     * @param name
     * @param f
     *
     * @return
     */
    protected Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> noArgs(
            String name, SlingFunction f) {
        Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> p
                = reserve(name);
        p.setAssembler(new FunctionAssembler(f));
        return p;
    }

    /**
     * Constructs and returns a parser that recognizes a
     * number and that uses a <code>NumAssembler</code>.
     *
     * @return
     */
    protected Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> num() {
        return new Num<TypeOrType<SlingFunction, Command>, SlingTarget>().
                setAssembler(new NumAssembler());
    }

    /**
     * Return a parser that recognizes and stacks a one-
     * argument function.
     *
     * @param name
     * @param f
     *
     * @return
     */
    protected Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> oneArg(
            String name, SlingFunction f) {
        Track<Token, TypeOrType<SlingFunction, Command>, SlingTarget> t
                = new Track<>(name);
        t.add(reserve(name));
        t.add(lParen());
        t.add(expression());
        t.add(rParen());
        t.setAssembler(new FunctionAssembler(f));
        return t;
    }

    /**
     * Returns a parser that recognizes the literal "pi". Sets
     * the parser's assembler to be a <code>PiAssembler</code>.
     *
     * @return
     */
    protected Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> pi() {
        ReservedLiteral<TypeOrType<SlingFunction, Command>, SlingTarget> pi
                = reserve("pi");
        pi.setAssembler(new PiAssembler());
        return pi;
    }

    /**
     * Return a parser that recognizes the grammar:
     * <p>
     * plotStatement = "plot" expression ';';
     *
     * @return
     */
    protected Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> plotStatement() {
        Track<Token, TypeOrType<SlingFunction, Command>, SlingTarget> t
                = new Track<>();
        t.add(reserve("plot"));
        t.add(expression());
        t.add(semicolon());
        t.setAssembler(new PlotAssembler());
        return t;
    }

    /**
     * Returns a parser that will recognize the grammar:
     * <p>
     * plusTerm = '+' term;
     *
     * @return
     */
    protected Track<Token, TypeOrType<SlingFunction, Command>, SlingTarget> plusTerm() {
        Track<Token, TypeOrType<SlingFunction, Command>, SlingTarget> t
                = new Track<>();
        t.add(new Symbol<TypeOrType<SlingFunction, Command>, SlingTarget>('+').
                discard());
        t.add(term());
        t.setAssembler(new FunctionAssembler(new Arithmetic('+')));
        return t;
    }

    /**
     * Recognize a right brace.
     *
     * @return
     */
    protected static Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> rBrace() {
        return new Symbol<TypeOrType<SlingFunction, Command>, SlingTarget>('}').
                discard();
    }

    /**
     * Returns a parser that will recognize the grammar:
     * <p>
     * remainderElement = '%' element;
     *
     * @return
     */
    protected Track<Token, TypeOrType<SlingFunction, Command>, SlingTarget> remainderElement() {
        Track<Token, TypeOrType<SlingFunction, Command>, SlingTarget> t
                = new Track<>();
        t.add(new Symbol<TypeOrType<SlingFunction, Command>, SlingTarget>('%').
                discard());
        t.add(element());
        t.setAssembler(new FunctionAssembler(new Arithmetic('%')));
        return t;
    }

    /**
     * Mark the given word as reserved, meaning users cannot use
     * the word as a variable. Create a special literal parser
     * to recognize the word, and return this parser.
     *
     * @param s
     *
     * @return
     */
    protected ReservedLiteral<TypeOrType<SlingFunction, Command>, SlingTarget> reserve(
            String s) {
        wors().addReservedWord(s);
        ReservedLiteral<TypeOrType<SlingFunction, Command>, SlingTarget> lit
                = new ReservedLiteral<>(s);
        lit.discard();
        return lit;
    }

    /**
     * Recognize a right parenthesis.
     *
     * @return
     */
    protected static Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> rParen() {
        return new Symbol<TypeOrType<SlingFunction, Command>, SlingTarget>(')').
                discard();
    }

    /**
     * Recognize the first slider variable.
     *
     * @return
     */
    protected Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> s1() {
        Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> p
                = reserve("s1");
        // the index here recovers a real slider from the target
        p.setAssembler(new SliderAssembler(1));
        return p;

    }

    /**
     * Recognize the second slider variable.
     *
     * @return
     */
    protected Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> s2() {
        Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> p
                = reserve("s2");
        p.setAssembler(new SliderAssembler(2));
        return p;
    }

    /**
     * Returns a parser that recognizes scale functions, and
     * sets the parser's assembler to be a <code>ScaleAssembler
     * </code>.
     *
     * @return
     */
    protected Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> scale() {
        Track<Token, TypeOrType<SlingFunction, Command>, SlingTarget> t
                = new Track<>("scale");
        t.add(reserve("scale"));
        t.add(lParen());

        t.add(expression());

        t.add(comma());
        t.add(expression());

        t.add(rParen());

        t.setAssembler(new ScaleAssembler());
        return t;
    }

    /**
     * Recognize a semicolon.
     *
     * @return
     */
    protected static Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> semicolon() {
        return new Symbol<TypeOrType<SlingFunction, Command>, SlingTarget>(';').
                discard();
    }

    /**
     * Recoginze Sling <code>statements</code>.
     *
     * @return
     */
    public Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> start() {
        return statements();
    }

    /**
     * Returns a parser that will recognize the grammar:
     * <p>
     * statement = assignment | forStatement | plotStatement;
     *
     * @return
     */
    protected synchronized Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> statement() {
        if (statement == null) {
            statement = new Alternation<>("Statement");
            statement.add(assignment());
            statement.add(forStatement());
            statement.add(plotStatement());
        }
        return statement;
    }

    /**
     * Returns a parser that will recognize the grammar:
     * <p>
     * statements = statement statement*;
     *
     * @return
     */
    protected Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> statements() {
        Sequence<Token, TypeOrType<SlingFunction, Command>, SlingTarget> s
                = new Sequence<>();
        s.add(statement());
        s.add(new Repetition<>(statement()));
        return s;
    }

    /**
     * Returns a parser that will recognize the grammar:
     * <p>
     * term = element (timesElement | divideElement |
     * remainderElement)*;
     *
     * @return
     */
    protected Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> term() {
        Sequence<Token, TypeOrType<SlingFunction, Command>, SlingTarget> s
                = new Sequence<>("term");
        s.add(element());
        Alternation<Token, TypeOrType<SlingFunction, Command>, SlingTarget> a
                = new Alternation<>();
        a.add(timesElement());
        a.add(divideElement());
        a.add(remainderElement());
        s.add(new Repetition<>(a));
        return s;
    }

    /**
     * Returns a parser that will recognize the grammar:
     * <p>
     * timesElement = '*' element;
     *
     * @return
     */
    protected Track<Token, TypeOrType<SlingFunction, Command>, SlingTarget> timesElement() {
        Track<Token, TypeOrType<SlingFunction, Command>, SlingTarget> t
                = new Track<>();
        t.add(new Symbol<TypeOrType<SlingFunction, Command>, SlingTarget>('*').
                discard());
        t.add(element());
        t.setAssembler(new FunctionAssembler(new Arithmetic('*')));
        return t;
    }

    /**
     * Creates a tokenizer that uses a <code>WordOrReservedState
     * </code> instead of a normal <code>WordState</code>.
     *
     * @return
     */
    public synchronized Tokenizer tokenizer() {
        if (tokenizer == null) {
            start(); // to reserve the key words
            tokenizer = new Tokenizer();
            tokenizer.setCharacterState('a', 'z', wors());
            tokenizer.setCharacterState('A', 'Z', wors());
            tokenizer.setCharacterState(0xc0, 0xff, wors());
        }
        return tokenizer;
    }

    /**
     * Return a parser that recognizes and stacks a one-
     * argument function.
     *
     * @param name
     * @param f
     *
     * @return
     */
    protected Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> twoArg(
            String name, SlingFunction f) {
        Track<Token, TypeOrType<SlingFunction, Command>, SlingTarget> t
                = new Track<>(name);
        t.add(reserve(name));
        t.add(lParen());
        t.add(expression());
        t.add(comma());
        t.add(expression());
        t.add(rParen());
        t.setAssembler(new FunctionAssembler(f));
        return t;
    }

    /**
     * Recognize a word as a variable.
     *
     * @return
     */
    protected Parser<Token, TypeOrType<SlingFunction, Command>, SlingTarget> variable() {
        return new Word<TypeOrType<SlingFunction, Command>, SlingTarget>().
                setAssembler(new VariableAssembler());
    }

    /**
     * Returns a WordOrReservedState object, which is a tokenizer
     * state that differentiates reserved words from nonreserved
     * words.
     *
     * @return
     */
    protected WordOrReservedState wors() {
        if (wors == null) {
            wors = new WordOrReservedState();
        }
        return wors;
    }

    private static final Logger LOG
            = Logger.getLogger(SlingParser.class.getName());

}
