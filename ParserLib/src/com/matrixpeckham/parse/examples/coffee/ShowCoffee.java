package com.matrixpeckham.parse.examples.coffee;

import static com.matrixpeckham.parse.examples.coffee.CoffeeParser.start;
import static com.matrixpeckham.parse.examples.coffee.CoffeeParser.tokenizer;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.TokenAssembly;
import com.matrixpeckham.parse.parse.tokens.Tokenizer;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.ClassLoader.getSystemResourceAsStream;
import java.util.logging.Logger;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Show the recognition of a list of types of coffee, reading from a file.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowCoffee {

    /**
     * Show how to recognize coffees in a file.
     *
     * @param args
     * @throws java.lang.Exception
     */
    public static void main(String args[]) throws Exception {
        InputStream is = getSystemResourceAsStream("coffee.txt");
        BufferedReader r
                = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        Tokenizer t = tokenizer();
        Parser<Token, NullCloneable, Coffee> p = start();
        while (true) {
            String s = r.readLine();
            if (s == null) {
                break;
            }
            t.setString(s);
            Assembly<Token, NullCloneable, Coffee> in = new TokenAssembly<>(t);
            Assembly<Token, NullCloneable, Coffee> out = p.bestMatch(in);
            System.out.println(out.getTarget());
        }
    }

    private static final Logger LOG
            = Logger.getLogger(ShowCoffee.class.getName());

}
