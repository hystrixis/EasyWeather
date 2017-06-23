package com.example.huang.easyweather.utilities;

import com.example.huang.easyweather.data.CityAndDegree;

/**
 * Created by huang on 2017/6/22.
 */

public class CityAndDegreeUtils {
    public static boolean handleCityAndDegree(String mCityId,String mCityZh,String mDegree){
        try{
            CityAndDegree cityAndDegree=new CityAndDegree();
            cityAndDegree.setCityId(mCityId);
            cityAndDegree.setCityZh(mCityZh);
            cityAndDegree.setDegree(mDegree);
            cityAndDegree.save();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }



}
