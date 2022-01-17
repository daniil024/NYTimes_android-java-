package com.example.myapplication.DTOData;

import com.google.gson.annotations.SerializedName;

public class UserDTO {

    @SerializedName("display_name")
    String displayName;

    @SerializedName("photo_url")
    String photoUrl;

    @SerializedName("about")
    String about;

    @SerializedName("is_star")
    boolean isStar;

    @SerializedName("ages")
    byte ages;

}
