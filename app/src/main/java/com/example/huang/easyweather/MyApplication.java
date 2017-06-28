package com.example.huang.easyweather;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePalApplication;
import org.litepal.util.Const;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by huang on 2017/6/18.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        LitePalApplication.initialize(context);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
    public static Context getContext(){
        return  context;
    }
}
