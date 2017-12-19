package com.example.huang.easyweather.gson;

/**
 * Created by huang on 2017/4/22.
 */

public class AQI {
    public AQICity city;
    public class AQICity{
        public String aqi;//空气质量指数
        public String co;//一氧化碳
        public String no2;//二氧化氮
        public String o3;//臭氧
        public String pm10;
        public String pm25;
        public String qlty;//空气质量
        public String so2;//二氧化硫
    }
}
