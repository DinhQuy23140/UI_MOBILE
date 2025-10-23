package com.example.testui.client;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    private static Retrofit retrofit;
    private static final String URL = "http://192.168.1.10:8000/api/";
    public static Retrofit getInstance() {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS) // timeout kết nối
                    .readTimeout(30, TimeUnit.SECONDS)    // timeout đọc dữ liệu
                    .writeTimeout(30, TimeUnit.SECONDS)   // timeout ghi dữ liệu
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
