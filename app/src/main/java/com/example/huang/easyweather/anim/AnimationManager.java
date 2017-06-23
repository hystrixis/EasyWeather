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
    private Animation animation;
    private ImageView sunImageView;
    private boolean state=false;
//    private ImageView cloudImageView;
//
//    public void sunAnimation(){
//
//        Animation animation= AnimationUtils.loadAnimation(MyApplication.getContext(),R.anim.rotate);
//        sunImageView=(ImageView)
//        mColud= (ImageView) findViewById(R.id.id_img_clod);
//        sunImageView.setAnimation(animation);
//    }
    //public AnimationManager(Animation)

    //晴天动画
    public static void initSun(final ImageView bgView,ImageView imgView) {
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
    public static void initCloud(final ImageView bgView,ImageView imgView) {
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

    //下雨动画
    public static void initRain(final RainView rainView){
        rainView.setVisibility(View.VISIBLE);
    }
//    public static void cancelAllRain(final ImageView bgView,ImageView imgView,RainView rainView){
//        bgView.setVisibility(View.GONE);
//        imgView.setVisibility(View.GONE);
//        rainView.setVisibility(View.GONE);
//    }
    public static void initSnow(final SnowView snowView){
        snowView.setVisibility(View.VISIBLE);
    }
    public static void removeAllView(RainView rainView,SnowView snowView){
        rainView.setVisibility(View.GONE);
        snowView.setVisibility(View.GONE);
    }






}
