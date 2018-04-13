package com.chembrovich.weatherinfo.network;

import android.support.annotation.NonNull;

import com.chembrovich.weatherinfo.model.WeatherResponse;
import com.chembrovich.weatherinfo.network.interfaces.WeatherApiInterface;
import com.chembrovich.weatherinfo.presenter.interfaces.GetWeatherNetworkCallback;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherNetworkHandler {
    private static final String WEATHER_API_BASE_URL = "http://api.openweathermap.org/";
    private static final String API_KEY = "e2855cc74ef5ccb2b9a7bc4e0e0c81bf";
    private static final String METRIC = "metric";
    private static final String API_KEY_QUERY_PARAMETER = "APPID";
    private static final String UNITS_QUERY_PARAMETER = "units";
    private static final String THERE_IS_NO_INTERNET_CONNECTION = "There is no internet connection";
    private static final String NETWORK_ERROR_MESSAGE = "Network error";
    private static final String SERVER_ERROR_MESSAGE = "Server error";
    private static final int SERVER_ERROR_FIRST_CODE = 500;
    private static final int SERVER_ERROR_LAST_CODE = 599;

    private WeatherApiInterface weatherApi;
    private GetWeatherNetworkCallback callback;

    public WeatherNetworkHandler(GetWeatherNetworkCallback callback) {
        this.callback = callback;

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                        Request original = chain.request();
                        HttpUrl httpUrl = original.url();
                        HttpUrl newHttpUrl = httpUrl.newBuilder()
                                            .addQueryParameter(API_KEY_QUERY_PARAMETER, API_KEY).build();
                        newHttpUrl = newHttpUrl.newBuilder()
                                    .addQueryParameter(UNITS_QUERY_PARAMETER, METRIC).build();
                        Request.Builder requestBuilder = original.newBuilder().url(newHttpUrl);
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WEATHER_API_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherApi = retrofit.create(WeatherApiInterface.class);
    }

    public void makeRequestToGetWeather(double latitude, double longitude) {
        weatherApi.getWeather(latitude, longitude).enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    callback.weatherIsReceivedFromNetwork(response.body());
                } else {
                    if (isServerError(response.code())) {
                        callback.onFailure(SERVER_ERROR_MESSAGE);
                    } else {
                        callback.onFailure(NETWORK_ERROR_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                callback.onFailure(THERE_IS_NO_INTERNET_CONNECTION);
            }
        });
    }

    private boolean isServerError(int code) {
        return code >= SERVER_ERROR_FIRST_CODE && code <= SERVER_ERROR_LAST_CODE;
    }
}
