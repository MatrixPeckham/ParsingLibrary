package com.matrixpeckham.parse.combinatorics;

import java.util.Comparator;
import java.util.logging.Logger;

public class NewPermutations<T extends Comparable<T>> {

    /**
     *
     */
    public Comparator<T> comparableComparator;

    /**
     * Insert the method's description here. Creation date: (7/3/2001 5:48:25
     * AM)
     *
     * @return java.util.Comparator
     */
    public Comparator<T> comparableComparator() {
        if (comparableComparator == null) {
            comparableComparator = (T o1, T o2) -> {
                return o1.compareTo(o2);
            };
        }
        return comparableComparator;
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
     * @param array
     * @return
     */
    public boolean moveIndex(T[] array) {
        return moveIndex(array, comparableComparator());
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
     * @param array
     * @param c
     * @return
     */
    public boolean moveIndex(T[] array, Comparator<T> c) {

        // find the index of the first element that dips
        int dip = rightmostDip(array, c);
        if (dip < 0) {
            return false;

        }

        // find the least greater element to the right of the dip
        int leastToRightIndex = dip + 1;
        for (int j = dip + 2; j < array.length; j++) {
            if ((c.compare(array[j], array[leastToRightIndex]) < 0) && (c.
                    compare(array[j], array[dip]) > 0)) {
                leastToRightIndex = j;
            }
        }

        // switch dip element with least greater element to its
        // right
        T o = array[dip];
        array[dip] = array[leastToRightIndex];
        array[leastToRightIndex] = o;

        //if (m - 1 > i) {
        //// reverse the elements to the right of the dip
        //reverseAfter(i);
        //// reverse the elements to the right of m - 1
        //reverseAfter(m - 1);
        //}
        reverseAfter(array, dip);
        return true;
    }

    /**
     * Reverse the index elements to the right of the specified index.
     */
    private void reverseAfter(T[] array, int i) {
        int start = i + 1;
        int end = array.length - 1;
        while (start < end) {
            T o = array[start];
            array[start] = array[end];
            array[end] = o;
            start++;
            end--;
        }
    }

    /**
     * @return int the index of the first element from the right that is less
     * than its neighbor on the right.
     */
    private int rightmostDip(T[] array, Comparator<T> c) {
        for (int i = array.length - 2; i >= 0; i--) {
            if (c.compare(array[i], array[i + 1]) < 0) {
                return i;
            }
        }
        return -1;

    }

    private static final Logger LOG
            = Logger.getLogger(NewPermutations.class.getName());

}
