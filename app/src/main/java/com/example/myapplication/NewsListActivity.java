package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.data.NewsItem;
import com.example.myapplication.adapter.recycler.NYTimesRecyclerAdapter;
import com.example.myapplication.adapter.spinner.CategoriesSpinnerAdapter;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.Converter2DBEntity;
import com.example.myapplication.database.entites.NewsEntity;
import com.example.myapplication.network.RestApi;
import com.example.myapplication.network.models.NewsCategory;
import com.example.myapplication.network.models.dto.ArticleDTO;
import com.example.myapplication.network.models.dto.TopStoriesResponse;
import com.example.myapplication.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
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

    @Nullable
    private Toolbar toolbar;
    @Nullable
    private ProgressBar progressBar;
    @Nullable
    private View error;
    @Nullable
    private Button errorAction;
    @Nullable
    private RecyclerView recycler;
    @Nullable
    private Spinner spinnerCategories;
    @Nullable
    private FloatingActionButton floatingActionButton;

    private NYTimesRecyclerAdapter newsAdapter;
    private CategoriesSpinnerAdapter categoriesAdapter;

    @Nullable
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newslistactivity_main);
        setUpUI();
        setUpUX();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // loadItems(categoriesAdapter.getSelectedCategory().serverValue());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Utils.setViewVisibility(recycler, true);
        Utils.setViewVisibility(floatingActionButton, true);
        Utils.setViewVisibility(progressBar, false);
        Utils.setViewVisibility(error, false);
    }


    /*
    Load items to recyclerView with RXJava
     */
    private void loadItems(@NonNull String category) {
        Utils.setViewVisibility(progressBar, true);
        Utils.setViewVisibility(recycler, false);
        Utils.setViewVisibility(floatingActionButton, false);
        Utils.setViewVisibility(error, false);

        Call<TopStoriesResponse> searchRequest;
        searchRequest = RestApi.getInstance()
                .getTopStoriesByCall().getByCall(category);

        searchRequest.enqueue(new Callback<TopStoriesResponse>() {
            @Override
            public void onResponse(@NonNull Call<TopStoriesResponse> call,
                                   @NonNull Response<TopStoriesResponse> response) {
                List<ArticleDTO> news;
                if (response.body() != null) {
                    news = response.body().getNews();
                    //updateItems(TopStoriesMapper.map(response.body().getNews()));
                    Converter2DBEntity.toDatabase(NewsListActivity.this, news);
                }
                // при нажатии кнопки у нас данные дропаются в БД и потом ресайклер считывает из БД
            }

            @Override
            public void onFailure(@NonNull Call<TopStoriesResponse> call,
                                  @NonNull Throwable t) {
                handleError(t);
            }
        });

        if (compositeDisposable != null) {
            compositeDisposable.add(AppDatabase.getInstance(NewsListActivity.this).newsDao()
                    //.getNewsByCategory(category)
                    .getAll()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::updateItems, this::handleError));
        }

    }

    private void updateItems(@Nullable List<NewsEntity> news) {

        List<NewsItem> result = new ArrayList<>();
        if (news != null) {
            result = Converter2DBEntity.fromDatabase(news);
        }

        if (newsAdapter != null && news != null) {
            newsAdapter.replaceData(result);
        }

        Utils.setViewVisibility(recycler, true);
        Utils.setViewVisibility(floatingActionButton, true);
        Utils.setViewVisibility(progressBar, false);
        Utils.setViewVisibility(error, false);
    }

    private void handleError(Throwable th) {
        if (Utils.isDebug())
            Log.e(TAG, th.getMessage(), th);

        Utils.setViewVisibility(error, true);
        Utils.setViewVisibility(floatingActionButton, false);
        Utils.setViewVisibility(recycler, false);
        Utils.setViewVisibility(progressBar, false);
    }


    private void setUpRecyclerViewAdapter() {
        newsAdapter = new NYTimesRecyclerAdapter(
                item -> DetailedWebViewActivity.start(this, item), Glide.with(this));
        if (recycler != null) {
            recycler.setAdapter(newsAdapter);
            //recycler.addItemDecoration(new NewsItemDecoration(getResources().getDimensionPixelSize(R.dimen.spacing_micro)));
            if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
                final int column_count = R.integer.landscape_news_columns_count;
                recycler.setLayoutManager(new GridLayoutManager(this, column_count));
            } else
                recycler.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (compositeDisposable != null)
            compositeDisposable.clear();

        categoriesAdapter = null;
        newsAdapter = null;
        recycler = null;
        toolbar = null;
        progressBar = null;
        errorAction = null;
        error = null;
        spinnerCategories = null;
    }

    private void findViews() {
        toolbar = findViewById(R.id.main_toolbar);
        progressBar = findViewById(R.id.progress_bar);
        error = findViewById(R.id.error_layout);
        errorAction = findViewById(R.id.error_action_button);
        spinnerCategories = findViewById(R.id.spinner_categories);
        recycler = findViewById(R.id.recycler_view);
        floatingActionButton = findViewById(R.id.floating_action_button);
    }

    private void setUpUI() {
        findViews();
        setUpRecyclerViewAdapter();
        final NewsCategory[] categories = NewsCategory.values();
        categoriesAdapter = CategoriesSpinnerAdapter.createDefault(this, categories);
        if (spinnerCategories != null)
            spinnerCategories.setAdapter(categoriesAdapter);
    }

    private void setUpUX() {
        if (errorAction != null) {
            errorAction.setOnClickListener(v ->
                    loadItems(categoriesAdapter.getSelectedCategory().serverValue()));
        }
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }
        if (spinnerCategories != null) {
            categoriesAdapter.setOnCategorySelectedListener(spinnerCategories);
        }
        if (floatingActionButton != null) {
            floatingActionButton.setOnClickListener(
                    v -> loadItems(categoriesAdapter
                            .getSelectedCategory()
                            .serverValue()));
        }
    }
}