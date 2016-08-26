package com.compiler.tourpanse.service;

import com.compiler.tourpanse.pojo.CurrentWeatherResponse;
import com.compiler.tourpanse.pojo.WeatherForecaseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by User on 8/4/2016.
 */
public interface WeatherServiceApi {
    @GET
    Call<CurrentWeatherResponse> getAllWeather(@Url String userUrl);
    @GET
    Call<WeatherForecaseResponse> getAllWeatherForecast(@Url String url);
}
