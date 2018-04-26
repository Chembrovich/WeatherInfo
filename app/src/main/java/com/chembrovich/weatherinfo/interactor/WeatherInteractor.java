package com.chembrovich.weatherinfo.interactor;

import android.content.Context;

import com.chembrovich.weatherinfo.database.DatabaseHandler;
import com.chembrovich.weatherinfo.database.interfaces.DatabaseHandlerInterface;
import com.chembrovich.weatherinfo.interactor.interfaces.WeatherInteractorInterface;
import com.chembrovich.weatherinfo.presenter.interfaces.GetWeatherDatabaseCallback;

public class WeatherInteractor implements WeatherInteractorInterface {
    private Context appContext;

    public WeatherInteractor(Context appContext) {
        this.appContext = appContext;
    }

    @Override
    public DatabaseHandlerInterface getDatabaseHandler(GetWeatherDatabaseCallback callback) {
        return new DatabaseHandler(/*appContext,*/ callback);
    }
}
