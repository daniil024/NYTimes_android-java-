package com.example.myapplication.Endpoints;

import androidx.annotation.Nullable;

public class DefaultResponse<T> {

    public T data;

    @Nullable
    public T getData() {
        return data;
    }
}
