package com.example.huang.easyweather.data;

import org.litepal.crud.DataSupport;

/**
 * Created by huang on 2017/4/22.
 */

public class City extends DataSupport {
    //获取和风天气服务器上的城市代码
    //https://cdn.heweather.com/china-city-list.json
    private String cityId; //城市id,例如CN101191104
    private String cityZh;//中文城市名称
    private String leaderZh;//所属市级的中文名称
    private String provinceZh;//所属省级或直辖市的中文名称
    private String countryZh;//所属国家的中文名称
    private double lon;//当前城市的经度
    private double lat;//当前城市的纬度

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

    public String getLeaderZh() {
        return leaderZh;
    }

    public void setLeaderZh(String leaderZh) {
        this.leaderZh = leaderZh;
    }

    public String getProvinceZh() {
        return provinceZh;
    }

    public void setProvinceZh(String provinceZh) {
        this.provinceZh = provinceZh;
    }

    public String getCountryZh() {
        return countryZh;
    }

    public void setCountryZh(String countryZh) {
        this.countryZh = countryZh;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public City() {
    }

    public City(String cityId, String cityZh) {
        this.cityId = cityId;
        this.cityZh = cityZh;
    }


}
