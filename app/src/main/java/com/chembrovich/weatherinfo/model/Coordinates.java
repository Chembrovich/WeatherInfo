package com.chembrovich.weatherinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coordinates {
    @SerializedName("lat")
    @Expose
    public Double latitude;

    @SerializedName("lon")
    @Expose
    public Double longitude;
}
