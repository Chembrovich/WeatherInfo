package com.chembrovich.weatherinfo.interactor.interfaces;

import com.chembrovich.weatherinfo.database.interfaces.DatabaseHandlerInterface;
import com.chembrovich.weatherinfo.presenter.interfaces.GetWeatherDatabaseCallback;

public interface WeatherInteractorInterface {
    DatabaseHandlerInterface getDatabaseHandler(GetWeatherDatabaseCallback presenter);
}
