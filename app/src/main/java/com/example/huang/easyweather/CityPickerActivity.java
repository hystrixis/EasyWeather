package com.example.huang.easyweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CityPickerActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PICK_CITY=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_picker);
    }
}
