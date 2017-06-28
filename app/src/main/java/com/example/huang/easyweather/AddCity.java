package com.example.huang.easyweather;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huang.easyweather.adapter.CityAdapter;
import com.example.huang.easyweather.adapter.HotCityAdapter;
import com.example.huang.easyweather.data.City;
import com.example.huang.easyweather.data.HotCity;
import com.example.huang.easyweather.utilities.NetworkUtils;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.huang.easyweather.utilities.CityJsonUtils.handleCityResponse;

public class AddCity extends AppCompatActivity {


//    HotCity hotCity={  "北京","上海","广州","深圳",
//            "杭州","南京","天津","武汉",
//            "重庆","成都","青岛","长沙",
//            "哈尔滨","香港","澳门","台北"};
    private List<HotCity> hotCityDatas=new ArrayList<>();
    private List<City> mCityDatas=new ArrayList<>();//当前视图列表
    private  ProgressDialog progressDialog;
    private EditText mSearchCity;
    private SearchView mSearchView;
    private RecyclerView mRecyclerView;//城市RecyclerView
    private RecyclerView hotCityRecyclerView;
    private List<City> cityList; //城市列表
    private CityAdapter cityAdapter;//适配器
    private HotCityAdapter hotCityAdapter;
    private boolean firstRequestFromNetWork=true;
    private boolean firstRequestWeather=true;
//    private boolean result=true;

    //和风天气城市代码地址
    private static final String cityAddressJson="http://118.89.176.68/china-city-list.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        //设置标题栏
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_add_city);
        setSupportActionBar(toolbar);
        ActionBar actionBar=this.getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mSearchCity=(EditText)findViewById(R.id.search_city);
        mSearchView=(SearchView)findViewById(R.id.search_view);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setQueryHint("请输入中文城市名称");

        mRecyclerView=(RecyclerView)findViewById(R.id.city_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);//指定RecyclerView的布局方式
        mRecyclerView.setLayoutManager(layoutManager);//设置布局管理器
        cityAdapter=new CityAdapter(mCityDatas); //设置Adapter
        mRecyclerView.setAdapter(cityAdapter);//设置适配器
        mRecyclerView.setHasFixedSize(true);//RecyclerView 的Item宽或者高不会变

        hotCityRecyclerView=(RecyclerView)findViewById(R.id.hotcity_recycler_view);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,4);//每行或每列的item数量
        hotCityRecyclerView.setLayoutManager(gridLayoutManager);
        hotCityAdapter=new HotCityAdapter(hotCityDatas);
        hotCityRecyclerView.setAdapter(hotCityAdapter);

        initlizeHotCity();
        //处理输入完后的搜索事件
        mSearchCity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH){
//                    mSearchCity.setInputType(InputType.TYPE_NULL);//失去编辑框的焦点
                    //queryCities(String.valueOf(mSearchCity.getText().toString()));
                    queryFromServer(cityAddressJson);
                    cityAdapter.setOnItemClickListener(new CityAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            String mCityId=cityList.get(position).getCityId();

                            if(firstRequestWeather){
                                Intent intent=new Intent(AddCity.this,WeatherActivity.class);
                                intent.putExtra("city_id",mCityId);
                                startActivity(intent);
                                firstRequestWeather=false;
                                finish();
                            }else{
                                WeatherActivity activity=new WeatherActivity();
                                activity.requestWeather(mCityId);
                                Intent intent=new Intent(AddCity.this,WeatherActivity.class);
                                intent.putExtra("city_id",mCityId);
                                startActivity(intent);
                            }
                        }
                    });

                }
                return false;
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                queryFromServer(cityAddressJson);
                mSearchView.clearFocus();
                cityAdapter.setOnItemClickListener(new CityAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String mCityId=cityList.get(position).getCityId();
                        if(firstRequestWeather){
                            Intent intent=new Intent(AddCity.this,WeatherActivity.class);
                            intent.putExtra("city_id",mCityId);
                            startActivity(intent);
                            firstRequestWeather=false;
                            finish();
                        }else{
                            WeatherActivity activity=new WeatherActivity();
                            activity.requestWeather(mCityId);
                            Intent intent=new Intent(AddCity.this,WeatherActivity.class);
                            intent.putExtra("city_id",mCityId);
                            startActivity(intent);
                        }
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        hotCityAdapter.setOnItemClickListener(new HotCityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String mCityId=hotCityDatas.get(position).getCityId();
                Log.d("AddCity","获取到的cityId为"+mCityId);
                    Intent intent=new Intent(AddCity.this,WeatherActivity.class);
                    intent.putExtra("city_id",mCityId);
                    startActivity(intent);
                    finish();
            }
        });
