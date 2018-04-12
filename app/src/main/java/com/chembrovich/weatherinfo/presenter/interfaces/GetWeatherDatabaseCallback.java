package com.chembrovich.weatherinfo.presenter.interfaces;

import com.chembrovich.weatherinfo.database.WeatherDbEntity;

import java.util.List;

public interface GetWeatherDatabaseCallback {
    void weatherIsReceivedFromDatabase(List<WeatherDbEntity> weatherEntities);

}
