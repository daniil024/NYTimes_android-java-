package com.example.myapplication.network.interceptor;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiKeyInterceptor implements Interceptor {

    private static final String TOP_STORIES_API_KEY = "lldO8stNovdJNVUx1kWUKGWbLkBttnyC";
    private static final String API_KEY_HEADER_NAME = "api-key";


    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        final Request requestWithoutApiKey = chain.request();
        final HttpUrl url = requestWithoutApiKey.url()
                .newBuilder()
                .addQueryParameter(API_KEY_HEADER_NAME, TOP_STORIES_API_KEY)
                .build();

        final Request requestWithApiKey = requestWithoutApiKey.newBuilder()
                .url(url)
                .addHeader(API_KEY_HEADER_NAME, TOP_STORIES_API_KEY)
                .build();

        return chain.proceed(requestWithApiKey);
    }
}
