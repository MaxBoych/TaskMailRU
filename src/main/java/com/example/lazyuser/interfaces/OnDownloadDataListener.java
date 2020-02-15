package com.example.lazyuser.interfaces;

import java.util.List;

public interface OnDownloadDataListener<T> {

    void onDownloadSuccessful(T obj);

    void onFailure(String errorMessage);
}