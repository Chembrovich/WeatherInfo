package com.chembrovich.weatherinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherListItem {
    @SerializedName("dt")
    @Expose
    private int weatherForecastTime;

    @SerializedName("main")
    @Expose
    private MainParameters mainParameters;

    @SerializedName("weather")
    @Expose
    private List<WeatherDescription> weatherDescription;

    @SerializedName("clouds")
    @Expose
    private Clouds clouds;

    @SerializedName("wind")
    @Expose
    private Wind wind;

    @SerializedName("dt_txt")
    @Expose
    private String dateAndTimeText;

    public int getWeatherForecastTime() {
        return weatherForecastTime;
    }

    public MainParameters getMainParameters() {
        return mainParameters;
    }

    public List<WeatherDescription> getWeatherDescription() {
        return weatherDescription;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public String getDateAndTimeText() {
        return dateAndTimeText;
    }

    public void setWeatherForecastTime(int weatherForecastTime) {
        this.weatherForecastTime = weatherForecastTime;
    }

    public void setMainParameters(MainParameters mainParameters) {
        this.mainParameters = mainParameters;
    }

    public void setWeatherDescription(List<WeatherDescription> weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public void setDateAndTimeText(String dateAndTimeText) {
        this.dateAndTimeText = dateAndTimeText;
    }
}