package com.chembrovich.weatherinfo.presenter;

import com.chembrovich.weatherinfo.model.City;
import com.chembrovich.weatherinfo.model.WeatherList;
import com.chembrovich.weatherinfo.model.WeatherResponse;
import com.chembrovich.weatherinfo.model.WeatherState;
import com.chembrovich.weatherinfo.network.WeatherNetworkHandler;
import com.chembrovich.weatherinfo.presenter.interfaces.GetWeatherCallback;
import com.chembrovich.weatherinfo.presenter.interfaces.WeatherPresenterInterface;
import com.chembrovich.weatherinfo.view.interfaces.WeatherViewInterface;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeatherPresenter implements WeatherPresenterInterface, GetWeatherCallback {
    private static final String CELSIUS = "°C";
    private static final String MILES_PER_HOUR = "mph";
    private static final String PERCENT = "%";

    private WeatherViewInterface view;
    private WeatherNetworkHandler networkHandler;
    //private WeatherResponse response;
    private List<WeatherList> weatherList;
    private City city;

    public WeatherPresenter() {

    }

    @Override
    public void attachView(WeatherViewInterface view) {
        this.view = view;
        networkHandler = new WeatherNetworkHandler(this);
        networkHandler.makeRequestToGetWeather();
    }

    @Override
    public String getCity() {
        return city.getName();
    }

    @Override
    public String getCountry() {
        return city.getCountryCode();
    }

    @Override
    public WeatherState getCurrentStateImageDescription() {
        String imageId = weatherList.get(0).getWeatherDescription().get(0).getIcon();
        return getWeatherStateByImageId(imageId);
    }

    @Override
    public String getCurrentStateAndWeather() {
        String currentState = weatherList.get(0).getWeatherDescription().get(0).getState().concat(" ");
        String currentTemperature = getTemperatureWithCelsius(weatherList.get(0).getMainParameters()
                                                              .getTemperature());

        return currentState.concat(currentTemperature);
    }

    @Override
    public int getWeatherListSize() {
        if (weatherList != null) {
            return weatherList.size();
        } else {
            return 0;
        }
    }

    @Override
    public String getListItemWeatherState(int position) {
        return weatherList.get(position).getWeatherDescription().get(0).getState();
    }

    @Override
    public WeatherState getListItemImageDescription(int position) {
        String imageId = weatherList.get(position).getWeatherDescription().get(0).getIcon();
        return getWeatherStateByImageId(imageId);
    }

    private WeatherState getWeatherStateByImageId(String imageId) {
        switch (imageId) {
            case "01d":
            case "01n":
                return WeatherState.CLEAR_SKY;

            case "02d":
            case "02n":
                return WeatherState.FEW_CLOUDS;

            case "03d":
            case "03n":
                return WeatherState.SCATTERED_CLOUDS;

            case "04d":
            case "04n":
                return WeatherState.BROKEN_CLOUDS;

            case "09d":
            case "09n":
                return WeatherState.SHOWER_RAIN;

            case "10d":
            case "10n":
                return WeatherState.RAIN;

            case "11d":
            case "11n":
                return WeatherState.THUNDERSTORM;

            case "13d":
            case "13n":
                return WeatherState.SNOW;

            case "50d":
            case "50n":
                return WeatherState.MIST;

            default:
                return WeatherState.CLEAR_SKY;
        }
    }

    @Override
    public String getListItemWeekDay(int position) {
        Date date = new java.util.Date(weatherList.get(position).getWeatherForecastTime() * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("EE", Locale.ENGLISH);
        return sdf.format(date);
    }

    @Override
    public String getListItemDayWithMonth(int position) {
        Date date = new java.util.Date(weatherList.get(position).getWeatherForecastTime() * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM HH:mm", Locale.ENGLISH);
        /*sdf.setTimeZone(TimeZone.getDefault());
        String formattedDate = sdf.format(date);*/
        return sdf.format(date);
    }

    @Override
    public String getListItemTemperature(int position) {
        return getTemperatureWithCelsius(weatherList.get(position).getMainParameters().getTemperature());
    }

    private String getTemperatureWithCelsius(double temperature) {
        return String.valueOf(temperature).concat(CELSIUS);
    }

    @Override
    public String getListItemWindSpeed(int position) {
        return getWindSpeedWithMph(weatherList.get(position).getWind().getSpeed());
    }

    private String getWindSpeedWithMph(double windSpeed) {
        return String.valueOf(windSpeed).concat(MILES_PER_HOUR);
    }

    @Override
    public String getListItemHumidity(int position) {
        return getValueWithPercent(weatherList.get(position).getMainParameters().getHumidity());
    }

    @Override
    public String getListItemCloudiness(int position) {
        return getValueWithPercent(weatherList.get(position).getClouds().getCloudiness());
    }

    private String getValueWithPercent(int value) {
        return String.valueOf(value) + PERCENT;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void weatherResponseIsReceived(WeatherResponse response) {
//        this.response = response;
        this.weatherList = response.getWeatherList();
        this.city = response.getCity();
        view.updateData();
    }

    @Override
    public void onFailure() {

    }
}
