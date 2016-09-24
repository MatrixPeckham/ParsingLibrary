package com.matrixpeckham.parse.utensil;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Logger;

public class FileString {

    /**
     * Returns a string that represents the contents of a file.
     *
     * @param fileName the name of the file to read
     *
     * @return string the contents of a file as a String
     *
     * @exception IOException if the file is not found, or if there is any
     * problem reading the file
     */
    public static String stringFromFileNamed(String fileName)
            throws java.io.IOException {

        final int BUFLEN = 1024;
        char buf[] = new char[BUFLEN];
        FileReader in = null;
        StringWriter out = null;
        try {
            out = new StringWriter();
            in = new FileReader(fileName);
            while (true) {
                int len = in.read(buf, 0, BUFLEN);
                if (len == -1) {
                    break;
                }
                out.write(buf, 0, len);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
        if (out != null) {
            return out.toString();
        }
        return "";
    }

    private static final Logger LOG
            = Logger.getLogger(FileString.class.getName());

}
