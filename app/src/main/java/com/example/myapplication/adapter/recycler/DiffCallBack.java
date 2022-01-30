package com.example.myapplication.adapter.recycler;

import androidx.recyclerview.widget.DiffUtil;

import com.example.myapplication.data.NewsItem;

import java.util.List;

public class DiffCallBack extends DiffUtil.Callback {

    private final List<NewsItem> oldItems;
    private final List<NewsItem> newItems;

    public DiffCallBack(List<NewsItem> oldItems, List<NewsItem> newItems) {
        this.oldItems = oldItems;
        this.newItems = newItems;
    }

    @Override
    public int getOldListSize() {
        return oldItems.size();
    }

    @Override
    public int getNewListSize() {
        return newItems.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItems.get(oldItemPosition) == newItems.get(newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItems.get(oldItemPosition).equals(newItems.get(newItemPosition));
    }
}
