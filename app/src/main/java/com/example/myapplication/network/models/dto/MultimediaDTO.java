package com.example.myapplication.network.models.dto;

import com.google.gson.annotations.SerializedName;

public class MultimediaDTO {

    @SerializedName("url")
    private String url;

    @SerializedName("type")
    private String type;

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }

}
