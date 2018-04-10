package com.chembrovich.weatherinfo.presenter.interfaces;

import com.chembrovich.weatherinfo.model.WeatherResponse;

public interface GetWeatherCallback {
    void weatherResponseIsReceived(WeatherResponse response);

    void onFailure();
}
