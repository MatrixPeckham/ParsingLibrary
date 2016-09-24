package com.matrixpeckham.parse.parse;

import com.matrixpeckham.parse.utensil.PubliclyCloneable;
import com.matrixpeckham.parse.utensil.TypeOrType;
import java.util.ArrayList;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * Parsers that have an Assembler ask it to work on an assembly after a
 * successful match.
 * <p>
 * By default, terminals push their matches on a assembly's stack after a
 * successful match.
 * <p>
 * Parsers recognize text, and assemblers provide any sort of work that should
 * occur after this recognition. This work usually has to do with the state of
 * the assembly, which is why assemblies have a stack and a target. Essentially,
 * parsers trade advancement on a assembly for work on the assembly's stack or
 * target.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 * @param <Tok>
 * @param <Val>
 * @param <Tar>
 */
public abstract class Assembler<Tok, Val, Tar extends PubliclyCloneable<Tar>> {

    /**
     * Returns a vector of the elements on an assembly's stack that appear
     * before a specified fence.
     * <p>
     * Sometimes a parser will recognize a list from within a pair of
     * parentheses or brackets. The parser can mark the beginning of the list
     * with a fence, and then retrieve all the items that come after the fence
     * with this method.
     *
     * @param <Tok>
     * @param <Val>
     * @param <Tar>
     * @param a a assembly whose stack should contain some number of items above
     * a fence marker
     * @param fence the fence, a marker of where to stop popping the stack
     *
     * @return ArrayList the elements above the specified fence
     *
     */
    public static <Tok, Val, Tar extends PubliclyCloneable<Tar>> ArrayList<TypeOrType<Tok, Val>> elementsAbove(
            Assembly<Tok, Val, Tar> a, Object fence) {
        ArrayList<TypeOrType<Tok, Val>> items = new ArrayList<>();

        while (!a.stackIsEmpty()) {
            TypeOrType<Tok, Val> top = a.popTypeOrType();
            if (top.asObject().equals(fence)) {
                break;
            }
            items.add(top);
        }
        return items;
    }

    /**
     * This is the one method all subclasses must implement. It specifies what
     * to do when a parser successfully matches against a assembly.
     *
     * @param a the assembly to work on
     */
    public abstract void workOn(Assembly<Tok, Val, Tar> a);
}
