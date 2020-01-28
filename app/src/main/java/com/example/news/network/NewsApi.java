package com.example.news.network;

import com.example.news.model.News;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("search")
    Call<News> getNews(@Query("api-key") String APP_ID);

}
