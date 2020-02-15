package com.example.lazyuser.repositories;

import android.util.Log;

import com.example.lazyuser.config.AppConfig;
import com.example.lazyuser.interfaces.OnDownloadDataListener;
import com.example.lazyuser.utils.AsyncRequest;

import java.util.concurrent.ExecutionException;

public class HtmlRequestRepository {

    public HtmlRequestRepository() {
    }

    public void downloadImagesHtml(String word, OnDownloadDataListener<String> listener) {

        String url = AppConfig.URL_FIRST_PART + word + AppConfig.URL_SECOND_PART;;
        AsyncRequest request = new AsyncRequest(url);
        try {
            String result = request.execute().get();
            int code = request.getResultCode();
            if (code == AppConfig.REQUEST_CODE_SUCCESS) {
                Log.d(AppConfig.APPLICATION_TAG, AppConfig.DOWNLOADED_SUCCESS);
                listener.onDownloadSuccessful(result);
            } else {
                Log.d(AppConfig.APPLICATION_TAG, AppConfig.DOWNLOADED_FAIL);
                Log.d(AppConfig.APPLICATION_TAG, result);
                listener.onFailure(AppConfig.DOWNLOADED_FAIL);
            }

        } catch (ExecutionException | InterruptedException e) {
            if (e.getMessage() != null) {
                Log.e(AppConfig.APPLICATION_TAG, e.getMessage());
                listener.onFailure(AppConfig.DOWNLOADED_FAIL);
            }
        }
    }
}
