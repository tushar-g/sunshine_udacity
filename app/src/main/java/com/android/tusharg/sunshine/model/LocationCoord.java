package com.android.tusharg.sunshine.model;

import java.io.Serializable;

/**
 * Created by tushar on 07/03/16.
 */
public class LocationCoord implements Serializable {

    private String lon;

    private String lat;

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}

