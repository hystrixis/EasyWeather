package com.example.huang.easyweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huang.easyweather.gson.Forecast;
import com.example.huang.easyweather.gson.HourlyForecast;
import com.example.huang.easyweather.gson.Weather;
import com.example.huang.easyweather.utilities.NetworkUtils;
import com.example.huang.easyweather.utilities.WeatherJsonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    public SwipeRefreshLayout swipeRefresh;
    private String mCityId;
    private String mCityZh;//城市的中文名称，临时热门城市解决方案

    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView titleDegree;

    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;

    private ImageButton addCity;
    private List<HourlyForecast> hourlyForecastDatas=new ArrayList<>();//当前视图列表
    private RecyclerView hourlyRecyclerView;
    private HourlyForecastAdapter hourlyForecastAdapter;//创建适配器实例
    private List<Forecast> forecastDatas=new ArrayList<>();

    private RecyclerView forecastRecyclerView;
    private ForecastAdapter forecastAdapter;

    private boolean beforeWeatherInfo=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        //添加城市菜单
        addCity=(ImageButton) findViewById(R.id.add_city);
        //下拉刷新
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        /*
        weather_title
         */
        titleCity=(TextView)findViewById(R.id.city) ;
        titleUpdateTime=(TextView)findViewById(R.id.update_time);
        titleDegree=(TextView)findViewById(R.id.degree);
        /*
        suggestion
         */
        comfortText=(TextView)findViewById(R.id.comfort_text);
        carWashText=(TextView)findViewById(R.id.car_wash_text);
        sportText=(TextView)findViewById(R.id.sport_text);


        /*
        HourlyForecastAdapter
         */
        hourlyRecyclerView=(RecyclerView)findViewById(R.id.hourly_recyclerView);
        LinearLayoutManager HourlyLayoutManager=new LinearLayoutManager(this);//创建布局管理器的实例
        HourlyLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//设置布局方式为水平
        hourlyRecyclerView.setLayoutManager(HourlyLayoutManager);//设置布局
        hourlyForecastAdapter=new HourlyForecastAdapter(hourlyForecastDatas);//创建适配器实例
        hourlyRecyclerView.setAdapter(hourlyForecastAdapter);//设置适配器
        hourlyRecyclerView.setHasFixedSize(true);//保持item的宽或高不会变

        /*
        ForecastAdapter
         */
        forecastRecyclerView=(RecyclerView)findViewById(R.id.forecast_recyclerView);
        LinearLayoutManager forecastLayoutManager=new LinearLayoutManager(this);
        forecastRecyclerView.setLayoutManager(forecastLayoutManager);
        forecastAdapter=new ForecastAdapter(forecastDatas);
        forecastRecyclerView.setAdapter(forecastAdapter);
        forecastRecyclerView.setHasFixedSize(true);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather", null);

        if (weatherString != null && beforeWeatherInfo) {
            // 有缓存时直接解析天气数据
            beforeWeatherInfo=false;
            Weather weather = WeatherJsonUtils.handleWeatherResponse(weatherString);
            mCityId = weather.basic.cityId;
            showWeatherInfo(weather);
        } else {
            // 无缓存时去服务器查询天气
            mCityId = getIntent().getStringExtra("city_id");
//            mCityZh=getIntent().getStringExtra("city_zh");
            //weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(mCityId);
        }
        mCityId = getIntent().getStringExtra("city_id");
//            mCityZh=getIntent().getStringExtra("city_zh");
        //weatherLayout.setVisibility(View.INVISIBLE);
        requestWeather(mCityId);

        //添加城市按钮
        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WeatherActivity.this,AddCity.class);
                startActivity(intent);
            }
        });

        //下拉刷新
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //mCityId = getIntent().getStringExtra("city_id");
                requestWeather(mCityId);
            }
        });

    }
    public void requestWeather(final String cityId){
        String weatherUrl="https://free-api.heweather.com/v5/weather?city="+cityId+"&key=a944761a388842c1af1c47f8a16b95c7";
        NetworkUtils.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String returnWeatherInfo= response.body().string();
                final Weather weather=WeatherJsonUtils.handleWeatherResponse(returnWeatherInfo);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(weather!=null && "ok".equals(weather.status)){
                            SharedPreferences.Editor editor= PreferenceManager
                                    .getDefaultSharedPreferences(WeatherActivity.this)
                                    .edit();
                            editor.putString("weather",returnWeatherInfo);
                            editor.apply();
                            mCityId=weather.basic.cityId;
                            showWeatherInfo(weather);
                        }
//                        else {
//                            Toast.makeText(WeatherActivity.this, "获取天气失败", Toast.LENGTH_SHORT).show();
//                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
    }

    public void showWeatherInfo(Weather weather){
        String cityZh=weather.basic.cityZh;//城市中文名称
        String updateTime=weather.basic.update.updateTime.split(" ")[1];//更新时间
        String degree=weather.now.temperature+"℃";//当前天气温度
        titleCity.setText(cityZh);
        titleUpdateTime.setText(updateTime);
        titleDegree.setText(degree);
        //加载每小时预报数据
        hourlyForecastDatas.clear();
        for(HourlyForecast hourlyForecast:weather.hourlyForecastList){
            hourlyForecastDatas.add(hourlyForecast);
        }
        hourlyForecastAdapter.notifyDataSetChanged();
        //加载未来天气预报数据
        forecastDatas.clear();
        for(Forecast forecast:weather.forecastList){
            forecastDatas.add(forecast);
        }
        forecastAdapter.notifyDataSetChanged();
        //加载生活建议数据
        String comfort = "舒适度：" + weather.suggestion.comfort.info;
        String carWash = "洗车指数：" + weather.suggestion.carWash.info;
        String sport = "运行建议：" + weather.suggestion.sport.info;
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);


    }
}
