package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.data.NewsItem;
import com.example.myapplication.network.models.dto.ArticleDTO;
import com.example.myapplication.network.models.dto.MultimediaDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TopStoriesMapper {

    private static final String MULTIMEDIA_TYPE_IMAGE = "image";

    static List<NewsItem> map(@NonNull List<ArticleDTO> dtos) {
        final List<NewsItem> resultNews = new ArrayList<>();

        for (ArticleDTO dto : dtos)
            resultNews.add(mapItem(dto));

        return Collections.unmodifiableList(resultNews);
    }

    private static NewsItem mapItem(@NonNull ArticleDTO article) {

        return new NewsItem(article.getTitle(),
                mapImage(article.getMultimedia()),
                article.getSubsection(),
                article.getPublishDate(),
                article.getAbstractDescription(),
                article.getUrl());
    }

    private static String mapImage(@Nullable List<MultimediaDTO> multimedias) {
        if (multimedias == null || multimedias.isEmpty())
            return null;

        final MultimediaDTO multimedia = multimedias.get(multimedias.size() - 1);

        if (!multimedia.getType().equals(MULTIMEDIA_TYPE_IMAGE))
            return null;

        return multimedia.getUrl();
    }
}
