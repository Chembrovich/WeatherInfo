package com.chembrovich.weatherinfo.network.interfaces;

import com.chembrovich.weatherinfo.model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiInterface {
    @GET("data/2.5/forecast")
    Call<WeatherResponse> getWeather(@Query("lat") double latitude, @Query("lon") double longitude);
}
