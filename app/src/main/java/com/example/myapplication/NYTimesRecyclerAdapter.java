package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.Data.NewsItem;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class NYTimesRecyclerAdapter extends RecyclerView.Adapter<NYTimesRecyclerAdapter.ViewHolder> {

    private List<NewsItem> newsItems;
    private final LayoutInflater inflater;
    private final RequestManager imageLoader;
    private final OnNYTimesNewsListener listener;

    // Other realization for clickListener
    public NYTimesRecyclerAdapter(Context context, List<NewsItem> newsItems,
                                  OnNYTimesNewsListener listener) {
        this.newsItems = newsItems;
        inflater = LayoutInflater.from(context);
        this.listener = listener;

        final RequestOptions imageOption = new RequestOptions()
                .placeholder(R.drawable.image_placeholder)
                .fallback(R.drawable.image_placeholder)
                .centerCrop();
        imageLoader = Glide.with(context).applyDefaultRequestOptions(imageOption);
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                inflater.inflate(R.layout.newsitem, parent, false), listener
        );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get out custom object from our dataset at this position
        NewsItem item = newsItems.get(position);

        // fill view with our data
        holder.bind(item);
    }

    public void replaceData(List<NewsItem> newNewsItems) {
        // Calculate what really changed
        DiffUtil.DiffResult diff = DiffUtil.calculateDiff(
                new DiffCallBack(newsItems, newNewsItems));

        newsItems = newNewsItems;
        // Dispatch a proper set of notify* calls to RecyclerView
        diff.dispatchUpdatesTo(this);
    }


     class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView category;
        public final TextView title;
        public final TextView description;
        public final ImageView photo;
        public final TextView time;

        public ViewHolder(View itemView, OnNYTimesNewsListener listener) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onNYTimesNewsClick(newsItems.get(position));
                }
            });

            category = itemView.findViewById(R.id.category);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.short_desc);
            photo = itemView.findViewById(R.id.img);
            time = itemView.findViewById(R.id.date);
        }

        public void bind(NewsItem item) {
            category.setText(item.getCategory().getName());
            title.setText(item.getTitle());
            description.setText(item.getPreviewText());
            imageLoader.load(item.getImageUrl()).into(photo);
            // Glide.with(itemView.getContext()).load(item.getImageUrl()).into(photo);
            time.setText(Utils.formatDateTime(itemView.getContext(), item.getPublishDate()));
        }
    }

    public interface OnNYTimesNewsListener {
        void onNYTimesNewsClick(NewsItem item);
    }
}


// RecyclerView with ListAdapter

//public class NYTimesRecyclerAdapter extends ListAdapter<NewsItem, NYTimesRecyclerAdapter.ViewHolder> {
//
//    private List<NewsItem> newsItems;
//    private final Context context;
//    private final LayoutInflater inflater;
//
//    public NYTimesRecyclerAdapter(Context context, List<NewsItem> newsItems) {
//        super(new DiffCallBack());
//        this.newsItems = newsItems;
//        this.context = context;
//        inflater = LayoutInflater.from(context);
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return newsItems.size();
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new ViewHolder(
//                inflater.inflate(R.layout.newsitem, parent, false)
//        );
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        // get out custom object from our dataset at this position
//        NewsItem item = newsItems.get(position);
//
//        // fill view with out data
//        holder.category.setText(item.getCategory().getName());
//        holder.title.setText(item.getTitle());
//        holder.description.setText(item.getPreviewText());
//        Glide.with(context).load(item.getImageUrl()).into(holder.photo);
//        holder.time.setText(item.getPublishDate().toString());
//    }
//
//    public void replaceData(List<NewsItem> newNewsItems) {
//        submitList(newNewsItems);
//    }
//
//
//    static class ViewHolder extends RecyclerView.ViewHolder {
//
//        public final TextView category;
//        public final TextView title;
//        public final TextView description;
//        public final ImageView photo;
//        public final TextView time;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            category = itemView.findViewById(R.id.category);
//            title = itemView.findViewById(R.id.title);
//            description = itemView.findViewById(R.id.short_desc);
//            photo = itemView.findViewById(R.id.img);
//            time = itemView.findViewById(R.id.date);
//        }
//    }
//}
//
//class DiffCallBack extends DiffUtil.ItemCallback<NewsItem> {
//    @Override
//    public boolean areItemsTheSame(@NonNull NewsItem oldItem, @NonNull NewsItem newItem) {
//        return oldItem.equals(newItem);
//    }
//
//    @Override
//    public boolean areContentsTheSame(@NonNull NewsItem oldItem, @NonNull NewsItem newItem) {
//        return oldItem.equals(newItem);
//    }
//}