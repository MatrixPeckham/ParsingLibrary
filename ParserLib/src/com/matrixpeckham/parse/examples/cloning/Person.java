package com.matrixpeckham.parse.examples.cloning;

import java.util.logging.Logger;

/**
 * Demonstrates Cloneable
 */
public class Person implements Cloneable {

    String name;

    Person spouse;

    /**
     * This method was created by Steve Metsker
     *
     * @param name java.lang.String
     */
    public Person(String name) {
        this.name = name;
    }

    /**
     * This method was created by Steve Metsker
     *
     * @return java.lang.Object
     *
     * @throws java.lang.CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    /**
     * This method was created by Steve Metsker
     *
     * @return java.lang.String
     */
    public String getName() {
        return name;
    }

    /**
     * This method was created by Steve Metsker
     *
     * @return com.matrixpeckham.parse.examples.cloning.Person
     */
    public Person getSpouse() {
        return spouse;
    }

    /**
     * This method was created by Steve Metsker
     *
     * @param name java.lang.String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was created by Steve Metsker
     *
     * @param spouse com.matrixpeckham.parse.examples.cloning.Person
     */
    public void setSpouse(Person spouse) {
        this.spouse = spouse;
    }

    private static final Logger LOG = Logger.getLogger(Person.class.getName());

}
