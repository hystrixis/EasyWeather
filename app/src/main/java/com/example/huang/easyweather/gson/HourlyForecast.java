package com.example.huang.easyweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by huang on 2017/6/3.
 */

public class HourlyForecast {
    public String date; //每小时
    @SerializedName("cond")
    public Cond cond;//天气状况
    @SerializedName("tmp")
    public String temperature;//温度
    public class Cond{
        @SerializedName("txt")
        public String cond;
    }
}
