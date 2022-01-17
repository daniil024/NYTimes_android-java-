package com.example.myapplication.DTOData;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GifResponse {

    @SerializedName("data")
    private List<GifDTO> data;

    @Nullable
    public List<GifDTO> getData(){
        return data;
    }
}
