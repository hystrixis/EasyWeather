package com.example.huang.easyweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.huang.easyweather.data.City;

import org.litepal.crud.DataSupport;
import org.litepal.util.Const;

public class MainActivity extends AppCompatActivity {
    private Button mSearchCity;
    private Button deleteCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSearchCity=(Button)findViewById(R.id.go_queryCity);
        deleteCity=(Button)findViewById(R.id.delete_database);
        mSearchCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddCity.class);
                startActivity(intent);
            }
        });
        deleteCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // deleteDatabase("easy_weather");
                DataSupport.deleteAll(City.class);
                Log.d("MainActivity","删除城市数据库成功");
            }
        });
    }
}
