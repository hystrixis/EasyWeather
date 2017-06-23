package com.example.huang.easyweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import com.example.huang.easyweather.data.City;
import com.example.huang.easyweather.data.CityAndDegree;
import com.example.huang.easyweather.gson.Now;
//import com.example.huang.easyweather.utilities.ItemTouchHelperCallback;
import com.example.huang.easyweather.gson.Weather;
import com.example.huang.easyweather.utilities.CityAndDegreeUtils;
import com.example.huang.easyweather.utilities.WeatherJsonUtils;
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
    private String mDegree;
    private TextView mAutoLocation;
    private static final String TAG="CityManager";
//    private CityAndDegree mCityAndDegreeDatas=new ArrayList<>()
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


        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this));

//        mCallback = new ItemTouchHelperCallback();
//        mItemTouchHelper = new ItemTouchHelperExtension(mCallback);
//        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        //mAdapter.setItemTouchHelperExtension(mItemTouchHelper);
//        finish();
        /*已废弃，本打算用SharePreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String cityString = prefs.getString("city_manager", null);
        String degreeString=prefs.getString("degree_manager",null);
        */
        //CityAndDegree cityAndDegree=new CityAndDegree();
//        if (cityString != null ||degreeString!=null) {
            // 有缓存时
//          Weather weather = WeatherJsonUtils.handleWeatherResponse(weatherString);
            //mCityZh =getIntent().getStringExtra(cityString);
            //mDegree=getIntent().getStringExtra(degreeString);
           // cityAndDegree.setCityZh(cityString);
           // cityAndDegree.setDegree(degreeString);
//            Log.d(TAG,"城市和温度"+cityString+"\t"+degreeString);
            //mCityAndDegreeDatas.clear();
           // mCityAndDegreeDatas.add(new CityAndDegree(cityString,degreeString));
            //mCityAndDegreeDatas.add(new CityAndDegree("nicai","ad"));

//            mCityAndDegreeDatas.add(mCallback);
//        } else {
            // 无缓存时
            autoLocation(mCityId);
            display();
//            Log.d(TAG,"城市和温度"+mCityZh+"\t"+mDegree);
//
//
//            cityAndDegree.setCityZh(mCityZh);
//            cityAndDegree.setDegree(mDegree);
//            mCityAndDegreeDatas.clear();
//            mCityAndDegreeDatas.add(cityAndDegree);
//        }

//        mCityId = getIntent().getStringExtra("city_id");
//        requestWeather(mCityId);
        mAdapter.setOnItemClickListener(new CityManagerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //String mCityZh=cityAndDegreeList.get(position).getCityZh();
                String mCityId=cityAndDegreeList.get(position).getCityId();
                Intent intent=new Intent(CityManager.this,WeatherActivity.class);
                intent.putExtra("city_id",mCityId);
                startActivity(intent);
                finish();
            }
        });

    }
//    private CityAndDegree AddCityAndDegree(String mCityZh,String mDegree){
//
//        return AddCityAndDegree(mCityZh,mDegree);
//    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        display();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        display();
//    }
    //保存当前排序列表
    private void saveList(){

    }
    private void display(){
        mCityId=getIntent().getStringExtra("city_id");
        mCityZh = getIntent().getStringExtra("city_zh");
        mDegree=getIntent().getStringExtra("degree");
        CityAndDegreeUtils.handleCityAndDegree(mCityId,mCityZh,mDegree);
        Log.d(TAG,"城市和温度"+mCityId+"\t"+mCityZh+"\t"+mDegree);
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
    private void autoLocation(String mCityId){
        mAutoLocation.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.city_add,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            Intent intent=new Intent(this,AddCity.class);
            startActivity(intent);
            finish();
            return true;
    }
}
