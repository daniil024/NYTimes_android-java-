package com.example.myapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.myapplication.DTOData.GifDTO;
import com.example.myapplication.Data.NewsItem;
import com.example.myapplication.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class GifsRecyclerAdapter extends RecyclerView.Adapter<GifsRecyclerAdapter.ViewHolder> {

    private final RequestManager requestManager;
    private List<GifDTO> gifsItems = new ArrayList<>();
    private final LayoutInflater inflater;

    // Other realization for clickListener
    public GifsRecyclerAdapter(Context context, RequestManager requestManager) {
        this.requestManager = requestManager;
        inflater = LayoutInflater.from(context);

        final RequestOptions imageOption = new RequestOptions()
                .centerCrop();
        requestManager.applyDefaultRequestOptions(imageOption);

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return gifsItems.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                inflater.inflate(R.layout.gifitem, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get out custom object from our dataset at this position
        GifDTO item = gifsItems.get(position);

        // fill view with our data
        holder.bind(item);
    }

    public void replaceData(List<GifDTO> newGifs) {
        // Calculate what really changed
        DiffUtil.DiffResult diff = DiffUtil.calculateDiff(
                new DiffCallBack(gifsItems, newGifs));

        gifsItems = newGifs;
        // Dispatch a proper set of notify* calls to RecyclerView
        diff.dispatchUpdatesTo(this);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public final ImageView photo;

        public ViewHolder(View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.img);
        }

        public void bind(GifDTO item) {

            requestManager.load(item.getUrl())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e,
                                                    Object model, Target<Drawable> target,
                                                    boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource,
                                                       Object model, Target<Drawable> target,
                                                       DataSource dataSource,
                                                       boolean isFirstResource) {
                            ProgressBar gpb = itemView.findViewById(R.id.gif_progress_bar);
                            gpb.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(photo);
        }
    }
}