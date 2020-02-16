package com.example.lazyuser.interfaces;

public interface DownloadDataListener<T> {

    void onDownloadSuccessful(T obj);

    void onFailure(String errorMessage);
}