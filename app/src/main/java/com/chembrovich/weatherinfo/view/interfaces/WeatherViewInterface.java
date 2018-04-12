package com.chembrovich.weatherinfo.view.interfaces;

public interface WeatherViewInterface {
    void updateData();

    void makeMessage(String message);

    void requestLocation();

    void stopRefreshing();

    void saveLocationToPreferences(String city, String country);

    String getCityFromPreferences();

    String getCountryFromPreferences();
}
