package com.example.huang.easyweather.data;

/**
 * Created by huang on 2017/6/5.
 */

public class HotCity {
    private String cityId;
    private String cityZh;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityZh() {
        return cityZh;
    }

    public void setCityZh(String cityZh) {
        this.cityZh = cityZh;
    }

    public HotCity( String cityZh,String cityId) {
        this.cityId = cityId;
        this.cityZh = cityZh;
    }
}
