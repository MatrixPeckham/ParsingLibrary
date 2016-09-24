package com.matrixpeckham.parse.combinatorics;

import java.util.logging.Logger;

/**
 * Insert the type's description here. Creation date: (7/3/2001 5:24:34 AM)
 *
 * @author:
 */
class ShowNewPermutations {

    /**
     * Insert the method's description here. Creation date: (7/3/2001 5:24:56
     * AM)
     *
     * @param args java.lang.String[]
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
