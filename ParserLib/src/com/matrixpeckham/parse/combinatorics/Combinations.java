package com.matrixpeckham.parse.combinatorics;

import static com.matrixpeckham.parse.combinatorics.Combinatoric.check;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class Combinations<T> implements java.util.Iterator<T[]> {

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
     * Create a Combination to enumerate through all subsets of the supplied
     * Object array, selecting 'm' at a time.
     *
     * @param inArray the group to choose from
     *
     * @param m int the number to select in each choice
     * @throws com.matrixpeckham.parse.combinatorics.CombinatoricException
     */
    public Combinations(T[] inArray, int m)
            throws CombinatoricException {

        this.n = inArray.length;
        this.inArray = Arrays.copyOf(inArray, n);
        this.m = m;

// throw exception unless n >= m >= 0
        check(n, m);

        /**
         * 'index' is an array of ints that keep track of the next combination
         * to return. For example, an index on 5 things taken 3 at a time might
         * contain {0 3 4}. This index will be followed by {1 2 3}. Initially,
         * the index is {0 ... m - 1}.
         */
        index = new int[m];
        for (int i = 0; i < m; i++) {
            index[i] = i;
        }
    }

    /**
     * @return true, unless we have already returned the last combination.
     */
    @Override
    public boolean hasNext() {
        return hasMore;
    }

    /**
     * Move the index forward a notch. The algorithm finds the rightmost index
     * element that can be incremented, increments it, and then changes the
     * elements to the right to each be 1 plus the element on their left.
     * <p>
     * For example, if an index of 5 things taken 3 at a time is at {0 3 4},
     * only the 0 can be incremented without running out of room. The next index
     * is {1, 1+1, 1+2) or {1, 2, 3}. This will be followed by {1, 2, 4}, {1, 3,
     * 4}, and {2, 3, 4}.
     * <p>
     * The algorithm is from "Applied Combinatorics", by Alan Tucker.
     *
     */
    protected void moveIndex() {
        int i = rightmostIndexBelowMax();
        if (i >= 0) {
            index[i] += 1;
            for (int j = i + 1; j < m; j++) {
                index[j] = index[j - 1] + 1;
            }
        } else {
            hasMore = false;
        }
    }

    /**
     * @return java.lang.Object, the next combination from the supplied Object
     * array.
     * <p>
     * Actually, an array of Objects is returned. The declaration must say just
     * "Object", since the Combinations class implements Iterator, which
     * declares that the "next()" returns a plain Object. Users must cast the
     * returned object to (Object[]).
     */
    @Override
    public T[] next() {
        if (!hasMore) {
            throw new NoSuchElementException();
        }

        T[] out = Arrays.copyOf(inArray, m);
        for (int i = 0; i < m; i++) {
            out[i] = inArray[index[i]];
        }

        moveIndex();
        return out;
    }

    /**
     * @return int, the index which can be bumped up.
     */
    protected int rightmostIndexBelowMax() {

        for (int i = m - 1; i >= 0; i--) {
            if (index[i] < n - m + i) {
                return i;
            }
        }
        return -1;
    }

    private static final Logger LOG
            = Logger.getLogger(Combinations.class.getName());

}
