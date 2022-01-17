package com.example.myapplication;

import androidx.recyclerview.widget.DiffUtil;

import com.example.myapplication.DTOData.GifDTO;
import com.example.myapplication.Data.NewsItem;

import java.util.List;

public class DiffCallBack extends DiffUtil.Callback {

    private final List<GifDTO> oldItems;
    private final List<GifDTO> newItems;

    public DiffCallBack(List<GifDTO> oldItems, List<GifDTO> newItems) {
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
