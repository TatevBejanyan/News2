package com.example.news.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsInstance {
    private static final String BASE_URL = "https://content.guardianapis.com/";
    public static final String API_KEY = "edbb3095-88f3-498a-a2e7-a977954724db";
    private static Retrofit instance;

    public static Retrofit getInstance(){
        if (instance == null)
            instance = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();

        return instance;
    }
}

