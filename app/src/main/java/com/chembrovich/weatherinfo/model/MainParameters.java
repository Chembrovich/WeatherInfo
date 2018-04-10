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
}
