package com.example.myapplication;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Data.DataUtils;
import com.example.myapplication.Data.NewsItem;
import com.example.myapplication.Utils.Utils;

import java.util.List;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class NewsListActivity extends AppCompatActivity {

    private final static String TAG = NewsListActivity.class.getSimpleName();

    private List<NewsItem> data;
    private ProgressBar progressBar;
    private View error;
    private Button errorAction;
    private RecyclerView recycler;
    private NYTimesRecyclerAdapter adapter;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newslistactivity_main);

        error = findViewById(R.id.error_layout);
        errorAction = findViewById(R.id.error_action_button);
        errorAction.setOnClickListener(view -> loadItems());

        progressBar = findViewById(R.id.progress_bar);
        recycler = findViewById(R.id.recycler_view);
        adapter = new NYTimesRecyclerAdapter(this,
                item -> NewsDetailsExtendedActivity.start(this, item));
        recycler.setAdapter(adapter);

        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            final int columnCount = getResources().getInteger(R.integer.landscape_news_columns_count);
            recycler.setLayoutManager(new GridLayoutManager(this, columnCount));
        } else
            recycler.setLayoutManager(new LinearLayoutManager(this));

        // TODO: do the app with: thread+handler, rxjava, runOnUiThread
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadItems();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Utils.setViewVisibility(recycler, true);
        Utils.setViewVisibility(progressBar, false);
        Utils.setViewVisibility(error, true);

        Utils.disposeSafe(disposable);
        disposable = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter = null;
        recycler = null;
        progressBar = null;
    }


    /*
        Load items to recyclerView with RXJava
    */
    private void loadItems() {
        Utils.setViewVisibility(progressBar, true);
        Utils.setViewVisibility(recycler, false);
        Utils.setViewVisibility(error, false);

        // My guess: I used here another method (observeNews), because when u transmit
        // generateNews in fromArray it runs synchronously,
        // because fromArray doesn't support async running, it just gets array
        disposable = DataUtils.observeNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // References to the methods which will be executed while onNext, onError, onCompleted done
                .subscribe(this::updateItems, this::handleError);
    }

    private void updateItems(@Nullable List<NewsItem> news) {
        if (adapter != null) adapter.replaceData(news);

        Utils.setViewVisibility(recycler, true);
        Utils.setViewVisibility(progressBar, false);
        Utils.setViewVisibility(error, false);
    }

    private void handleError(Throwable th) {
        if (Utils.isDebug())
            Log.e(TAG, th.getMessage(), th);

        Utils.setViewVisibility(error, true);
        Utils.setViewVisibility(recycler, false);
        Utils.setViewVisibility(progressBar, false);
    }
}