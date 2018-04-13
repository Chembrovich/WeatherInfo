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
    private List<WeatherListItem> weatherList;

    @SerializedName("city")
    @Expose
    private City city;

    public String getCode() {
        return code;
    }

    public List<WeatherListItem> getWeatherList() {
        return weatherList;
    }

    public City getCity() {
        return city;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setWeatherList(List<WeatherListItem> weatherList) {
        this.weatherList = weatherList;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
