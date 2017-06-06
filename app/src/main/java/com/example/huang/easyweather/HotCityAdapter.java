package com.example.huang.easyweather;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huang.easyweather.data.HotCity;

import java.util.List;


/**
 * Created by huang on 2017/6/5.
 */

public class HotCityAdapter extends RecyclerView.Adapter<HotCityAdapter.ViewHolder>{
    private List<HotCity> hotCityDatas;

    //定义点击事件的接口
    public static interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    //声明这个接口的变量
    private HotCityAdapter.OnItemClickListener mOnItemClickListener=null;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.hotcity_list_item,parent,false);
        ViewHolder vh=new ViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener!=null){
                    //注意这里使用getTag方法获取position
                    mOnItemClickListener.onItemClick(v,(int)v.getTag());
                }
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String hotCity=hotCityDatas.get(position).getCityZh();
        holder.listItemHotCityView.setText(hotCity);
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        if(null==hotCityDatas){
            Log.d("AddCity","热门城市列表为空");
            return 0;
        }else {
            return hotCityDatas.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView listItemHotCityView;
        public ViewHolder(View itemView){
            super(itemView);
            listItemHotCityView=(TextView)itemView.findViewById(R.id.hotcity_text);
        }
    }

    public HotCityAdapter(List<HotCity> hotCityList) {
        hotCityDatas = hotCityList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
