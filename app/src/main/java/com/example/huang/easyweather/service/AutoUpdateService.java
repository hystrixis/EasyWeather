package com.example.huang.easyweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.IntDef;
import android.widget.Toast;

import com.example.huang.easyweather.WeatherActivity;
import com.example.huang.easyweather.gson.Weather;
import com.example.huang.easyweather.settings.SettingsActivity;
import com.example.huang.easyweather.settings.SettingsFragment;
import com.example.huang.easyweather.utilities.NetworkUtils;
import com.example.huang.easyweather.utilities.WeatherJsonUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AutoUpdateService extends Service {
    public static final String KEY_PREF_UPDATE_FREQUENCY="update_frequency";

    public AutoUpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
        boolean networkState=prefs.getBoolean(SettingsFragment.KEY_PREF_WIFI_UPDATE,false);
        if(networkState){
            if(isWifiConnected())
            updateWeather();
        }
        //将获取到的值转换为int类型，这是分钟，然后在转换成毫秒
        int updateFrequency=Integer.parseInt(prefs.getString(SettingsFragment.KEY_PREF_UPDATE_FREQUENCY,"0")) *60*1000;
        //获取一个闹钟实例，用于到点自动更新天气
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //SystemClock.elapsedRealtime():表示计算某个时间经历了多长时间，例如程序运行了多长时间
        long triggerAtTime = SystemClock.elapsedRealtime() + updateFrequency;
        Intent i = new Intent(this, AutoUpdateService.class);
        //PendingIntent:intent的封装包，不是立刻执行的
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        //取消该闹钟
        manager.cancel(pi);
        /*
        该方法用于设置一次性闹钟，第一个参数表示闹钟类型，第二个参数表示闹钟执行时间，第三个参数表示闹钟响应动作。
        1.通过真实的时间流逝才能触发，休眠状态时间也会被计算，修改系统时间不会触发
        2.闹钟执行时间
        3.闹钟响应的动作
         */
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }
    //判断是否有网络连接
    private boolean isNetConnected(){
        ConnectivityManager connMgr=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connMgr.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }
    //判断是否是wifi连接
    private boolean isWifiConnected(){
        ConnectivityManager connMgr=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connMgr.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected() && networkInfo.getType()==connMgr.TYPE_WIFI){
            return true;
        }else {
            return false;
        }
    }
    /**
     * 更新天气信息。
     */
    private void updateWeather(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather", null);
        if (weatherString != null) {
            // 有缓存时直接解析天气数据
            Weather weather = WeatherJsonUtils.handleWeatherResponse(weatherString);
            String cityId = weather.basic.cityId;
//            String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=a944761a388842c1af1c47f8a16b95c7";
            String weatherUrl="https://free-api.heweather.com/v5/weather?city="+cityId+"&key=a944761a388842c1af1c47f8a16b95c7";
            NetworkUtils.sendOkHttpRequest(weatherUrl, new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String returnWeatherInfo = response.body().string();
                    Weather weather = WeatherJsonUtils.handleWeatherResponse(returnWeatherInfo);
                    if (weather != null && "ok".equals(weather.status)) {
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                        editor.putString("weather", returnWeatherInfo);
                        editor.apply();
                    }
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "天气自动更新异常", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
