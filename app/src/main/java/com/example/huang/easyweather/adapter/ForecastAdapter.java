package com.example.huang.easyweather.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huang.easyweather.R;
import com.example.huang.easyweather.gson.Forecast;

import java.util.List;

/**
 * Created by huang on 2017/6/4.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {
    private List<Forecast> forecastDatas;
    private static final String TAG="WeatherActivity";
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Forecast forecast=forecastDatas.get(position);
        holder.listItemForecastDataView.setText(forecast.date);
        holder.listItemForecastInfoView.setText(forecast.more.info);
        holder.listItemForecastMaxView.setText(forecast.temperature.max);
        holder.listItemForecastMinView.setText(forecast.temperature.min);
    }

    @Override
    public int getItemCount() {
        if(null==forecastDatas){
            Log.d("TAG","未来天气预报获取失败");
            return 0;
        }else {
            Log.d("TAG","成功获取天气");
            return forecastDatas.size();
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView listItemForecastDataView;
        TextView listItemForecastInfoView;
        TextView listItemForecastMaxView;
        TextView listItemForecastMinView;

        public ViewHolder(View itemView) {
            super(itemView);
            listItemForecastDataView=(TextView)itemView.findViewById(R.id.date_text);
            listItemForecastInfoView=(TextView)itemView.findViewById(R.id.info_text);
            listItemForecastMaxView=(TextView)itemView.findViewById(R.id.max_text);
            listItemForecastMinView=(TextView)itemView.findViewById(R.id.min_text);
        }
    }

    public ForecastAdapter(List<Forecast> forecastList) {
        forecastDatas = forecastList;
    }
}
