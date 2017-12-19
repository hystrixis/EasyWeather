package com.example.huang.easyweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by huang on 2017/4/22.
 */

public class Forecast {
    public String date;//预报日期

    @SerializedName("tmp")
    public Temperature temperature;//包括最高和最低气温
    @SerializedName("cond")
    public More more;
    public class Temperature{
        public String max;
        public String min;
    }
    public class More{
        @SerializedName("txt_d")
        public String info;//白天天气状况描述
    }
}