//        mSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                queryFromServer(cityAddressJson);
//            }
//        });
    }



    /**
     * 从数据库上查询数据，查不到再去服务器上查
     * 服务器上只查询一次，查询之后便把所有城市加载到了数据库
     * 所以以后的查询都只从数据库查询
     */
    public boolean queryCities(String searchCity){

        //从数据库查询通过用户输入的城市名称会有多个，而每一个对应了唯一的一个ID
        cityList= DataSupport  //查询得到的全部数据放在cityList中
                .where("provinceZh = ? or leaderZh = ? or cityZh = ?",searchCity,searchCity,searchCity)
                .find(City.class);
        boolean result=DataSupport.findAll(City.class).contains(searchCity);
//        if(result){
            //如果数据库有数据，
//        if(!firstRequestFromNetWork){
            if(cityList.size()>0) {
                //去除重复的数据
                for ( int i = 0 ; i < cityList.size() - 1 ; i ++ ) {
                    for ( int j = cityList.size() - 1 ; j > i; j -- ) {
                        if (cityList.get(j).getCityId().equals(cityList.get(i).getCityId())) {
                            cityList.remove(j);
                        }
                    }
                }
                mCityDatas.clear();//显示的列表初始化
                for(City city:cityList){
                    mCityDatas.add(city);
                }
                cityAdapter.notifyDataSetChanged();
                Log.d("AddCity","是从数据库查询到的城市");
                return true;
            }else{
//                queryFromServer(cityAddressJson);
                return false;
            }


    }
    public void initlizeHotCity(){
        hotCityDatas.clear();
        HotCity beijing=new HotCity("北京","CN101010100");
        HotCity shanghai=new HotCity("上海","CN101020100");;
        HotCity guangzhou=new HotCity("广州","CN101280101");
        HotCity shenzhen=new HotCity("深圳","CN101280601");
        HotCity hangzhou=new HotCity("杭州","CN101210101");
        HotCity nanjing=new HotCity("南京","CN101190101");
        HotCity tianjin=new HotCity("天津","CN101030100");
        HotCity wuhan=new HotCity("武汉","CN101200101");
        HotCity chongqing=new HotCity("重庆","CN101040100");
        HotCity chengdu=new HotCity("成都","CN101270101");
        HotCity qingdao=new HotCity("青岛","CN101120201");
        HotCity changsha=new HotCity("长沙","CN101250101");
        HotCity haerbin=new HotCity("哈尔滨","CN101050101");
        HotCity hongkong=new HotCity("香港","CN101320101");
        HotCity macao=new HotCity("澳门","CN101330101");
        HotCity taibei=new HotCity("台北","CN101340101");
        hotCityDatas.add(beijing);
        hotCityDatas.add(shanghai);
        hotCityDatas.add(guangzhou);
        hotCityDatas.add(shenzhen);
        hotCityDatas.add(hangzhou);
        hotCityDatas.add(nanjing);
        hotCityDatas.add(tianjin);
        hotCityDatas.add(wuhan);
        hotCityDatas.add(chongqing);
        hotCityDatas.add(chengdu);
        hotCityDatas.add(qingdao);
        hotCityDatas.add(changsha);
        hotCityDatas.add(haerbin);
        hotCityDatas.add(hongkong);
        hotCityDatas.add(macao);
        hotCityDatas.add(taibei);

    }

    public void queryFromServer(String cityAddressJson) {
        if(!queryCities(String.valueOf(mSearchView.getQuery()))){
            mCityDatas.clear();//显示的列表初始化
            showProgressDialog();//加载对话框
            //建立HTTP连接
            NetworkUtils.sendOkHttpRequest(cityAddressJson, new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("AddCity","HTTP连接失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String returnCityName=response.body().string(); //设置希望返回的字符串
                    boolean result;
                    result=handleCityResponse(returnCityName);//处理返回的JSON数据

                    if(result){ //如果成功处理,就开始查询
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                closeProgressDialog();
                                Log.d("AddCity","从网络上查询到的城市");
                                if(queryCities(String.valueOf(mSearchView.getQuery()))){
//                                    Log.d("AddCity","查询"+mSearchCity.getText()+"成功");
                                }else {
                                    Toast.makeText(AddCity.this, "查不到所需要的城市，请检查输入是否正确", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    } else{
                        closeProgressDialog();
                        Looper.prepare();
                        Toast.makeText(AddCity.this, "查不到所需要的城市，请检查输入是否正确", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }
            });
        }

    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载...有点慢...请稍等");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
