package com.example.huang.easyweather.utilities;

import android.app.ProgressDialog;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.huang.easyweather.AddCity;
import com.example.huang.easyweather.MainActivity;
import com.example.huang.easyweather.data.City;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by huang on 2017/5/27.
 */

public class CityJsonUtils {
    private ProgressDialog progressDialog;




    /**
     * 城市列表
     */



    /*
    解析和处理服务器返回的县级数据
    由于和风天气API的城市代码里返回的是JSONARRAY,像这样：
        {"key":
    "value"}
    //JSONObject(对象)

    [{"key1":
    "value1"},
     {"key2":
    "value2"}]
    //JSONArray(数组)
   */
    //处理获取到的JSON数据并把它们存储在数据库中
    public static boolean handleCityResponse(String response){
            try {
                JSONArray allcities=new JSONArray(response);
                for (int i=0;i<allcities.length();i++){
                    JSONObject cityObject=allcities.getJSONObject(i);
                    //String id=cityObject.getString("id");
                   // String cityZh=cityObject.getString("cityZh");
                  //  Log.d("MainActivity","id is "+id);
                  //  Log.d("MainActivity","city name is "+cityZh);
                    City city=new City();
                    city.setCityId(cityObject.getString("id")); //城市id
                    city.setCityZh(cityObject.getString("cityZh"));//城市中文名
                    city.setLeaderZh(cityObject.getString("leaderZh"));//所属市级中文名
                    city.setProvinceZh(cityObject.getString("provinceZh"));//所属省级中文名
                    city.setCountryZh(cityObject.getString("countryZh"));//所属国家中文名

                    //储存在数据库中
                    city.save();

                   // Log.d("AddCity","获取的JSON数据存储到数据库成功");
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        return false;

    }


//    /**
//     * 显示进度对话框
//     */
//    public  void showProgressDialog() {
//        if (progressDialog == null) {
//            progressDialog = new ProgressDialog();
//            progressDialog.setMessage("正在加载...");
//            progressDialog.setCanceledOnTouchOutside(false);
//        }
//        progressDialog.show();
//    }
//
//    /**
//     * 关闭进度对话框
//     */
//    public void closeProgressDialog() {
//        if (progressDialog != null) {
//            progressDialog.dismiss();
//        }
//    }


}
