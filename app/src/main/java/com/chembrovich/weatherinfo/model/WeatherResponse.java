package com.chembrovich.weatherinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {
    @SerializedName("cod")
    @Expose
    public String code;

    @SerializedName("weatherList")
    @Expose
    public List<WeatherList> weatherList;

    @SerializedName("city")
    @Expose
    public City city;
}
