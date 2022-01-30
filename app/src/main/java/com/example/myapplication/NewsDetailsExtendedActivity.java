package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.myapplication.data.NewsItem;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class NewsDetailsExtendedActivity extends AppCompatActivity {

    private static final String EXTRA_NEWS_ITEM = "extra:newsItem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NewsItem item = (NewsItem) getIntent().getSerializableExtra(EXTRA_NEWS_ITEM);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_details_screen_collapsed);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(item.getCategory());
        Toolbar toolbar = findViewById(R.id.extended_toolbar);

        Glide.with(getApplicationContext()).load(item.getImageUrl()).into((ImageView) findViewById(R.id.toolbar_img));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        PrettyTime p = new PrettyTime(new Locale("en"));
        ((TextView) findViewById(R.id.title_details)).setText(item.getTitle());
        String time = p.format(item.getPublishDate()) + ", " +
                new SimpleDateFormat("hh:mm a", Locale.US).format(item.getPublishDate());
        ((TextView) findViewById(R.id.time_details)).setText(time);
        ((TextView) findViewById(R.id.text_details)).setText(item.getFullText());
    }


    public static void start(Context context, NewsItem item) {
        context.startActivity(
                new Intent(context, NewsDetailsExtendedActivity.class)
                        .putExtra(EXTRA_NEWS_ITEM, item)
        );
    }
}
