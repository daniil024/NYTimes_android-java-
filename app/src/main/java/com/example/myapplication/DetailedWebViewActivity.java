package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.myapplication.data.NewsItem;

@SuppressLint("SetJavaScriptEnabled")
public class DetailedWebViewActivity extends AppCompatActivity {

    private WebView webView;
    private static final String EXTRA_NEWS_ITEM = "extra:newsItem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_web_view);
        NewsItem item = (NewsItem) getIntent().getSerializableExtra(EXTRA_NEWS_ITEM);

        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(item.getFullText());
    }

    public static void start(Context context, NewsItem item) {
        context.startActivity(
                new Intent(context, DetailedWebViewActivity.class)
                        .putExtra(EXTRA_NEWS_ITEM, item)
        );
    }
}