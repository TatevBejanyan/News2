package com.example.news.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.R;
import com.example.news.adapter.NewsAdapter;
import com.example.news.listeners.SectionClickListener;
import com.example.news.model.News;
import com.example.news.model.Result;
import com.example.news.network.NewsApi;
import com.example.news.network.NewsInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SectionClickListener {
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private List<Result> resultList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private SectionClickListener sectionClickListener;
    private int selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        getData();
    }

    public void initViews() {
        recyclerView = findViewById(R.id.news_recycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        newsAdapter = new NewsAdapter(this, sectionClickListener);
    }

    public void getData() {
        NewsApi newsApi = NewsInstance.getInstance().create(NewsApi.class);
        Call<News> call;
        call = newsApi.getNews(NewsInstance.API_KEY);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                assert response.body() != null;
                resultList.addAll(response.body().getResponse().getResults());
                newsAdapter = new NewsAdapter(MainActivity.this, sectionClickListener);
                recyclerView.setAdapter(newsAdapter);
                newsAdapter.setData(resultList);
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });
    }

    @Override
    public void itemClick(int position) {
        selectedPosition = position;
        Result result = resultList.get(position);
        Toast.makeText(this,result.getPillarName(), Toast.LENGTH_LONG).show();

    }
}
