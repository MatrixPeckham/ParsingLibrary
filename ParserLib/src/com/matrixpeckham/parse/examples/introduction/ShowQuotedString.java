package com.matrixpeckham.parse.examples.introduction;

import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.tokens.QuotedString;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.TokenAssembly;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Show how to recognize a quoted string.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowQuotedString {

    /**
     * Show how to recognize a quoted string.
     *
     * @param args
     */
    public static void main(String[] args) {
        Parser<Token, NullCloneable, NullCloneable> p = new QuotedString<>();
        String id = "\"Clark Kent\"";
        System.out.println(p.bestMatch(new TokenAssembly<>(id)));
    }

    private static final Logger LOG
            = Logger.getLogger(ShowQuotedString.class.getName());

}
