package com.chembrovich.weatherinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainParameters {
    @SerializedName("temp")
    @Expose
    private double temperature;

    @SerializedName("temp_min")
    @Expose
    private double tempMin;

    @SerializedName("temp_max")
    @Expose
    private double tempMax;

    @SerializedName("pressure")
    @Expose
    private double pressure;

    @SerializedName("sea_level")
    @Expose
    private double seaLevel;

    @SerializedName("grnd_level")
    @Expose
    private double groundLevel;

    @SerializedName("humidity")
    @Expose
    private int humidity;

    @SerializedName("temp_kf")
    @Expose
    private double tempKf;

    public MainParameters(double temperature, int humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getTempMin() {
        return tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public double getPressure() {
        return pressure;
    }

    public double getSeaLevel() {
        return seaLevel;
    }

    public double getGroundLevel() {
        return groundLevel;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getTempKf() {
        return tempKf;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setSeaLevel(double seaLevel) {
        this.seaLevel = seaLevel;
    }

    public void setGroundLevel(double groundLevel) {
        this.groundLevel = groundLevel;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setTempKf(double tempKf) {
        this.tempKf = tempKf;
    }
}
