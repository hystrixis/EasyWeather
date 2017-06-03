package com.example.huang.easyweather;

import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.LocaleDisplayNames;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huang.easyweather.data.City;
import com.example.huang.easyweather.utilities.CityJsonUtils;
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


    private List<City> mCityDatas=new ArrayList<>();//当前视图列表
    private  ProgressDialog progressDialog;
    private EditText mSearchCity;
    private Button mSearch;
    private RecyclerView mRecyclerView;//城市RecyclerView
    private List<City> cityList; //城市列表
    private CityAdapter adapter;//适配器
    private boolean firstRequestFromNetWork=true;

    //和风天气城市代码地址
    private static final String cityAddressJson="https://cdn.heweather.com/china-city-list.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        mSearchCity=(EditText)findViewById(R.id.search_city);
        //测试用按钮
        mSearch=(Button)findViewById(R.id.search);



        mRecyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);//指定RecyclerView的布局方式
        mRecyclerView.setLayoutManager(layoutManager);//设置布局管理器
        adapter=new CityAdapter(mCityDatas); //设置Adapter
        mRecyclerView.setAdapter(adapter);//设置适配器
        mRecyclerView.setHasFixedSize(true);//RecyclerView 的Item宽或者高不会变

        //处理输入完后的搜索事件
        mSearchCity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH){
                   queryCities(String.valueOf(mSearchCity.getText().toString()));
                    Toast.makeText(AddCity.this,"搜搜这个按钮还是还好",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // queryCities("北京");
                queryFromServer(cityAddressJson);

            }
        });
    }

    /**
     * 根据传入的地址和类型从服务器上查询省市县数据。
     */
    public void queryCities(String mSearchCity){

        //从数据库查询通过用户输入的城市名称会有多个，而每一个对应了唯一的一个ID
        cityList= DataSupport  //查询得到的全部数据放在cityList中
                .where("cityZh = ?",mSearchCity)
                .find(City.class);
                //如果数据库有数据，
        //firstRequestFromNetWork=false;
        if(!firstRequestFromNetWork){
            if(cityList.size()>0) {
                mCityDatas.clear();//显示的列表初始化
                for(City city:cityList){
                    mCityDatas.add(city);
                    if(null==mCityDatas){
                        Toast.makeText(this, "查不到所需要的城市", Toast.LENGTH_SHORT).show();
                        Log.d("AddCity","查不到所需要的城市");
                    }
                }
                adapter.notifyDataSetChanged();
                Log.d("AddCity","是从数据库查询到的城市");
            }
        }else{
            firstRequestFromNetWork=false;
            queryFromServer(cityAddressJson);
        }


    }
    public void queryFromServer(String cityAddressJson) {
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
                boolean result=false;
                result=handleCityResponse(returnCityName);//处理返回的JSON数据

                if(result){ //如果成功处理,就开始查询
                    runOnUiThread(new Runnable() {

                       @Override
                       public void run() {
                           closeProgressDialog();
                           Log.d("AddCity","从网络上查询到的城市");
                           queryCities(String.valueOf(mSearchCity.getText().toString()));
                           Log.d("AddCity","查询"+mSearchCity.getText()+"成功");
                       }
                   });
                }
            }
        });
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载...");
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





}
