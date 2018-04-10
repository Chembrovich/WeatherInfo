package com.chembrovich.weatherinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainParameters {
    @SerializedName("temp")
    @Expose
    public Double currentTemperature;

    @SerializedName("temp_min")
    @Expose
    public Double tempMin;

    @SerializedName("temp_max")
    @Expose
    public Double tempMax;

    @SerializedName("pressure")
    @Expose
    public Integer pressure;

    @SerializedName("sea_level")
    @Expose
    public Double seaLevel;

    @SerializedName("grnd_level")
    @Expose
    public Integer grndLevel;

    @SerializedName("humidity")
    @Expose
    public Integer humidity;

    @SerializedName("temp_kf")
    @Expose
    public Double tempKf;
}
