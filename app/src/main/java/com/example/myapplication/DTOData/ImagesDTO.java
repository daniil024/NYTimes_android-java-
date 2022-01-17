package com.example.myapplication.DTOData;

import com.google.gson.annotations.SerializedName;

public class ImagesDTO {

    @SerializedName("original")
    private OriginalDTO original;

    public OriginalDTO getOriginal() {
        return original;
    }
}
