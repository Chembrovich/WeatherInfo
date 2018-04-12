package com.chembrovich.weatherinfo.presenter.interfaces;

import com.chembrovich.weatherinfo.model.WeatherResponse;

public interface GetWeatherNetworkCallback {
    void weatherIsReceivedFromNetwork(WeatherResponse response);

    void onFailure();
}
