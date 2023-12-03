package com.matrixpeckham.parse.examples.coffee;

import com.matrixpeckham.parse.parse.*;
import com.matrixpeckham.parse.parse.tokens.*;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.logging.Logger;

/**
 * This class provides a parser that recognizes a
 * textual description of a type of coffee, and builds a
 * corresponding coffee object.
 * <p>
 * The grammar this class supports is:
 * <blockquote><pre>
 *
 *     coffee     = name ',' roast ',' country ',' price;
 *     name       = Word (formerName | Empty);
 *     formerName = '(' Word ')';
 *     roast      = Word (orFrench | Empty);
 *     orFrench   = '/' "french";
 *     country    = Word;
 *     price      = Num;
 * </pre></blockquote>
 */
public class CoffeeParser {

    /**
     * Return a parser that will recognize the grammar:
     * <p>
     * <blockquote><pre>
     *
     *     coffee  = name ',' roast ',' country ',' price;
     *
     * </pre></blockquote>
     * <p>
     * This parser creates a <code>Coffee</code> object as an assembly's target.
     *
     * @return a parser that will recognize and build a <code>Coffee</code>
     *         object from a textual description.
     */
    public Parser<Token, NullCloneable, Coffee> coffee() {
        Symbol<NullCloneable, Coffee> comma = new Symbol<>(',');
        comma.discard();
        Sequence<Token, NullCloneable, Coffee> s = new Sequence<>();
        s.add(name());
        s.add(comma);
        s.add(roast());
        s.add(comma);
        s.add(country());
        s.add(comma);
        s.add(price());
        return s;
    }

    /**
     * Return a parser that will recognize the grammar:
     * <p>
     * country = Word;
     * <p>
     * Use a CountryAssembler to update the target coffee
     * object.
     */
    protected Parser<Token, NullCloneable, Coffee> country() {
        return new Word<NullCloneable, Coffee>().setAssembler(
                new CountryAssembler());
    }

    /**
     * Return a parser that will recognize the grammar:
     * <p>
     * formerName = '(' Word ')';
     * <p>
     * Use a FormerNameAssembler to update the target coffee
     * object.
     */
    protected Parser<Token, NullCloneable, Coffee> formerName() {
        Sequence<Token, NullCloneable, Coffee> s = new Sequence<>();
        s.add(new Symbol<NullCloneable, Coffee>('(').discard());
        s.add(new Word<NullCloneable, Coffee>().setAssembler(
                new FormerNameAssembler()));
        s.add(new Symbol<NullCloneable, Coffee>(')').discard());
        return s;
    }

    /**
     * Return a parser that will recognize the grammar:
     * <p>
     * name = Word (formerName | empty);
     * <p>
     * Use a NameAssembler to update the target coffee object
     * with the recognized Word; formerName also uses an
     * assembler.
     */
    protected Parser<Token, NullCloneable, Coffee> name() {
        Sequence<Token, NullCloneable, Coffee> s = new Sequence<>();
        s.add(new Word<NullCloneable, Coffee>().
                setAssembler(new NameAssembler()));
        Alternation<Token, NullCloneable, Coffee> a = new Alternation<>();
        a.add(formerName());
        a.add(new Empty<>());
        s.add(a);
        return s;
    }

    /**
     * Return a parser that will recognize the sequence:
     * <p>
     * orFrench = '/' "french";
     * <p>
     * Use an AlsoFrenchAssembler to update the target coffee
     * object.
     */
    protected Parser<Token, NullCloneable, Coffee> orFrench() {
        Sequence<Token, NullCloneable, Coffee> s = new Sequence<>();
        s.add(new Symbol<NullCloneable, Coffee>('/').discard());
        s.add(new CaselessLiteral<NullCloneable, Coffee>("french").discard());
        s.setAssembler(new AlsoFrenchAssembler());
        return s;
    }

    /**
     * Return a parser that will recognize the sequence:
     * <p>
     * price = Num;
     * <p>
     * Use a PriceAssembler to update the target coffee object.
     */
    protected Parser<Token, NullCloneable, Coffee> price() {
        return new Num<NullCloneable, Coffee>().setAssembler(
                new PriceAssembler());
    }

    /**
     * Return a parser that will recognize the grammar:
     * <p>
     * roast = Word (orFrench | Empty);
     * <p>
     * Use a RoastAssembler to update the target coffee object
     * with the recognized Word; orFrench also uses an
     * assembler.
     */
    protected Parser<Token, NullCloneable, Coffee> roast() {
        Sequence<Token, NullCloneable, Coffee> s = new Sequence<>();
        s.add(new Word<NullCloneable, Coffee>().setAssembler(
                new RoastAssembler()));
        Alternation<Token, NullCloneable, Coffee> a = new Alternation<>();
        a.add(orFrench());
        a.add(new Empty<>());
        s.add(a);
        return s;
    }

    /**
     * Return the primary parser for this class -- coffee().
     *
     * @return the primary parser for this class -- coffee()
     */
    public static Parser<Token, NullCloneable, Coffee> start() {
        return new CoffeeParser().coffee();
    }

    /**
     * Returns a tokenizer that allows spaces to appear inside the "words" that
     * identify a coffee's name.
     *
     * @return a tokenizer that allows spaces to appear inside the "words" that
     *         identify a coffee's name.
     */
    public static Tokenizer tokenizer() {
        Tokenizer t = new Tokenizer();
        t.wordState().setWordChars(' ', ' ', true);
        return t;
    }

    private static final Logger LOG
            = Logger.getLogger(CoffeeParser.class.getName());

}
