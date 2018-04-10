package com.chembrovich.weatherinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherDescription {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("main")
    @Expose
    private String state;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("icon")
    @Expose
    private String icon;

    public int getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
