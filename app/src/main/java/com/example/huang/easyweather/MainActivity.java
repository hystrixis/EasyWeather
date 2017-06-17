package com.example.huang.easyweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.huang.easyweather.data.City;

import org.litepal.crud.DataSupport;
import org.litepal.util.Const;

import luo.library.base.base.BaseAndroid;

public class MainActivity extends AppCompatActivity {
    private Button mSearchCity;
    private Button deleteCity;
    private Button gotoWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置标题栏
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        mSearchCity=(Button)findViewById(R.id.go_queryCity);
        deleteCity=(Button)findViewById(R.id.delete_database);
        gotoWeather=(Button)findViewById(R.id.goto_weather);
        mSearchCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddCity.class);
                startActivity(intent);
            }
        });
        deleteCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // deleteDatabase("easy_weather");
                DataSupport.deleteAll(City.class);
                Log.d("MainActivity","删除城市数据库成功");
            }
        });
        gotoWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,WeatherActivity.class);
                startActivity(intent);
            }
        });
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getString("weather",null)!=null){
            Intent intent=new Intent(this,WeatherActivity.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent=new Intent(this,AddCity.class);
            startActivity(intent);
            finish();
        };
        BaseAndroid.checkUpdate(MainActivity.this, 2,
                "http://118.89.176.68/downloads/app-release.apk",
                "快更新吧，相信我，没错的", false);
    }
}
