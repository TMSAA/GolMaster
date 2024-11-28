package com.example.golmaster;

public interface Callback<T> {
    void onSuccess(T result);
    void onFailure(Exception e);
}