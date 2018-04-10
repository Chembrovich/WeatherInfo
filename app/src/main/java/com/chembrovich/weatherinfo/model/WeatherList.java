package com.chembrovich.weatherinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherList {
    @SerializedName("dt")
    @Expose
    public Integer weatherForecastTime;

    @SerializedName("main")
    @Expose
    public MainParameters mainParameters;

    @SerializedName("weather")
    @Expose
    public List<WeatherDescription> weatherDescription;

    @SerializedName("clouds")
    @Expose
    public Clouds clouds;

    @SerializedName("wind")
    @Expose
    public Wind wind;

    @SerializedName("dt_txt")
    @Expose
    public String dateAndTimeText;
}