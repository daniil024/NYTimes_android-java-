package com.example.myapplication.network.endpoints;

import androidx.annotation.NonNull;

import com.example.myapplication.network.models.dto.TopStoriesResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TopStoriesEndpoint {

    @GET("topstories/v2/{section}.json")
    Single<TopStoriesResponse> get(@Path("section") @NonNull String section);

    @GET("topstories/v2/{section}.json")
    Call<TopStoriesResponse> getByCall(@Path("section") @NonNull String section);
}
