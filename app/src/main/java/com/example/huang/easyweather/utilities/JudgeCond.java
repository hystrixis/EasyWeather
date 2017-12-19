package com.example.huang.easyweather.utilities;

import android.view.View;
import android.widget.ImageView;

import com.example.huang.easyweather.R;
import com.example.huang.easyweather.anim.AnimationManager;
import com.example.huang.easyweather.anim.RainView;
import com.example.huang.easyweather.anim.SnowView;

/**
 * Created by huang on 2017/6/20.
 */

public class JudgeCond {


    public static void judgeCond(String cond, ImageView bgView, ImageView imgView, RainView rainView, SnowView snowView) {

        switch (cond){
            case "晴":AnimationManager.removeAllView(rainView,snowView);AnimationManager.initSun(bgView,imgView);break;
            //云
            case "多云":AnimationManager.removeAllView(rainView,snowView);AnimationManager.initCloud(bgView,imgView);break;
            case "少云":AnimationManager.removeAllView(rainView,snowView);AnimationManager.initCloud(bgView,imgView);break;
            case "晴间多云":AnimationManager.removeAllView(rainView,snowView);AnimationManager.initCloud(bgView,imgView);break;
            //雨
            case "阵雨":AnimationManager.initRain(rainView);break;
            case "小雨":AnimationManager.initRain(rainView);break;
            case "中雨":AnimationManager.initRain(rainView);break;
            case "大雨":AnimationManager.initRain(rainView);break;
            case "暴雨":AnimationManager.initRain(rainView);break;
            case "大暴雨":AnimationManager.initRain(rainView);break;
            case "特大暴雨":AnimationManager.initRain(rainView);break;
            case "冻雨":AnimationManager.initRain(rainView);break;
            //雪
            case "小雪":AnimationManager.initSnow(snowView);break;
            case "中雪":AnimationManager.initSnow(snowView);break;
            case "大雪":AnimationManager.initSnow(snowView);break;

            case "阴":AnimationManager.removeAllView(rainView,snowView);AnimationManager.initOvercast(bgView,imgView);break;
            default:AnimationManager.removeAllView(rainView,snowView);AnimationManager.initSun(bgView,imgView);
        }
    }
}
