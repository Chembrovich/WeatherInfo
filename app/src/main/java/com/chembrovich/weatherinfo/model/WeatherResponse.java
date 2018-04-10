package com.chembrovich.weatherinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {
    @SerializedName("cod")
    @Expose
    private String code;

    @SerializedName("list")
    @Expose
    private List<WeatherList> weatherList;

    @SerializedName("city")
    @Expose
    private City city;

    public String getCode() {
        return code;
    }

    public List<WeatherList> getWeatherList() {
        return weatherList;
    }

    public City getCity() {
        return city;
    }
}
