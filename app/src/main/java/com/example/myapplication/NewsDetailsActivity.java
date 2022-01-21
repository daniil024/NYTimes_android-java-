package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Data.DataUtils;
import com.example.myapplication.Data.NewsItem;
import com.example.myapplication.adapter.recycler.NYTimesRecyclerAdapter;
import com.example.myapplication.adapter.spinner.CategoriesSpinnerAdapter;
import com.example.myapplication.utils.Utils;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class NewsDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_NEWS_ITEM = "extra:newsItem";

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NewsItem item = (NewsItem) getIntent().getSerializableExtra(EXTRA_NEWS_ITEM);
        setTitle(item.getCategory());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_details_screen);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        Glide.with(getApplicationContext()).load(item.getImageUrl()).into((ImageView) findViewById(R.id.full_photo));
        ((TextView) findViewById(R.id.title_details)).setText(item.getTitle());
        ((TextView) findViewById(R.id.time_details)).setText(Utils.formatDateTime(this, item.getPublishDate()));
        ((TextView) findViewById(R.id.text_details)).setText(item.getFullText());
    }


    @Override
    protected void onStop() {
        super.onStop();
        toolbar = null;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static void start(Context context, NewsItem item) {
        context.startActivity(
                new Intent(context, NewsDetailsActivity.class)
                        .putExtra(EXTRA_NEWS_ITEM, item)
        );
    }
}
