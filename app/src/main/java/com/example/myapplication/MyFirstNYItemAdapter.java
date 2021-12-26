//package com.example.myapplication;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.DiffUtil;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.RequestManager;
//import com.bumptech.glide.request.RequestOptions;
//import com.example.myapplication.Data.NewsItem;
//
//import java.util.List;
//
//public class MyFirstNYItemAdapter extends RecyclerView.Adapter<MyFirstNYItemAdapter.ViewHolder> {
//
//    private List<NewsItem> newsItems;
//    private final LayoutInflater inflater;
//    private final OnNYTimesNewsListener onNYTimesNewsListener;
//    private final RequestManager imageLoader;
//
//
//    public MyFirstNYItemAdapter(Context context, List<NewsItem> newsItems,
//                                  OnNYTimesNewsListener onNYTimesNewsListener) {
//        this.newsItems = newsItems;
//        inflater = LayoutInflater.from(context);
//        this.onNYTimesNewsListener = onNYTimesNewsListener;
//
//        final RequestOptions imageOption = new RequestOptions()
//                .placeholder(R.drawable.image_placeholder)
//                .fallback(R.drawable.image_placeholder)
//                .centerCrop();
//        imageLoader = Glide.with(context).applyDefaultRequestOptions(imageOption);
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
//    }
//
//    @Override
//    public int getItemCount() {
//        return newsItems.size();
//    }
//
//    @NonNull
//    @Override
//    public MyFirstNYItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new MyFirstNYItemAdapter.ViewHolder(
//                inflater.inflate(R.layout.newsitem, parent, false), onNYTimesNewsListener
//        );
//    }
//
//    @Override
//    public void onBindViewHolder(MyFirstNYItemAdapter.ViewHolder holder, int position) {
//        // get out custom object from our dataset at this position
//        NewsItem item = newsItems.get(position);
//
//        // fill view with our data
//        holder.bind(item);
//    }
//
//    public void replaceData(List<NewsItem> newNewsItems) {
//        // Calculate what really changed
//        DiffUtil.DiffResult diff = DiffUtil.calculateDiff(
//                new DiffCallBack(newsItems, newNewsItems));
//
//        newsItems = newNewsItems;
//        // Dispatch a proper set of notify* calls to RecyclerView
//        diff.dispatchUpdatesTo(this);
//    }
//
//
//    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        public final TextView category;
//        public final TextView title;
//        public final TextView description;
//        public final ImageView photo;
//        public final TextView time;
//
//        private final MyFirstNYItemAdapter.OnNYTimesNewsListener onNYTimesNewsListener;
//
//        public ViewHolder(View itemView, MyFirstNYItemAdapter.OnNYTimesNewsListener onNYTimesNewsListener) {
//            super(itemView);
//            category = itemView.findViewById(R.id.category);
//            title = itemView.findViewById(R.id.title);
//            description = itemView.findViewById(R.id.short_desc);
//            photo = itemView.findViewById(R.id.img);
//            time = itemView.findViewById(R.id.date);
//            this.onNYTimesNewsListener = onNYTimesNewsListener;
//
//            itemView.setOnClickListener(this);
//        }
//
//        public void bind(NewsItem item) {
//            category.setText(item.getCategory().getName());
//            title.setText(item.getTitle());
//            description.setText(item.getPreviewText());
//            imageLoader.load(item.getImageUrl()).into(photo);
//            // Glide.with(itemView.getContext()).load(item.getImageUrl()).into(photo);
//            time.setText(Utils.formatDateTime(itemView.getContext(), item.getPublishDate()));
//        }
//
//        @Override
//        public void onClick(View v) {
//            onNYTimesNewsListener.onNYTimesNewsClick(getBindingAdapterPosition());
//        }
//    }
//
//    public interface OnNYTimesNewsListener {
//        void onNYTimesNewsClick(int position);
//    }
//}
