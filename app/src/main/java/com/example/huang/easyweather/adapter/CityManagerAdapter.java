package com.example.huang.easyweather.adapter;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huang.easyweather.CityManager;
import com.example.huang.easyweather.R;
import com.example.huang.easyweather.data.City;
import com.example.huang.easyweather.data.CityAndDegree;
import com.example.huang.easyweather.gson.Now;
import com.loopeer.itemtouchhelperextension.Extension;
import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by huang on 2017/6/20.
 */

public class CityManagerAdapter extends RecyclerView.Adapter<CityManagerAdapter.ViewHolder> {

    private List<CityAndDegree> mCityAndDegreeDatas;
    private Context mContext;
    private ItemTouchHelperExtension mItemTouchHelperExtension;

        //定义点击事件的接口
    public static interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    //声明这个接口的变量
    private CityManagerAdapter.OnItemClickListener mOnItemClickListener=null;

    public void setItemTouchHelperExtension(ItemTouchHelperExtension itemTouchHelperExtension) {
        mItemTouchHelperExtension = itemTouchHelperExtension;
    }
    @Override
    public CityManagerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_citymanager,parent,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener!=null){
                    //注意这里使用getTag方法获取position
                    mOnItemClickListener.onItemClick(v,(int)v.getTag());
                }
            }
        });
        return new ItemSwipeWithActionWidthNoSpringViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CityManagerAdapter.ViewHolder holder, int position) {
        CityAndDegree cityAndDegree=mCityAndDegreeDatas.get(position);
        Log.d("CityManager","城市ID:"+cityAndDegree.getCityId());
        Log.d("CityManager","城市名称:"+cityAndDegree.getCityZh());
        Log.d("CityManager","城市温度:"+cityAndDegree.getDegree());
        holder.listItemCityManagerView.setText(cityAndDegree.getCityZh());
        holder.listItemDegreeManagerView.setText(cityAndDegree.getDegree());
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
        if (holder instanceof ItemSwipeWithActionWidthViewHolder) {
            ItemSwipeWithActionWidthViewHolder viewHolder = (ItemSwipeWithActionWidthViewHolder) holder;
//            viewHolder.mActionViewRefresh.setOnClickListener(
//                    new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Toast.makeText(mContext, "Refresh Click" + holder.getAdapterPosition()
//                                    , Toast.LENGTH_SHORT).show();
//                            mItemTouchHelperExtension.closeOpened();
//                        }
//                    }
//
//            );
            viewHolder.mActionViewDelete.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            doDelete(holder.getAdapterPosition());
                        }
                    }

            );
        }
    }
    private void doDelete(int adapterPosition) {
        String cityId=mCityAndDegreeDatas.get(adapterPosition).getCityId();//获取对象的ID
        int result=DataSupport.deleteAll(CityAndDegree.class,"cityId=?",cityId);
        if(result>0){
            mCityAndDegreeDatas.remove(adapterPosition);
            notifyItemRemoved(adapterPosition);
            Log.d("CityManager","删除城市成功");
        }else{
            Log.d("CityManager","删除城市失败");
        }

    }

    public void move(int from, int to) {
        CityAndDegree prev = mCityAndDegreeDatas.remove(from);
        mCityAndDegreeDatas.add(to > from ? to - 1 : to, prev);
        notifyItemMoved(from, to);
    }

    @Override
    public int getItemCount() {
        if(null==mCityAndDegreeDatas){
            Log.d("CityManager","城市温度不存在失败");
            return 0;
        }
        return mCityAndDegreeDatas.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView listItemCityManagerView;
        TextView listItemDegreeManagerView;
        public View mViewContent;
        public View mActionContainer;
        public ViewHolder(View itemView) {
            super(itemView);
            listItemCityManagerView=(TextView)itemView.findViewById(R.id.city_manager);
            listItemDegreeManagerView=(TextView)itemView.findViewById(R.id.degree_manager);
            mViewContent = itemView.findViewById(R.id.list_manager);
            mActionContainer = itemView.findViewById(R.id.view_list_repo_action_container);
            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                        mItemTouchHelperExtension.startDrag(ViewHolder.this);
                    }
                    return true;
                }
            });
        }
    }
    class ItemSwipeWithActionWidthViewHolder extends ViewHolder implements Extension {

        View mActionViewDelete;
//        View mActionViewRefresh;

        public ItemSwipeWithActionWidthViewHolder(View itemView) {
            super(itemView);
            mActionViewDelete = itemView.findViewById(R.id.view_list_repo_action_delete);
//            mActionViewRefresh = itemView.findViewById(R.id.view_list_repo_action_update);
        }

        @Override
        public float getActionWidth() {
            return mActionContainer.getWidth();
        }
    }

    public class ItemSwipeWithActionWidthNoSpringViewHolder extends ItemSwipeWithActionWidthViewHolder implements Extension {

        public ItemSwipeWithActionWidthNoSpringViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public float getActionWidth() {
            return mActionContainer.getWidth();
        }
    }

    public CityManagerAdapter(List<CityAndDegree> cityAndDegreeList){
        mCityAndDegreeDatas=cityAndDegreeList;
    }
    public CityManagerAdapter(Context context) {
        mCityAndDegreeDatas = new ArrayList<>();
        mContext = context;
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
