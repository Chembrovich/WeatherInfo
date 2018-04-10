package com.chembrovich.weatherinfo.presenter.interfaces;

import com.chembrovich.weatherinfo.model.WeatherState;
import com.chembrovich.weatherinfo.view.interfaces.WeatherViewInterface;

public interface WeatherPresenterInterface {
    void attachView(WeatherViewInterface view);

    int getWeatherListSize();

    String getCity();

    String getCountry();

    WeatherState getCurrentStateImageDescription();

    String getCurrentStateAndWeather();

    String getListItemWeatherState(int position);

    WeatherState getListItemImageDescription(int position);

    String getListItemWeekDay(int position);

    String getListItemDayWithMonth(int position);

    String getListItemTemperature(int position);

    String getListItemWindSpeed(int position);

    String getListItemHumidity(int position);

    String getListItemCloudiness(int position);

    void detachView();
}
