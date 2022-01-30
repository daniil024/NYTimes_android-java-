package com.example.myapplication.adapter.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.data.NewsItem;
import com.example.myapplication.R;
import com.example.myapplication.utils.Utils;

public class NewsViewHolder extends RecyclerView.ViewHolder {

    private static final int LAYOUT = R.layout.newsitem;

    private final RequestManager imageLoader;
    public final TextView category;
    public final TextView title;
    public final TextView description;
    public final ImageView photo;
    public final TextView time;

    public static NewsViewHolder create(@NonNull ViewGroup parent,
                                        RequestManager imageLoader) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(LAYOUT, parent, false);
        return new NewsViewHolder(view, imageLoader);
    }

    private NewsViewHolder(@NonNull View itemView, RequestManager imageLoader) {
        super(itemView);
        this.imageLoader = imageLoader;
        category = itemView.findViewById(R.id.category);
        title = itemView.findViewById(R.id.title);
        description = itemView.findViewById(R.id.short_desc);
        photo = itemView.findViewById(R.id.img);
        time = itemView.findViewById(R.id.date);
    }

    public void bind(NewsItem item, NYTimesRecyclerAdapter.OnNYTimesNewsListener listener) {
        itemView.setOnClickListener(v -> {
            if (listener != null &&
                    getBindingAdapterPosition() != RecyclerView.NO_POSITION)
                listener.onNYTimesNewsClick(item);
        });

        category.setText(item.getCategory());
        title.setText(item.getTitle());
        description.setText(item.getPreviewText());
        imageLoader.load(item.getImageUrl())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                .thumbnail(0.3f)
                .into(photo);
        if (item.getPublishDate() != null)
            time.setText(Utils.formatDateTime(itemView.getContext(), item.getPublishDate()));
    }
}
