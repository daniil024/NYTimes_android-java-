package com.example.myapplication.data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class NewsItem implements Serializable { //implements Parcelable

    private final String title;
    private final String imageUrl;
    private final String category;
    private final Date publishDate;
    private final String previewText;
    private final String fullText;

    public NewsItem(String title, String imageUrl,
                    String category, Date publishDate,
                    String previewText, String fullText) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.category = category;
        this.publishDate = publishDate;
        this.previewText = previewText;
        this.fullText = fullText;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public String getPreviewText() {
        return previewText;
    }

    public String getFullText() {
        return fullText;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null || o.getClass() != this.getClass())
            return false;

        NewsItem newsItem = (NewsItem) o;
        return newsItem.getTitle().equals(this.getTitle())
                && newsItem.getImageUrl().equals(this.getImageUrl())
                && newsItem.getCategory().equals(this.getCategory())
                && newsItem.getPublishDate().equals(this.getPublishDate())
                && newsItem.getPreviewText().equals(this.getPreviewText())
                && newsItem.getFullText().equals(this.getFullText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, imageUrl, category, publishDate, previewText, fullText);
    }
}