package com.android.tusharg.sunshine.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tushar on 07/03/16.
 */
public class WeatherData implements Serializable {

    private City city;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public ArrayList<DayInfo> getList() {
        return list;
    }

    public void setList(ArrayList<DayInfo> list) {
        this.list = list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String cod;
    private String message;
    private int cnt;

    private ArrayList<DayInfo> list;
}
