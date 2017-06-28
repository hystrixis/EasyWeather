package com.example.huang.easyweather.anim;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.huang.easyweather.R;

import static android.view.animation.Animation.INFINITE;
import static android.view.animation.Animation.REVERSE;

/**
 * Created by huang on 2017/6/18.
 */

public  class AnimationManager {

    //晴天动画
    public static void initSun(ImageView bgView,ImageView imgView) {
        //设置视图可见
        bgView.setVisibility(View.VISIBLE);
        imgView.setVisibility(View.VISIBLE);
        //光线从水平x轴顺时针旋转30°
        final Animation animation=new RotateAnimation(0,30);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(7000);//持续7秒
        animation.setRepeatCount(INFINITE);//一直重复
        animation.setRepeatMode(REVERSE);//设置动画反向重绘
        // 如被设置为使用硬件加速，则通过硬件渲染，否则等同于第二种类型。
        imgView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
        bgView.setBackgroundResource(R.mipmap.bg_day_sunny);
        imgView.setImageResource(R.mipmap.light_single);
        imgView.setAnimation(animation);
    }
    //多云动画
    public static void initCloud(ImageView bgView,ImageView imgView) {
        //云朵水平来回平移
        bgView.setVisibility(View.VISIBLE);
        imgView.setVisibility(View.VISIBLE);
        Animation animation=new TranslateAnimation(-800,800,0,0);
        animation.setDuration(7000);
        animation.setRepeatCount(INFINITE);
        animation.setRepeatMode(REVERSE);
        bgView.setBackgroundResource(R.mipmap.bg_day_cloud);
        imgView.setImageResource(R.mipmap.cloud_single);
        imgView.setAnimation(animation);
    }
    public static void initOvercast(ImageView bgView,ImageView imgView){
        bgView.setVisibility(View.VISIBLE);
        imgView.setVisibility(View.GONE);
        bgView.setBackgroundResource(R.mipmap.bg_day_overcast);
        imgView.setImageResource(0);
    }

    //下雨动画
    public static void initRain(final RainView rainView){
        rainView.setVisibility(View.VISIBLE);
    }

    public static void initSnow(final SnowView snowView){
        snowView.setVisibility(View.VISIBLE);
    }
    public static void removeAllView(RainView rainView,SnowView snowView){
        rainView.setVisibility(View.GONE);
        snowView.setVisibility(View.GONE);
    }






}
