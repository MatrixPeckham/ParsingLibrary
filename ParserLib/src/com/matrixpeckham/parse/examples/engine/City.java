package com.matrixpeckham.parse.examples.engine;

import java.util.logging.Logger;

public class City {

    /**
     *
     */
    public String name;

    /**
     *
     */
    public int altitude;

    /**
     * Constructs a city.
     *
     * @param name the city's name
     *
     * @param altitude the city's altitude
     */
    public City(String name, int altitude) {
        this.name = name;
        this.altitude = altitude;
    }

    private static final Logger LOG = Logger.getLogger(City.class.getName());

}
