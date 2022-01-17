package com.example.myapplication.Utils;

import androidx.annotation.NonNull;

import com.example.myapplication.DTOData.GifResponse;
import com.example.myapplication.Endpoints.GifEndpoint;
import com.example.myapplication.interceptors.ApiKeyInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApi {

    private static RestApi restApi;
    private static final String BASE_URL = "https://api.giphy.com";

    public static synchronized RestApi getInstance() {
        if (restApi == null)
            restApi = new RestApi();

        return restApi;
    }

    private final GifEndpoint gifEndpoint;

    private RestApi() {
        final OkHttpClient client = buildClient();
        final Retrofit retrofit = buildRetrofit(client);

        gifEndpoint = retrofit.create(GifEndpoint.class);
    }

    private Retrofit buildRetrofit(@NonNull OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    private OkHttpClient buildClient() {

        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        return new OkHttpClient.Builder()
                .addInterceptor(ApiKeyInterceptor.create())
                .addInterceptor(interceptor)
                .build();
    }

    @NonNull
    public GifEndpoint gifs(){
        return gifEndpoint;
    }
}
