package com.android.tusharg.sunshine.model;

import java.io.Serializable;

/**
 * Created by tushar on 07/03/16.
 */

public class City implements Serializable {
    private LocationCoord coord;

    private String id;

    private String name;

    private String population;

    private String country;

    public LocationCoord getCoord() {
        return coord;
    }

    public void setCoord(LocationCoord coord) {
        this.coord = coord;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}




