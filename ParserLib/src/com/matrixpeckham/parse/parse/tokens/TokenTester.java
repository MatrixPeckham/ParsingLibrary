package com.matrixpeckham.parse.parse.tokens;

import com.matrixpeckham.parse.parse.*;
import com.matrixpeckham.parse.utensil.PubliclyCloneable;
import java.util.logging.Logger;

/**
 * Tester for token based assemblies.
 *
 * @author Owner
 * @param <Val>
 * @param <Tar>
 */
public class TokenTester<Val, Tar extends PubliclyCloneable<Tar>>
        extends ParserTester<Token, Val, Tar> {

    /**
     *
     * @param p
     */
    public TokenTester(Parser<Token, Val, Tar> p) {
        super(p);
    }

    /**
     * Make a new token assembly.
     *
     * @param s
     *
     * @return
     */
    @Override
    protected Assembly<Token, Val, Tar> assembly(String s) {
        return new TokenAssembly<>(s);
    }

    private static final Logger LOG
            = Logger.getLogger(TokenTester.class.getName());

}
