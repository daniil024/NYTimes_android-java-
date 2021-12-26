package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Data.DataUtils;
import com.example.myapplication.Data.NewsItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;


public class NewsListActivity extends AppCompatActivity {

    private List<NewsItem> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newslistactivity_main);

        data = DataUtils.generateNews();
        RecyclerView recycler = findViewById(R.id.recycler_view);
        recycler.setAdapter(new NYTimesRecyclerAdapter(this, data,
                item->NewsDetailsExtendedActivity.start(this, item)));

        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            final int columnsCount = getResources()
                    .getInteger(R.integer.landscape_news_columns_count);
            recycler.setLayoutManager(new GridLayoutManager(this, columnsCount));
        } else
            recycler.setLayoutManager(new LinearLayoutManager(this)); // ,LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void onResume() {
        // After a pause OR at startup
        super.onResume();

        //Refresh list stuff here
        RecyclerView recycler = findViewById(R.id.recycler_view);
        NYTimesRecyclerAdapter adapter = (NYTimesRecyclerAdapter) recycler.getAdapter();
        if (adapter != null)
            adapter.replaceData(DataUtils.generateNews());
    }


}