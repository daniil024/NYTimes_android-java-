package com.example.myapplication.network;

import androidx.annotation.NonNull;

import com.example.myapplication.network.endpoints.TopStoriesEndpoint;
import com.example.myapplication.network.interceptor.ApiKeyInterceptor;

import java.util.concurrent.TimeUnit;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApi {

    private static RestApi restApi;
    private static final String BASE_URL="https://api.nytimes.com/svc/";

    private final TopStoriesEndpoint topStoriesEndpoint;

    public static synchronized RestApi getInstance(){
        if(restApi==null)
            restApi = new RestApi();
        return restApi;
    }

    public RestApi() {
        Retrofit retrofit = buildRetrofit(buildClient());
        topStoriesEndpoint = retrofit.create(TopStoriesEndpoint.class);
    }

    private Retrofit buildRetrofit(@NonNull OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    private OkHttpClient buildClient(){
        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        return new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new ApiKeyInterceptor())
                .build();
    }

    public TopStoriesEndpoint getTopStories(){
        return topStoriesEndpoint;
    }

    public TopStoriesEndpoint getTopStoriesByCall(){
        return topStoriesEndpoint;
    }
}
