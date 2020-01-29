package com.example.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.R;
import com.example.news.listeners.SectionClickListener;
import com.example.news.model.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
    private List<Result> listResultNews;
    private Context mContext;
    private SectionClickListener sectionClickListener;
    private int position;

    public NewsAdapter(Context mContext, SectionClickListener sectionClickListener) {
        this.mContext = mContext;
        this.sectionClickListener = sectionClickListener;
        listResultNews = new ArrayList<>();
    }

    public void setData(List<Result> mlistresults) {
        listResultNews.clear();
        listResultNews.addAll(mlistresults);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_news, parent, false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        holder.title.setText(listResultNews.get(position).getSectionName());
        holder.description.setText(listResultNews.get(position).getWebTitle());
        if (listResultNews.get(position).isRedLine()) {
            holder.redLineView.setVisibility(View.VISIBLE);
        }else {
            holder.redLineView.setVisibility(View.INVISIBLE);
        }
        Picasso.get().load(String.valueOf(listResultNews.get(position).getWebUrl())).into(holder.imgNews);
    }

    @Override
    public int getItemCount() {
        return listResultNews.size();
    }

    class NewsHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private ImageView imgNews;
        private View redLineView;

        NewsHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txtTitle);
            description = itemView.findViewById(R.id.txtDescription);
            imgNews = itemView.findViewById(R.id.newsImg);
            redLineView = itemView.findViewById(R.id.redViewLine);
            if (listResultNews.get(position).isRedLine()) {
                redLineView.setVisibility(View.VISIBLE);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position = getAdapterPosition();
                    redLineView.setVisibility(View.VISIBLE);
                    listResultNews.get(getAdapterPosition()).setRedLine(true);
                    Toast.makeText(mContext, listResultNews.get(getAdapterPosition()).getSectionName(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
