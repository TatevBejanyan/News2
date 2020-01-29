package com.example.news.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

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
    private Menu menu;
    private SectionClickListener sectionClickListener;
    private int selectedPosition;
    private boolean isGrid = true;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        getData();
    }

    public void initViews() {
        recyclerView = findViewById(R.id.news_recycler);
        progressBar = findViewById(R.id.progress_bar);
        if (isGrid) {
            layoutManager = new LinearLayoutManager(this);
        } else {
            layoutManager = new StaggeredGridLayoutManager(2, 1);
        }
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
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
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
        Toast.makeText(this, result.getPillarName(), Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.progress_horizontal) {
            isGrid = !isGrid;
        }
        if (isGrid) {
            item.setIcon(R.drawable.ic_view_stream_black_24dp);
        } else {
            item.setIcon(R.drawable.ic_view_week_black_24dp);

        }

        initViews();
        return true;
    }
}
