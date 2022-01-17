package com.example.myapplication.DTOData;

import com.google.gson.annotations.SerializedName;

public class GifDTO {

    @SerializedName("user")
    private UserDTO user;
    @SerializedName("images")
    private ImagesDTO images;

    public String getUrl(){
        return images.getOriginal().getUrl();
    }
}
