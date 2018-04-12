package com.chembrovich.weatherinfo.database.interfaces;

import com.chembrovich.weatherinfo.database.WeatherDbEntity;

import java.util.List;

public interface DatabaseHandlerInterface {
    void getWeatherEntities();

    void insertAll(List<WeatherDbEntity> weatherEntities);

    void updateAll(List<WeatherDbEntity> weatherEntities);

    void closeDatabase();
}
