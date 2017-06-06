package com.example.huang.easyweather.utilities;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by huang on 2017/5/27.
 */

public class NetworkUtils {
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        //创建okHttpClient对象
        OkHttpClient client=new OkHttpClient();
        //创建一个Request
        Request request=new Request.Builder()
                .url(address)
                .build();
        //new call
        Call call=client.newCall(request);
        call.enqueue(callback);
    }
}
