package com.matrixpeckham.parse.parse.chars;

import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.utensil.PubliclyCloneable;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

/**
 * A CharacterAssembly is an Assembly whose elements are
 * characters.
 *
 * @author Owner
 * @param <Val>
 * @param <Tar>
 */
public class CharacterAssembly<Val, Tar extends PubliclyCloneable<Tar>>
        extends Assembly<Character, Val, Tar> {

    /**
     * the string to consume
     */
    protected String string;

    /**
     * Constructs a CharacterAssembly from the given String.
     *
     * @param string the String to consume
     */
    public CharacterAssembly(String string) {
        this.string = string;
    }

    private CharacterAssembly(CharacterAssembly<Val, Tar> c) {
        super(c);
        this.string = c.string;
    }

    @Override
    public CharacterAssembly<Val, Tar> copy() {
        return new CharacterAssembly<>(this);
    }

    /**
     * Returns a textual representation of the amount of this characterAssembly
     * that has been consumed.
     *
     * @param delimiter the mark to show between consumed elements
     *
     * @return a textual description of the amount of this assembly that has
     *         been consumed
     */
    @Override
    public String consumed(String delimiter) {
        if (delimiter.isEmpty()) {
            return string.substring(0, elementsConsumed());
        }
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < elementsConsumed(); i++) {
            if (i > 0) {
                buf.append(delimiter);
            }
            buf.append(string.charAt(i));
        }
        return buf.toString();
    }

    /**
     * Returns the default string to show between elements consumed or
     * remaining.
     *
     * @return the default string to show between elements consumed or remaining
     */
    @Override
    public String defaultDelimiter() {
        return "";
    }

    /**
     * Returns the number of elements in this assembly.
     *
     * @return the number of elements in this assembly
     */
    @Override
    public int length() {
        return string.length();
    }

    /**
     * Returns the next character.
     *
     * @return the next character from the associated token string
     *
     * @exception ArrayIndexOutOfBoundsException if there are no more characters
     *                                           in this assembly's string
     */
    @Override
    public Character next() {
        try {
            return string.charAt(index++);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            throw new NoSuchElementException();
        }
    }

    /**
     * Shows the next object in the assembly, without removing it
     *
     * @return the next object
     */
    @Override
    public Object peek() {
        if (index < length()) {
            return string.charAt(index);
        } else {
            return null;
        }
    }

    /**
     * Shows the next object in the assembly, without removing it
     *
     * @return the next object
     */
    @Override
    public Character peekTok() {
        if (index < length()) {
            return string.charAt(index);
        } else {
            return null;
        }
    }

    /**
     * Returns a textual representation of the amount of this characterAssembly
     * that remains to be consumed.
     *
     * @param delimiter the mark to show between consumed elements
     *
     * @return a textual description of the amount of this assembly that remains
     *         to be consumed
     */
    @Override
    public String remainder(String delimiter) {
        if (delimiter.isEmpty()) {
            return string.substring(elementsConsumed());
        }
        StringBuilder buf = new StringBuilder();
        for (int i = elementsConsumed();
                i < string.length();
                i++) {

            if (i > elementsConsumed()) {
                buf.append(delimiter);
            }
            buf.append(string.charAt(i));
        }
        return buf.toString();
    }

    private static final Logger LOG
            = Logger.getLogger(CharacterAssembly.class.getName());

}
