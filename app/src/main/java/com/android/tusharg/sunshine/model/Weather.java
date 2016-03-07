package com.android.tusharg.sunshine.model;

import java.io.Serializable;

/**
 * Created by tushar on 07/03/16.
 */
public class Weather implements Serializable {
    private String id;

    private String icon;

    private String description;

    private String main;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}
