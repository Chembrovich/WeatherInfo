package com.chembrovich.weatherinfo.presenter;

import com.chembrovich.weatherinfo.database.DatabaseHandler;
import com.chembrovich.weatherinfo.database.WeatherDbEntity;
import com.chembrovich.weatherinfo.database.interfaces.DatabaseHandlerInterface;
import com.chembrovich.weatherinfo.interactor.interfaces.WeatherInteractorInterface;
import com.chembrovich.weatherinfo.model.City;
import com.chembrovich.weatherinfo.model.Clouds;
import com.chembrovich.weatherinfo.model.MainParameters;
import com.chembrovich.weatherinfo.model.WeatherDescription;
import com.chembrovich.weatherinfo.model.WeatherListItem;
import com.chembrovich.weatherinfo.model.WeatherResponse;
import com.chembrovich.weatherinfo.model.WeatherState;
import com.chembrovich.weatherinfo.model.Wind;
import com.chembrovich.weatherinfo.network.WeatherNetworkHandler;
import com.chembrovich.weatherinfo.presenter.interfaces.GetWeatherDatabaseCallback;
import com.chembrovich.weatherinfo.presenter.interfaces.GetWeatherNetworkCallback;
import com.chembrovich.weatherinfo.presenter.interfaces.WeatherPresenterInterface;
import com.chembrovich.weatherinfo.view.interfaces.WeatherViewInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeatherPresenter implements WeatherPresenterInterface, GetWeatherNetworkCallback,
                                         GetWeatherDatabaseCallback {
    private static final String CELSIUS = "°C";
    private static final String MILES_PER_HOUR = "mph";
    private static final String PERCENT = "%";
    private static final String GPS_IS_DISABLED = "GPS is disabled";
    private static final String DATABASE_ERROR_MESSAGE = "Cannot get weather from database";
    private static final String DATABASE_TAG = DatabaseHandler.class.getName();
    private static final String NETWORK_TAG = WeatherNetworkHandler.class.getName();
    private static final long MILLISECONDS_IN_SECONDS = 1000L;
    private boolean isWeatherNew = false;
    private WeatherViewInterface view;
    private WeatherNetworkHandler networkHandler;
    private WeatherInteractorInterface interactor;
    private DatabaseHandlerInterface databaseHandler;
    private List<WeatherDbEntity> weatherListCache;
    private List<WeatherListItem> weatherList;
    private City city;

    public WeatherPresenter(WeatherInteractorInterface interactor) {
        this.interactor = interactor;
        weatherListCache = new ArrayList<>();
    }

    @Override
    public void attachView(WeatherViewInterface view) {
        this.view = view;
        this.view.requestLocation();
        try {
            databaseHandler = interactor.getDatabaseHandler(this);
            databaseHandler.getWeatherEntities();
        } catch (RuntimeException exception) {
            view.makeMessage(DATABASE_ERROR_MESSAGE);
            view.makeErrorLog(DATABASE_TAG, DATABASE_ERROR_MESSAGE);
        }
        city = new City(view.getCityFromPreferences(),view.getCountryFromPreferences());
        networkHandler = new WeatherNetworkHandler(this);
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

    private long getTimeInMilliseconds(int timeInSeconds) {
        return timeInSeconds * MILLISECONDS_IN_SECONDS;
    }

    @Override
    public String getListItemWeekDay(int position) {
        long time = getTimeInMilliseconds(weatherList.get(position).getWeatherForecastTime());
        Date date = new java.util.Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("EE", Locale.ENGLISH);
        return sdf.format(date);
    }

    @Override
    public String getListItemDayWithTime(int position) {
        long time = getTimeInMilliseconds(weatherList.get(position).getWeatherForecastTime());
        Date date = new java.util.Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM HH:mm", Locale.ENGLISH);
        return sdf.format(date);
    }

    @Override
    public String getListItemTemperature(int position) {
        return getTemperatureWithCelsius(weatherList.get(position).getMainParameters().getTemperature());
    }

    @Override
    public String getListItemWindSpeed(int position) {
        return getWindSpeedWithMph(weatherList.get(position).getWind().getSpeed());
    }

    @Override
    public String getListItemHumidity(int position) {
        return getValueWithPercent(weatherList.get(position).getMainParameters().getHumidity());
    }

    @Override
    public String getListItemCloudiness(int position) {
        return getValueWithPercent(weatherList.get(position).getClouds().getCloudiness());
    }

    @Override
    public void newLocationsIsReceived(double latitude, double longitude) {
        networkHandler.makeRequestToGetWeather(latitude, longitude);
    }

    @Override
    public void gpsPermissionGranted() {
        if (view != null) {
            view.requestLocation();
        }
    }

    @Override
    public void gpsDisabled() {
        if (view != null) {
            view.makeMessage(GPS_IS_DISABLED);
        }
    }

    @Override
    public void gpsEnabled() {
        if (view != null) {
            view.requestLocation();
        }
    }

    @Override
    public void updateLocation() {
        if (view != null) {
            view.requestLocation();
        }
    }

    @Override
    public boolean isWeatherNew() {
        return isWeatherNew;
    }

    @Override
    public void detachView() {
        this.view = null;
        databaseHandler.closeDatabase();
    }

    public void setWeatherList(List<WeatherListItem> weatherList) {
        this.weatherList = weatherList;
    }

    @Override
    public void weatherIsReceivedFromNetwork(WeatherResponse response) {
        setWeatherList(response.getWeatherList());
        this.city = response.getCity();
        isWeatherNew = true;
        if (view != null) {
            view.saveLocationToPreferences(city.getName(), city.getCountryCode());
            view.updateData();
        }
        updateDatabase();
    }

    @Override
    public void onFailure(String message) {
        if (view != null) {
            view.makeMessage(message);
            view.makeErrorLog(NETWORK_TAG, message);
            view.stopRefreshing();
        }
    }

    @Override
    public void weatherIsReceivedFromDatabase(List<WeatherDbEntity> weatherEntities) {
        this.weatherListCache = weatherEntities;
        if (!weatherListCache.isEmpty()) {
            setWeatherListFromCache();
            if (view != null && !isWeatherNew) {
                view.updateData();
            }
        }
    }

    private String getTemperatureWithCelsius(double temperature) {
        return String.valueOf(Math.round(temperature)).concat(CELSIUS);
    }

    private String getWindSpeedWithMph(double windSpeed) {
        return String.valueOf(Math.round(windSpeed)).concat(MILES_PER_HOUR);
    }

    private String getValueWithPercent(int value) {
        return String.valueOf(value) + PERCENT;
    }

    private List<WeatherDbEntity> getCacheListUsingWeatherListFromNetwork() {
        List<WeatherDbEntity> cacheList = new ArrayList<>();
        int id = 0;

        for (WeatherListItem newWeatherItem : weatherList) {
            WeatherDbEntity cacheWeather = new WeatherDbEntity();

            cacheWeather.setId(id);
            cacheWeather.setCloudiness(newWeatherItem.getClouds().getCloudiness());
            cacheWeather.setForecastTime(newWeatherItem.getWeatherForecastTime());
            cacheWeather.setHumidity(newWeatherItem.getMainParameters().getHumidity());
            cacheWeather.setImageId(newWeatherItem.getWeatherDescription().get(0).getIcon());
            cacheWeather.setState(newWeatherItem.getWeatherDescription().get(0).getState());
            cacheWeather.setTemperature(newWeatherItem.getMainParameters().getTemperature());
            cacheWeather.setWindSpeed(newWeatherItem.getWind().getSpeed());

            cacheList.add(cacheWeather);
            id++;
        }
        this.weatherListCache = cacheList;

        return cacheList;
    }

    private void updateDatabase() {
        if (weatherList != null) {
            if (weatherListCache.isEmpty()) {
                databaseHandler.insertAll(getCacheListUsingWeatherListFromNetwork());
            } else {
                databaseHandler.updateAll(getCacheListUsingWeatherListFromNetwork());
            }
        }
    }

    private void setWeatherListFromCache() {
        weatherList = new ArrayList<>();
        for (WeatherDbEntity weatherCache : weatherListCache) {
            WeatherListItem newWeatherItem = new WeatherListItem();

            List<WeatherDescription> description = new ArrayList<>();
            description.add(new WeatherDescription(weatherCache.getState(), weatherCache.getImageId()));
            newWeatherItem.setWeatherDescription(description);

            MainParameters parameters = new MainParameters(weatherCache.getTemperature(),
                    weatherCache.getHumidity());
            newWeatherItem.setMainParameters(parameters);

            Wind wind = new Wind(weatherCache.getWindSpeed());
            newWeatherItem.setWind(wind);

            Clouds clouds = new Clouds(weatherCache.getCloudiness());
            newWeatherItem.setClouds(clouds);

            newWeatherItem.setWeatherForecastTime(weatherCache.getForecastTime());

            weatherList.add(newWeatherItem);
        }
    }

    private WeatherState getWeatherStateByImageId(String imageId) {
        switch (imageId) {
            case "01d":
                return WeatherState.CLEAR_SKY;

            case "01n":
                return WeatherState.CLEAR_SKY_NIGHT;

            case "02d":
                return WeatherState.FEW_CLOUDS;

            case "02n":
                return WeatherState.FEW_CLOUDS_NIGHT;

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
                return WeatherState.RAIN;

            case "10n":
                return WeatherState.RAIN_NIGHT;

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
}
