package com.example.huang.easyweather.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        holder.listItemHourlyCondView.setText(hourlyForecast.cond.cond);//设置显示天气状况
        holder.listItemHourlyTemperatureView.setText(hourlyForecast.temperature);//设置显示温度
    }

    @Override
    public int getItemCount() {
        if(null==hourlyForecastDatas){
            Log.d("WeatherActivity","每小时预报列表为空");
            return 0;
        }else{
            return hourlyForecastDatas.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView listItemHourlyDateView;
        TextView listItemHourlyCondView;
        TextView listItemHourlyTemperatureView;
        public ViewHolder(View itemView){
            super(itemView);
            listItemHourlyDateView=(TextView)itemView.findViewById(R.id.hourly_date);
            listItemHourlyCondView=(TextView)itemView.findViewById(R.id.hourly_cond);
            listItemHourlyTemperatureView=(TextView)itemView.findViewById(R.id.hourly_temperature);
        }
    }

    public HourlyForecastAdapter(List<HourlyForecast> hourlyForecastList) {
        hourlyForecastDatas = hourlyForecastList;
    }
}
