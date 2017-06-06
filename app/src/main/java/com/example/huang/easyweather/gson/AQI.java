package com.example.huang.easyweather.gson;

/**
 * Created by huang on 2017/4/22.
 */

public class AQI {
    public AQICity city;
    public class AQICity{
        public String aqi;//空气质量指数
        public String pm25;
        public String qlty;//空气质量
    }
}
