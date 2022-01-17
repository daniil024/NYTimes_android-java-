package com.example.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.DTOData.GifDTO;
import com.example.myapplication.DTOData.GifResponse;
import com.example.myapplication.Endpoints.DefaultResponse;
import com.example.myapplication.Utils.RestApi;
import com.example.myapplication.Utils.Utils;
import com.google.gson.Gson;

import java.util.List;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsListActivity extends AppCompatActivity {

    private final static String TAG = NewsListActivity.class.getSimpleName();

    private List<GifDTO> data;
    private ProgressBar progressBar;
    private View error;
    private RecyclerView recycler;
    private GifsRecyclerAdapter adapter;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newslistactivity_main);

        error = findViewById(R.id.error_layout);
        Button errorAction = findViewById(R.id.error_action_button);
        errorAction.setOnClickListener(view -> loadItems());

        progressBar = findViewById(R.id.progress_bar);
        recycler = findViewById(R.id.recycler_view);
        adapter = new GifsRecyclerAdapter(this, Glide.with(this));
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

        compositeDisposable.clear();
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

        RestApi restApi = RestApi.getInstance();
        restApi.gifs().search("Bojack Horseman")
                .enqueue(new Callback<DefaultResponse<List<GifDTO>>>() {
                    @Override
                    public void onResponse(@NonNull Call<DefaultResponse<List<GifDTO>>> call,
                                           @NonNull Response<DefaultResponse<List<GifDTO>>> response) {
                        if (response.body() != null) {
                            data = response.body().getData();
                            updateItems(data);
                        } else
                            onFailure(call, new Throwable("Something went wrong..."));
                    }

                    @Override
                    public void onFailure(@NonNull Call<DefaultResponse<List<GifDTO>>> call,
                                          @NonNull Throwable t) {
                        handleError(t);
                    }
                });

//        final Disposable disposable = RestApi.getInstance()
//                .gifs()
//                .search("Bojack Horseman")
//                .map(response -> data = response.getData())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(this::updateItems, this::handleError);
//
//       compositeDisposable.add(disposable);
    }

    private void updateItems(@Nullable List<GifDTO> news) {
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