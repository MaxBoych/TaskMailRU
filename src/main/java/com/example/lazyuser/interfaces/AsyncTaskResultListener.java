package com.example.lazyuser.interfaces;

public interface AsyncTaskResultListener<T> {

    void onResultAvailable(T result);
}
