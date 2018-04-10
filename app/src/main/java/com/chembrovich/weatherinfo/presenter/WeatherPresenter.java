package com.chembrovich.weatherinfo.presenter;

import com.chembrovich.weatherinfo.presenter.interfaces.WeatherPresenterInterface;
import com.chembrovich.weatherinfo.view.interfaces.WeatherViewInterface;

public class WeatherPresenter implements WeatherPresenterInterface{
    private WeatherViewInterface view;

    public WeatherPresenter(WeatherViewInterface view) {
        this.view = view;
    }
}
