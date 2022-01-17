package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.myapplication.Data.NewsItem;

public class NewsDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle data = getIntent().getExtras();
        NewsItem item = data.getParcelable("NewsItem");
        setTitle(item.getCategory().getName());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_details_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Uncomment the line below instead of using back button icon in xml
        // Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        Glide.with(getApplicationContext()).load(item.getImageUrl()).into((ImageView) findViewById(R.id.full_photo));
        ((TextView) findViewById(R.id.title_details)).setText(item.getTitle());
        ((TextView) findViewById(R.id.time_details)).setText(item.getPublishDate().toString());
        ((TextView) findViewById(R.id.text_details)).setText(item.getFullText());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
