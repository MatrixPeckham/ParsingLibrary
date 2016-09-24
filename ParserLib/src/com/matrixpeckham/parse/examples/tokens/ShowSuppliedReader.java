package com.matrixpeckham.parse.examples.tokens;

import com.matrixpeckham.parse.parse.tokens.Token;
import static com.matrixpeckham.parse.parse.tokens.Token.EOF;
import com.matrixpeckham.parse.parse.tokens.Tokenizer;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.logging.Logger;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * This class shows that you can supply your own reader to a tokenizer.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ShowSuppliedReader {

    /**
     * Just an example.
     *
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        String s = "Let's file this away.";
        try (FileWriter fw = new FileWriter("temp.txt")) {
            fw.write(s);
        }

        FileReader fr = new FileReader("temp.txt");
        PushbackReader pr = new PushbackReader(fr, 4);

        Tokenizer t = new Tokenizer();
        t.setReader(pr);

        while (true) {
            Token tok = t.nextToken();
            if (tok.equals(EOF)) {
                break;
            }
            System.out.println(tok);
        }
    }

    private static final Logger LOG
            = Logger.getLogger(ShowSuppliedReader.class.getName());

}
