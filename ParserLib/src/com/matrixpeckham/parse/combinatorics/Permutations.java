package com.matrixpeckham.parse.combinatorics;

import static com.matrixpeckham.parse.combinatorics.Combinatoric.check;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class Permutations<T> implements java.util.Iterator<T[]> {

    /**
     *
     */
    protected T[] inArray;

    protected int n,
            /**
             *
             */
            m;

    /**
     *
     */
    protected int[] index;

    /**
     *
     */
    protected boolean hasMore = true;

    /**
     * Create a Permutation to enumerate through all possible lineups of the
     * supplied array of Objects.
     *
     * @param inArray inArray the group to line up
     *
     * @exception CombinatoricException Should never happen with this interface
     *
     */
    public Permutations(T[] inArray)
            throws CombinatoricException {

        this(inArray, inArray.length);
    }

    /**
     * Create a Permutation to enumerate through all possible lineups of the
     * supplied array of Objects.
     *
     *
     * @param inArray java.lang.Object[], the group to line up
     *
     * @param m int, the number of objects to use
     *
     * @exception CombinatoricException if m is greater than the length of
     * inArray, or less than 0.
     */
    public Permutations(T[] inArray, int m)
            throws CombinatoricException {

        this.n = inArray.length;
        this.inArray = Arrays.copyOf(inArray, n);
        this.m = m;

// throw exception unless n >= m >= 0
        check(n, m);

        /**
         * 'index' is an array of ints that keep track of the next permutation
         * to return. For example, an index on a permutation of 3 things might
         * contain {1 2 0}. This index will be followed by {2 0 1} and {2 1 0}.
         * Initially, the index is {0 ... n - 1}.
         */
        index = new int[n];
        for (int i = 0; i < n; i++) {
            index[i] = i;
        }

        /**
         * The elements from m to n are always kept ascending right to left.
         * This keeps the 'dip' in the interesting region.
         */
        reverseAfter(m - 1);
    }

    /**
     * @return true, unless we have already returned the last permutation.
     */
    @Override
    public boolean hasNext() {
        return hasMore;
    }

    /**
     * Move the index forward a notch. The algorithm first finds the rightmost
     * index that is less than its neighbor to the right. This is the 'dip'
     * point. The algorithm next finds the least element to the right of the dip
     * that is greater than the dip. That element is switched with the dip.
     * Finally, the list of elements to the right of the dip is reversed.
     * <p>
     * For example, in a permuation of 5 items, the index may be {1, 2, 4, 3,
     * 0}. The 'dip' is 2 -- the rightmost element less than its neighbor on its
     * right. The least element to the right of 2 that is greater than 2 is 3.
     * These elements are swapped, yielding {1, 3, 4, 2, 0}, and the list right
     * of the dip point is reversed, yielding {1, 3, 0, 2, 4}.
     * <p>
     * The algorithm is from "Applied Combinatorics", by Alan Tucker.
     *
     */
    protected final void moveIndex() {

        // find the index of the first element that dips
        int i = rightmostDip();
        if (i < 0) {
            hasMore = false;
            return;
        }

        // find the least greater element to the right of the dip
        int leastToRightIndex = i + 1;
        for (int j = i + 2; j < n; j++) {
            if (index[j] < index[leastToRightIndex] && index[j] > index[i]) {
                leastToRightIndex = j;
            }
        }

        // switch dip element with least greater element to its
        // right
        int t = index[i];
        index[i] = index[leastToRightIndex];
        index[leastToRightIndex] = t;

        if (m - 1 > i) {
            // reverse the elements to the right of the dip
            reverseAfter(i);
            // reverse the elements to the right of m - 1
            reverseAfter(m - 1);
        }

    }

    /**
     * @return Object, the next permutation of the original Object array.
     * <p>
     * Actually, an array of Objects is returned. The declaration must say just
     * "Object", since the Permutations class implements Iterator, which
     * declares that the "next()" returns a plain Object. Users must cast the
     * returned object to (Object[]).
     */
    @Override
    public T[] next() {
        if (!hasMore) {
            throw new NoSuchElementException();
        }

        T[] out = Arrays.copyOf(inArray, n);
        for (int i = 0; i < m; i++) {
            out[i] = inArray[index[i]];
        }

        moveIndex();
        return out;
    }

    /**
     * Reverse the index elements to the right of the specified index.
     *
     * @param i
     */
    protected final void reverseAfter(int i) {

        int start = i + 1;
        int end = n - 1;
        while (start < end) {
            int t = index[start];
            index[start] = index[end];
            index[end] = t;
            start++;
            end--;
        }

    }

    /**
     * @return int the index of the first element from the right that is less
     * than its neighbor on the right.
     */
    protected int rightmostDip() {
        for (int i = n - 2; i >= 0; i--) {
            if (index[i] < index[i + 1]) {
                return i;
            }
        }
        return -1;

    }

    private static final Logger LOG
            = Logger.getLogger(Permutations.class.getName());

}
