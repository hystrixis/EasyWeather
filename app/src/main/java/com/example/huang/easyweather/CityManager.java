package com.example.huang.easyweather;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.huang.easyweather.adapter.CityManagerAdapter;
import com.example.huang.easyweather.data.CityAndDegree;
//import com.example.huang.easyweather.utilities.ItemTouchHelperCallback;
import com.example.huang.easyweather.settings.SettingsActivity;
import com.example.huang.easyweather.utilities.CityAndDegreeUtils;
import com.example.huang.easyweather.utilities.ItemTouchHelperCallback;
import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class CityManager extends AppCompatActivity {
    //RecyclerView相关
    private RecyclerView mRecyclerView;
    private CityManagerAdapter mAdapter;
    private List<CityAndDegree> mCityAndDegreeDatas=new ArrayList<>();
    private List<CityAndDegree> cityAndDegreeList;
    public ItemTouchHelperExtension mItemTouchHelper;
    public ItemTouchHelperExtension.Callback mCallback;

    private String mCityId;
    private String mCityZh;
    private String mDegreeMax;
    private String mDegreeMin;
    private TextView mAutoLocation;
    private static final String TAG="CityManager";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_manager);
        mRecyclerView=(RecyclerView)findViewById(R.id.citymanager_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter=new CityManagerAdapter(mCityAndDegreeDatas);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
        mCallback = new ItemTouchHelperCallback();
        mItemTouchHelper = new ItemTouchHelperExtension(mCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mAdapter.setItemTouchHelperExtension(mItemTouchHelper);
        mAutoLocation=(TextView)findViewById(R.id.auto_location);
        //设置标题栏
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_add_city);
        toolbar.setTitle("城市管理");
        setSupportActionBar(toolbar);
        //获取当前标题栏
        ActionBar actionBar=this.getSupportActionBar();
        //如果标题栏不为空
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        /*已废弃，本打算用SharePreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String cityString = prefs.getString("city_manager", null);
        String degreeString=prefs.getString("degree_manager",null);
        */
        autoLocation(mCityId);
        display();

        mAdapter.setOnItemClickListener(new CityManagerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String mCityId=cityAndDegreeList.get(position).getCityId();
                Intent intent=new Intent(CityManager.this,WeatherActivity.class);
                intent.putExtra("city_id",mCityId);
                startActivity(intent);
                finish();
            }
        });
    }

    //TODO 保存当前排序列表
    private void saveList(){

    }
    private void display(){
        mCityId=getIntent().getStringExtra("city_id");
        mCityZh = getIntent().getStringExtra("city_zh");
        mDegreeMax=getIntent().getStringExtra("degree_max");
        mDegreeMin=getIntent().getStringExtra("degree_min");
        CityAndDegreeUtils.handleCityAndDegree(mCityId,mCityZh,mDegreeMax,mDegreeMin);
        Log.d(TAG,"城市和温度"+mCityId+"\t"+mCityZh+"\t"+mDegreeMax+"\t"+mDegreeMin);
        cityAndDegreeList=DataSupport.findAll(CityAndDegree.class);
        if(cityAndDegreeList.size()>0){
            //去除重复的数据
            for ( int i = 0 ; i < cityAndDegreeList.size() - 1 ; i ++ ) {
                for ( int j = cityAndDegreeList.size() - 1 ; j > i; j -- ) {
                    if (cityAndDegreeList.get(j).getCityZh().equals(cityAndDegreeList.get(i).getCityZh())) {
                        cityAndDegreeList.remove(j);
                    }
                }
            }
            mCityAndDegreeDatas.clear();
            for(CityAndDegree cityAndDegree:cityAndDegreeList){
                mCityAndDegreeDatas.add(cityAndDegree);
            }
            mAdapter.notifyDataSetChanged();
        }else {
            Log.d(TAG,"数据库里没有");
        }
    }
    //TODO 自动定位
    private void autoLocation(String mCityId){
        mAutoLocation.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.city_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_add:{
                Intent intent=new Intent(this,AddCity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.action_settings:{
                Intent intent=new Intent(this,SettingsActivity.class);
                startActivity(intent);
            }
        }
        return true;
    }

}
