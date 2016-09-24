package com.matrixpeckham.parse.examples.coffee;

import com.matrixpeckham.parse.parse.Alternation;
import com.matrixpeckham.parse.parse.Empty;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.Sequence;
import com.matrixpeckham.parse.parse.tokens.CaselessLiteral;
import com.matrixpeckham.parse.parse.tokens.Num;
import com.matrixpeckham.parse.parse.tokens.Symbol;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.Tokenizer;
import com.matrixpeckham.parse.parse.tokens.Word;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.logging.Logger;

public class CoffeeParser {

    /**
     * Return a parser that will recognize the grammar:
     *
     * <blockquote><pre>
     *
     *     coffee  = name ',' roast ',' country ',' price;
     *
     *  </pre></blockquote>
     *
     * This parser creates a <code>Coffee</code> object as an assembly's target.
     *
     * @return a parser that will recognize and build a <code>Coffee</code>
     * object from a textual description.
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
    /*
     * Return a parser that will recognize the grammar:
     *
     *    country = Word;
     *
     * Use a CountryAssembler to update the target coffee
     * object.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, NullCloneable, Coffee> country() {
        return new Word<NullCloneable, Coffee>().setAssembler(
                new CountryAssembler());
    }
    /*
     * Return a parser that will recognize the grammar:
     *
     *     formerName = '(' Word ')';
     *
     * Use a FormerNameAssembler to update the target coffee
     * object.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, NullCloneable, Coffee> formerName() {
        Sequence<Token, NullCloneable, Coffee> s = new Sequence<>();
        s.add(new Symbol<NullCloneable, Coffee>('(').discard());
        s.add(new Word<NullCloneable, Coffee>().setAssembler(
                new FormerNameAssembler()));
        s.add(new Symbol<NullCloneable, Coffee>(')').discard());
        return s;
    }
    /*
     * Return a parser that will recognize the grammar:
     *
     *     name = Word (formerName | empty);
     *
     * Use a NameAssembler to update the target coffee object
     * with the recognized Word; formerName also uses an
     * assembler.
     */

    /**
     *
     * @return
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
    /*
     * Return a parser that will recognize the sequence:
     *
     *    orFrench = '/' "french";
     *
     * Use an AlsoFrenchAssembler to update the target coffee
     * object.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, NullCloneable, Coffee> orFrench() {
        Sequence<Token, NullCloneable, Coffee> s = new Sequence<>();
        s.add(new Symbol<NullCloneable, Coffee>('/').discard());
        s.add(new CaselessLiteral<NullCloneable, Coffee>("french").discard());
        s.setAssembler(new AlsoFrenchAssembler());
        return s;
    }
    /*
     * Return a parser that will recognize the sequence:
     *
     *    price = Num;
     *
     * Use a PriceAssembler to update the target coffee object.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, NullCloneable, Coffee> price() {
        return new Num<NullCloneable, Coffee>().setAssembler(
                new PriceAssembler());
    }
    /*
     * Return a parser that will recognize the grammar:
     *
     *     roast = Word (orFrench | Empty);
     *
     * Use a RoastAssembler to update the target coffee object
     * with the recognized Word; orFrench also uses an
     * assembler.
     */

    /**
     *
     * @return
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
     * identify a coffee's name.
     */
    public static Tokenizer tokenizer() {
        Tokenizer t = new Tokenizer();
        t.wordState().setWordChars(' ', ' ', true);
        return t;
    }

    private static final Logger LOG
            = Logger.getLogger(CoffeeParser.class.getName());

}
