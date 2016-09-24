package com.matrixpeckham.parse.examples.cloning;

import com.matrixpeckham.parse.utensil.PubliclyCloneable;
import java.util.logging.Logger;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 *
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose,
 * including the implied warranty of merchantability.
 */
/**
 * This class shows a typical copy() method.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class Course implements PubliclyCloneable<Course> {

    /**
     *
     */
    protected Professor professor;

    /**
     *
     */
    protected Textbook textbook;

    /**
     * Return a copy of this object.
     *
     * @return a copy of this object
     */
    @Override
    public Course copy() {
        Course copy = new Course();
        copy.setProfessor(professor.copy());
        copy.setTextbook(textbook.copy());
        return copy;
    }

    /**
     * Get the professor.
     *
     * @return the professor
     */
    public Professor getProfessor() {
        return professor;
    }

    /**
     * Get the textbook.
     *
     * @return the textbook
     */
    public Textbook getTextbook() {
        return textbook;
    }

    /**
     * Set the professor.
     *
     * @param professor professor
     */
    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    /**
     * Set the textbook.
     *
     * @param textbook textbook
     */
    public void setTextbook(Textbook textbook) {
        this.textbook = textbook;
    }

    private static final Logger LOG = Logger.getLogger(Course.class.getName());

}
