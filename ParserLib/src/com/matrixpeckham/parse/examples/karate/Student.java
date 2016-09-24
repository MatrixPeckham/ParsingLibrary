package com.matrixpeckham.parse.examples.karate;

import java.util.logging.Logger;

/**
 * KaratePuzzle uses this class as a data structure.
 */
public class Student {

    /**
     *
     */
    public String firstName;

    /**
     *
     */
    public String lastName;

    /**
     *
     */
    public String specialty;

    /**
     * Create a Student with the specified first name.
     *
     * @param firstName java.lang.String
     */
    public Student(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return String, a textual description of the Student
     */
    @Override
    public String toString() {
        return firstName + " " + lastName + ": " + specialty;
    }

    private static final Logger LOG = Logger.getLogger(Student.class.getName());
}
