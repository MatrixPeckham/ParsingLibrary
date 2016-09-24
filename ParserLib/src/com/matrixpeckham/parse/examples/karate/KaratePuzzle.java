package com.matrixpeckham.parse.examples.karate;

import com.matrixpeckham.parse.combinatorics.CombinatoricException;
import com.matrixpeckham.parse.combinatorics.Permutations;
import java.util.logging.Logger;

public class KaratePuzzle {

    /**
     *
     */
    protected Student amy = new Student("Amy");

    /**
     *
     */
    protected Student betti = new Student("Betti");

    /**
     *
     */
    protected Student carla = new Student("Carla");

    /**
     *
     */
    protected Student dianne = new Student("Dianne");

    /**
     *
     */
    protected Student[] students = {amy, betti, carla, dianne};

    /**
     *
     */
    protected String[] lastNames = {"Ellis", "Fowler", "Goodrich", "Hightower"};

    /**
     *
     */
    protected String[] specialties = {"Sparring", "Shoot Fighting",
        "Pressure Points", "Childrens"};


    /*
     * Set the student objects' last names and specialties
     * from the provided arrays.
     */
    /**
     *
     * @param lasts
     * @param specs
     */
    protected void assembleStudents(
            Object[] lasts, Object[] specs) {
        for (int i = 0; i < students.length; i++) {
            students[i].lastName = (String) lasts[i];
            students[i].specialty = (String) specs[i];
        }
    }

    /**
     * @return true, if the student objects meet all the clues in the puzzle
     */
    protected boolean cluesVerify() {
        return // Clue 1
                !"Ellis".equals(amy.lastName) && !"Fowler".equals(amy.lastName)
                && // Clue 2
                !"Sparring".equals(carla.specialty) && !"Sparring".equals(
                        dianne.specialty)
                && // Clue 3
                !"Shoot Fighting".equals(studentNamed("Fowler").specialty)
                && !"Pressure Points".equals(studentNamed("Fowler").specialty)
                && // Clue 4
                !"Childrens".equals(dianne.specialty) && // Clue 5
                !"Goodrich".equals(amy.lastName) && !"Pressure Points".equals(
                        amy.specialty)
                && // Clue 6
                !"Fowler".equals(betti.lastName) && // Clue 7
                !"Sparring".equals(studentNamed("Hightower").specialty) && // Clue 4, 1
                !"Ellis".equals(dianne.lastName);
    }

    /**
     * Solve the karate puzzle.
     *
     * @param args
     * @exception CombinatoricException Shouldn't happen
     */
    public static void main(String[] args)
            throws CombinatoricException {

        new KaratePuzzle().solve();
    }
    /*
     * Display the student objects.
     */

    /**
     *
     */
    protected void showStudents() {
        for (Student student : students) {
            System.out.println("\t" + student + " ");
        }
    }

    /**
     * Generate all permutations of last names and specialties, and check each
     * arrangement to see if it passes all the clues that the puzzle specifies.
     *
     * @exception CombinatoricException Shouldn't happen
     */
    protected void solve() throws CombinatoricException {
        String[] lasts, specs;
        Permutations<String> lastNamePerm, specPerm;
        lastNamePerm = new Permutations<>(lastNames);

        while (lastNamePerm.hasNext()) {
            lasts = lastNamePerm.next();
            specPerm = new Permutations<>(specialties);

            while (specPerm.hasNext()) {
                specs = specPerm.next();
                assembleStudents(lasts, specs);
                if (cluesVerify()) {
                    System.out.println("Solution:");
                    showStudents();
                }
            }
        }
    }

    /**
     * Return the Student who has the given last name
     *
     * @return Student, the Student with the given last name
     *
     * @param lastName String
     */
    protected Student studentNamed(String lastName) {
        for (Student student : students) {
            if (student.lastName.equals(lastName)) {
                return student;
            }
        }
        throw new InternalError("Bad last name");
    }

    private static final Logger LOG
            = Logger.getLogger(KaratePuzzle.class.getName());

}
