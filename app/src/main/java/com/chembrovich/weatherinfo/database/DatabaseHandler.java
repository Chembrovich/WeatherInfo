package com.chembrovich.weatherinfo.database;

import android.content.Context;

import com.chembrovich.weatherinfo.database.interfaces.DatabaseHandlerInterface;
import com.chembrovich.weatherinfo.presenter.interfaces.GetWeatherDatabaseCallback;

import java.util.List;

public class DatabaseHandler implements DatabaseHandlerInterface,WeatherAsyncTask.OnWeatherEntitiesReceivedCallback {
    private WeatherDatabase database;
    private GetWeatherDatabaseCallback callback;

    public DatabaseHandler(Context appContext, GetWeatherDatabaseCallback callback) {
        database = WeatherDatabase.getInstance(appContext);
        this.callback = callback;
    }

    @Override
    public void getWeatherEntities() {
        new WeatherAsyncTask(database, this).execute();
    }

    @Override
    public void updateAll(final List<WeatherDbEntity> weatherEntities) {
        new Thread(new Runnable() {
            public void run() {
                database.weatherDao().updateAll(weatherEntities);
            }
        }).start();
    }


    @Override
    public void insertAll(final List<WeatherDbEntity> weatherEntities) {
        new Thread(new Runnable() {
            public void run() {
                database.weatherDao().insertAll(weatherEntities);
            }
        }).start();
    }

    @Override
    public void closeDatabase() {
        WeatherDatabase.destroyInstance();
    }

    @Override
    public void onWeatherEntitiesReceived(List<WeatherDbEntity> weatherEntities) {
        callback.weatherIsReceivedFromDatabase(weatherEntities);
    }
}
