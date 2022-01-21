package com.example.myapplication.network.models.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopStoriesResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("results")
    private List<ArticleDTO> news;


    public String getStatus() {
        return status;
    }

    public List<ArticleDTO> getNews() {
        return news;
    }
}
