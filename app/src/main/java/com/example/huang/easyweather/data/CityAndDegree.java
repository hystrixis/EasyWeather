package com.example.huang.easyweather.data;

import org.litepal.crud.DataSupport;

/**
 * Created by huang on 2017/6/21.
 */

public class CityAndDegree extends DataSupport{
    private String cityId;
    private String cityZh;
    private String degreeMax;
    private String degreeMin;

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

    public String getDegreeMax() {
        return degreeMax;
    }

    public void setDegreeMax(String degreeMax) {
        this.degreeMax = degreeMax;
    }

    public String getDegreeMin() {
        return degreeMin;
    }

    public void setDegreeMin(String degreeMin) {
        this.degreeMin = degreeMin;
    }

    //    public CityAndDegree(String cityZh, String degree) {
//        this.cityZh = cityZh;
//        this.degree = degree;
//    }
}
