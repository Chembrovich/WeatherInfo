package com.chembrovich.weatherinfo.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "weather_item")
public class WeatherDbEntity {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "forecast_time")
    private int forecastTime;

    @ColumnInfo(name = "state")
    private String state;

    @ColumnInfo(name = "image_id")
    private String imageId;

    @ColumnInfo(name = "temperature")
    private double temperature;

    @ColumnInfo(name = "wind_speed")
    private double windSpeed;

    @ColumnInfo(name = "humidity")
    private int humidity;

    @ColumnInfo(name = "cloudiness")
    private int cloudiness;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getForecastTime() {
        return forecastTime;
    }

    public void setForecastTime(int forecastTime) {
        this.forecastTime = forecastTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(int cloudiness) {
        this.cloudiness = cloudiness;
    }
}
