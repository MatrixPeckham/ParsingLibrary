package com.matrixpeckham.parse.combinatorics;

import java.util.logging.Logger;

/**
 * Demonstration class for the new Permutation code
 */
class ShowNewPermutations {

    /**
     * Main Method
     */
    public static void main(String[] args) {
        String[] children = {"Leonardo", "Monica", "Nathan", "Olivia"};
        boolean more;
        do {
            for (String children1 : children) {
                System.out.print(children1 + " ");
            }
            System.out.println();
            NewPermutations<String> np = new NewPermutations<>();
            more = np.moveIndex(children);
        } while (more);
    }

    private static final Logger LOG
            = Logger.getLogger(ShowNewPermutations.class.getName());

}
