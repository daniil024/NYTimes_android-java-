package com.example.myapplication.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.data.NewsItem;
import com.example.myapplication.database.daos.NewsDao;
import com.example.myapplication.database.entites.NewsEntity;
import com.example.myapplication.network.models.dto.ArticleDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.TimeZone;

import io.reactivex.rxjava3.disposables.Disposable;

public class Converter2DBEntity {

    public static List<NewsItem> fromDatabase(@NonNull List<NewsEntity> news) {
        List<NewsItem> result = new ArrayList<>();
        for (NewsEntity n : news) {
            result.add(convert2NewsItem(n));
        }

        return result;
    }

    public static void toDatabase(@NonNull Context context, List<ArticleDTO> articles) {
        List<NewsEntity> news = new ArrayList<>();
        for (ArticleDTO a : articles) {
            news.add(convert2NewsEntity(a));
        }

        AppDatabase.getInstance(context).newsDao().insertAll(news.toArray(new NewsEntity[0]));
    }

    public static NewsEntity convert2NewsEntity(ArticleDTO article) {
        NewsEntity news = new NewsEntity();
        news.setTitle(article.getTitle());
        news.setCategory(article.getSubsection());
        news.setPreviewText(article.getAbstractDescription());
        news.setFullText(article.getUrl());
        news.setPublishDate(article.getPublishDate().toString());
        if (article.getMultimedia() != null) {
            news.setImageUrl(article.getMultimedia().get(article.getMultimedia().size() - 1).getUrl());
        }
        return news;
    }

    public static NewsItem convert2NewsItem(NewsEntity newsEntity) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.getDefault());
            date = format.parse(newsEntity.getPublishDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new NewsItem(newsEntity.getTitle(),
                newsEntity.getImageUrl(),
                newsEntity.getCategory(),
                date,
                newsEntity.getPreviewText(),
                newsEntity.getFullText());
    }
}
