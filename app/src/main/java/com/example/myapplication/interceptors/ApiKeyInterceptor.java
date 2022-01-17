package com.example.myapplication.interceptors;

import androidx.annotation.NonNull;

import com.bumptech.glide.RequestManager;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiKeyInterceptor implements Interceptor {

    private static final String API_KEY = "mJYE4u0gGi3Mzo1bSXd1lYarVdDfBEw7";
    private static final String API_KEY_HEADER_NAME = "api_key";

    public static ApiKeyInterceptor create() {
        return new ApiKeyInterceptor();
    }

    private ApiKeyInterceptor() {
    }


    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        final Request requestWithoutApiKey = chain.request();

        final HttpUrl url = requestWithoutApiKey.url()
                .newBuilder()
                .addQueryParameter(API_KEY_HEADER_NAME, API_KEY)
                .build();

        final Request requestWithAttachedApiKey = requestWithoutApiKey.newBuilder()
                .url(url)
                //.addHeader(API_KEY_HEADER_NAME, API_KEY)
                .build();

        return chain.proceed(requestWithAttachedApiKey);
    }
}
