package com.matrixpeckham.parse.combinatorics;

import static java.lang.Math.min;

import java.math.BigInteger;
import java.util.logging.Logger;

/**
 * Class with static methods to calculate permutation counts.
 */
public class Combinatoric {

    /**
     * Calculates n choose m.
     *
     * @return BigInteger, the number of unordered subsets of m objects chosen
     *         from a group of n objects.
     *
     * @param n int
     *
     * @param m int
     *
     * @exception CombinatoricException unless n &gt;= m &gt;= 0
     */
    public static BigInteger c(int n, int m)
            throws CombinatoricException {

        check(n, m);
        int r = min(m, n - m);
        return p(n, r).divide(factorial(r));
    }

    /**
     * Check that 0 &lt;= m &lt;= n.
     *
     * @param n int
     *
     * @param m int
     *
     * @exception CombinatoricException unless n &gt;= m &gt;= 0
     */
    static void check(int n, int m)
            throws CombinatoricException {
        if (n < 0) {
            throw new CombinatoricException(
                    "n, the number of items, must be " + "greater than 0");
        }
        if (n < m) {
            throw new CombinatoricException(
                    "n, the number of items, must be >= m, "
                    + "the number selected");
        }
        if (m < 0) {
            throw new CombinatoricException(
                    "m, the number of selected items, must be >= 0");
        }
    }

    /**
     * Calculates the factorial of a number.
     *
     * @return BigInteger, the product of the numbers 1 ... n
     *
     * @param n int
     *
     * @exception CombinatoricException unless n &lt;= 0
     */
    public static BigInteger factorial(int n)
            throws CombinatoricException {
        if (n < 0) {
            throw new CombinatoricException("n must be >= 0");
        }
        BigInteger factorial = new BigInteger(new byte[]{1});
        for (int i = n; i > 1; i--) {
            factorial = factorial.multiply(
                    new BigInteger(new byte[]{(byte) i}));
        }
        return factorial;
    }

    /**
     * Calculate the number of permutations of n objects.
     *
     * @return BigInteger, the number of possible ways of ordering n objects
     *
     * @param n int
     *
     * @exception CombinatoricException unless n &gt;= 0
     */
    public static BigInteger p(int n)
            throws CombinatoricException {

        return factorial(n);
    }

    /**
     * Calculates the number of permutations of m objects chosen from n items.
     *
     * @return BigInteger, the number of possible arrangements, or orderings, of
     *         m objects chosen from a group of n objects.
     *
     * @param n int
     *
     * @param m int
     *
     * @exception CombinatoricException unless n &gt;= m &gt;= 0
     */
    public static BigInteger p(int n, int m)
            throws CombinatoricException {

        check(n, m);

        BigInteger product = new BigInteger(new byte[]{1});
        for (int i = n; i > n - m; i--) {
            product = product.multiply(
                    new BigInteger(new byte[]{(byte) i}));
        }
        return product;
    }

    private static final Logger LOG
            = Logger.getLogger(Combinatoric.class.getName());

}
