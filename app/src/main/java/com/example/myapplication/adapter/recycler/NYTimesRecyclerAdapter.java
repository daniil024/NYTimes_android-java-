package com.example.myapplication.adapter.recycler;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.data.NewsItem;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class NYTimesRecyclerAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    private List<NewsItem> newsItems = new ArrayList<>();
    private final RequestManager imageLoader;
    private final OnNYTimesNewsListener listener;

    // Other realization for clickListener
    public NYTimesRecyclerAdapter(OnNYTimesNewsListener listener,
                                  RequestManager imageLoader) {
        this.listener = listener;
        this.imageLoader = imageLoader;

        final RequestOptions imageOption = new RequestOptions()
                .placeholder(R.drawable.image_placeholder)
                .fallback(R.drawable.image_placeholder)
                .centerCrop();
        this.imageLoader.applyDefaultRequestOptions(imageOption);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return NewsViewHolder.create(parent, imageLoader);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        // get out custom object from our dataset at this position
        NewsItem item = newsItems.get(position);

        // fill view with our data
        holder.bind(item, listener);
    }

    public void replaceData(List<NewsItem> newNewsItems) {
        // Calculate what really changed
        DiffUtil.DiffResult diff = DiffUtil.calculateDiff(
                new DiffCallBack(newsItems, newNewsItems));

        newsItems = newNewsItems;
        // Dispatch a proper set of notify* calls to RecyclerView
        diff.dispatchUpdatesTo(this);
        notifyDataSetChanged();
    }

    public interface OnNYTimesNewsListener {
        void onNYTimesNewsClick(NewsItem item);
    }
}