package com.example.huang.easyweather.gson;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by huang on 2017/4/22.
 */

public class Weather {
    public String status;
    public Basic basic;
    public AQI aqi;
    public HourlyForecast hourlyForecast;
    public Now now;
    public Suggestion suggestion;
    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
    @SerializedName("hourly_forecast")
    public List<HourlyForecast> hourlyForecastList;
}
