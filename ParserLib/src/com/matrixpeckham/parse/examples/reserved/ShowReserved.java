package com.matrixpeckham.parse.examples.reserved;

import static com.matrixpeckham.parse.examples.reserved.VolumeQuery2.query;
import static com.matrixpeckham.parse.examples.reserved.VolumeQuery2.tokenizer;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.TokenAssembly;
import com.matrixpeckham.parse.parse.tokens.Tokenizer;
import com.matrixpeckham.parse.utensil.NullCloneable;
import java.util.ArrayList;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * This class shows the use of a customized tokenizer, and the use of a terminal
 * that looks for the new token type.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowReserved {

    /**
     * Show the use of a customized tokenizer, and the use of a terminal that
     * looks for the new token type.
     *
     * @param args
     */
    public static void main(String[] args) {

        Tokenizer t = tokenizer();
        t.setString("How many cups are in a gallon?");

        ArrayList<Assembly<Token, String, NullCloneable>> v
                = new ArrayList<>();
        v.add(new TokenAssembly<>(t));

        System.out.println(query().match(v));
    }

    private static final Logger LOG
            = Logger.getLogger(ShowReserved.class.getName());

}
