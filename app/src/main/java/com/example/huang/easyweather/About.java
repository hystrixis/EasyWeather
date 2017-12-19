package com.example.huang.easyweather;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huang.easyweather.utilities.Utility;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class About extends AppCompatActivity {
    @BindView(R.id.source_code)TextView sourceCode;
    @BindView(R.id.csdn_blog)TextView csdnBlog;
    @BindView(R.id.donate)TextView donate;
    @BindView(R.id.share)TextView share;
    @BindView(R.id.qq)TextView qq;
    @BindView(R.id.suggest)TextView suggest;
    private Toolbar toolbar;
    private CollapsingToolbarLayout toolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //沉浸式状态栏的兼容性配置
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        toolbar=(Toolbar)findViewById(R.id.about_toolbar) ;
        toolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.about_toolbar_layout);
        toolbarLayout.setTitle("感谢有你");
        setSupportActionBar(toolbar);
        //获取当前标题栏
        ActionBar actionBar=this.getSupportActionBar();
        //如果标题栏不为空
        if(actionBar!=null){
            //使左上角图标是否显示，如果设成false，则没有程序图标，仅仅就个标题，否则，显示应用程序图标，对应id
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    @OnClick({R.id.source_code,R.id.csdn_blog,R.id.donate,R.id.share,R.id.qq,R.id.suggest})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.source_code:openWebsite("https://github.com/sicelex/EasyWeather");break;
            case R.id.csdn_blog:openWebsite("http://blog.csdn.net/a1262814100");break;
            case R.id.donate:openWebsite("http://118.89.176.68/image/Alipay.jpg");break;
            case R.id.share:openShare();break;
            case R.id.qq:
                Utility.copyToClipboard("1262814100",this);
                Toast.makeText(this, "QQ号已经复制到剪贴板", Toast.LENGTH_SHORT).show();
                break;
            case R.id.suggest:openWebsite("https://www.sojump.hk/jq/15152915.aspx");break;
        }
    }
    private void openWebsite(String url){
        Uri uri = Uri.parse(url);   //指定网址
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);           //指定Action
        intent.setData(uri);                            //设置Uri*/
        startActivity(intent);        //启动Activity
    }
    private void openShare(){
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.text_share));
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.title_share)));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
