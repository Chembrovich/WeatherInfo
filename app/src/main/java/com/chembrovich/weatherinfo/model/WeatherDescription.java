package com.chembrovich.weatherinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherDescription {

    @SerializedName("id")
    @Expose
    public Integer id;

    @SerializedName("main")
    @Expose
    public String state;

    @SerializedName("description")
    @Expose
    public String description;

    @SerializedName("icon")
    @Expose
    public String icon;
}
