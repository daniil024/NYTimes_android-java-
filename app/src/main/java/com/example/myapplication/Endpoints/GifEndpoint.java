package com.example.myapplication.Endpoints;

import com.example.myapplication.DTOData.GifDTO;
import com.example.myapplication.DTOData.GifResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GifEndpoint {
    @GET("/v1/gifs/search")
    Call<DefaultResponse<List<GifDTO>>> search(@Query("q") String search);

//    @GET("/v1/gifs/search")
//    Single<DefaultResponse<List<GifDTO>>> search(@Query("q") String search);
}
