package com.example.huang.easyweather.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huang.easyweather.R;
import com.example.huang.easyweather.gson.HourlyForecast;

import java.util.List;

/**
 * Created by huang on 2017/6/4.
 */

public class HourlyForecastAdapter extends RecyclerView.Adapter<HourlyForecastAdapter.ViewHolder> {
    private List<HourlyForecast> hourlyForecastDatas;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HourlyForecast hourlyForecast=hourlyForecastDatas.get(position);
        String date=hourlyForecast.date.split(" ")[1];
        holder.listItemHourlyDateView.setText(date);//设置显示的时间
        holder.listItemHourlyCondView.setImageResource(initImage(hourlyForecast.cond.cond));//设置显示天气状况
        holder.listItemHourlyTemperatureView.setText(hourlyForecast.temperature);//设置显示温度
    }

    @Override
    public int getItemCount() {
        if(hourlyForecastDatas==null){
            Log.d("WeatherActivity","每小时预报列表为空");
            return 0;
        }else{
            return hourlyForecastDatas.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView listItemHourlyDateView;
        ImageView listItemHourlyCondView;
        TextView listItemHourlyTemperatureView;
        public ViewHolder(View itemView){
            super(itemView);
            listItemHourlyDateView=(TextView)itemView.findViewById(R.id.hourly_date);
            listItemHourlyCondView=(ImageView) itemView.findViewById(R.id.hourly_cond);
            listItemHourlyTemperatureView=(TextView)itemView.findViewById(R.id.hourly_temperature);
        }
    }

    public HourlyForecastAdapter(List<HourlyForecast> hourlyForecastList) {
        hourlyForecastDatas = hourlyForecastList;
    }
    public static int initImage(String cond){
        switch (cond){
            case "晴":return R.drawable.sunny;
            case "多云":
            case "阴":
            case "少云":return R.drawable.cloudy;
            case "晴间多云":return R.drawable.sun_cloudy;
            case "有风":
            case "平静":
            case "微风":
            case "和风":
            case "清风":return R.drawable.windy;
            case "强风/劲风":
            case "疾风":
            case "大风":
            case "烈风":
            case "风暴":
            case "狂暴风":
            case "热带风暴":return R.drawable.gale;
            case "飓风":return R.drawable.hurricane;
            case "龙卷风":return R.drawable.tornado;
            case "阵雨":
            case "强阵雨":return R.drawable.shower_rain;
            case "雷阵雨":
            case "强雷阵雨":return R.drawable.thunder_shower;
            case "雷阵雨伴有冰雹":return R.drawable.hail;

            case "毛毛雨/细雨":
            case "小雨":return R.drawable.light_rain;
            case "冻雨":
            case "中雨":return R.drawable.moderate_rain;
            case "大雨":
            case "极端降雨":return R.drawable.heavy_rain;

            case "暴雨":
            case "大暴雨":
            case "特大暴雨":return R.drawable.severe_storm;

            case "小雪":return R.drawable.light_snow;

            case "阵雪":
            case "中雪":return R.drawable.moderate_snow;
            case "大雪":return R.drawable.heavy_snow;
            case "暴雪":return R.drawable.storm_snow;
            case "雨雪天气":
            case "阵雨夹雪":
            case "雨夹雪":return R.drawable.sleet;

            case "薄雾":
            case "雾":return R.drawable.foggy;
            case "霾":return R.drawable.haze;
            case "扬沙":return R.drawable.sand;
            case "浮尘":;
            case "沙尘暴":
            case "强沙尘暴":return R.drawable.dust;
            case "热":
            case "冷":
            case "未知":return R.drawable.unknown;
            default:return R.drawable.unknown;
        }
    }

    private int sb(String sa){
        switch (sa){
            case "d":return 1;
            default:return 0;
        }
    }
}
