package com.chembrovich.weatherinfo.database;

import android.os.AsyncTask;

import java.util.List;

public class WeatherAsyncTask extends AsyncTask<Void, Void, List<WeatherDbEntity>> {
    private WeatherDatabase database;
    private OnWeatherEntitiesReceivedCallback callback;

    WeatherAsyncTask(WeatherDatabase database,OnWeatherEntitiesReceivedCallback callback) {
        this.database = database;
        this.callback = callback;
    }

    @Override
    protected List<WeatherDbEntity> doInBackground(Void... voids) {
        return database.weatherDao().getAll();
    }

    @Override
    protected void onPostExecute(List<WeatherDbEntity> weatherEntities) {
        callback.onWeatherEntitiesReceived(weatherEntities);
    }

    public interface OnWeatherEntitiesReceivedCallback {
        void onWeatherEntitiesReceived(List<WeatherDbEntity> weatherEntities);
    }
}

