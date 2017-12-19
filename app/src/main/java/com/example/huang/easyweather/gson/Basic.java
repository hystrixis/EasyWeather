package com.example.huang.easyweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by huang on 2017/4/22.
 */

public class Basic {
    @SerializedName("city")
    public String cityZh;
    @SerializedName("id")
    public String cityId;
    public Update update;
    public class Update{
        @SerializedName("loc")
        public String updateTime;
    }
}
