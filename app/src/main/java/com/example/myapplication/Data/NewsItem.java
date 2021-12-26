package com.example.myapplication.Data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class NewsItem implements Serializable { //implements Parcelable

    private final String title;
    private final String imageUrl;
    private final Category category;
    private final Date publishDate;
    private final String previewText;
    private final String fullText;

//    protected NewsItem(Parcel in) {
//        String[] arr = new String[4];
//        in.readStringArray(arr);
//        this.title = arr[0];
//        this.imageUrl = arr[1];
//        this.previewText = arr[2];
//        this.fullText = arr[3];
//        this.publishDate = new Date(in.readLong());
//        this.category = in.readParcelable(Category.class.getClassLoader());
//    }

    public NewsItem(String title, String imageUrl, Category category, Date publishDate, String previewText, String fullText) {
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

    public Category getCategory() {
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
                || newsItem.getImageUrl().equals(this.getImageUrl())
                || newsItem.getCategory().equals(this.getCategory())
                || newsItem.getPublishDate().equals(this.getPublishDate())
                || newsItem.getPreviewText().equals(this.getPreviewText())
                || newsItem.getFullText().equals(this.getFullText());
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeStringArray(new String[]
//                {getTitle(), getImageUrl(), getPreviewText(), getFullText()});
//        dest.writeLong(getPublishDate().getTime());
//        dest.writeParcelable(getCategory(), flags);
//    }
//
//    public static final Parcelable.Creator<NewsItem> CREATOR = new Parcelable.Creator<NewsItem>() {
//        public NewsItem createFromParcel(Parcel in) {
//            return new NewsItem(in);
//        }
//
//        public NewsItem[] newArray(int size) {
//            return new NewsItem[size];
//        }
//    };
}