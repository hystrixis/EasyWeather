package com.example.huang.easyweather;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huang.easyweather.data.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huang on 2017/5/28.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private List<City> mCityDatas;
    //定义点击事件的接口
    public static interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    //声明这个接口的变量
    private  OnItemClickListener mOnItemClickListener=null;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
       // Context context=viewGroup.getContext(); //1.获取context
       // int layoutIdForListItem=R.layout.city_list_item;//2.将布局加载进来
       // LayoutInflater inflater= LayoutInflater.from(context);//3.
       // boolean shouldAttachToParentImmediately = false;

        //View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        /*等价于ViewHolder holder=new ViewHolder(view);
          return holder
         */
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.city_list_item,viewGroup,false);
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
        return  vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        City city=mCityDatas.get(position);

        holder.listItemCityView.setText(city.getCityZh()+"—"+city.getLeaderZh()+"—"+city.getProvinceZh());
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
       if(null==mCityDatas) {
           Log.d("AddCity","城市列表为空");
           return 0;
       }
       // Log.d("AddCity","城市列表不为空");
        return mCityDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView listItemCityView;
        //子项的最外层布局，绑定视图
        public ViewHolder(View itemView){
            super(itemView);
            listItemCityView=(TextView)itemView.findViewById(R.id.tv_item_city);
        }

    }
    public CityAdapter(List<City> cityList){
        mCityDatas=cityList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
