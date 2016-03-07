package com.android.tusharg.sunshine.model;

import java.io.Serializable;

/**
 * Created by tushar on 07/03/16.
 */
public class Temperature implements Serializable {
    private double min;

    private double eve;

    private double max;

    private double morn;

    private double night;

    private double day;

    public double getDay() {
        return day;
    }

    public void setDay(double day) {
        this.day = day;
    }

    public double getEve() {
        return eve;
    }

    public void setEve(double eve) {
        this.eve = eve;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMorn() {
        return morn;
    }

    public void setMorn(double morn) {
        this.morn = morn;
    }

    public double getNight() {
        return night;
    }

    public void setNight(double night) {
        this.night = night;
    }
}
