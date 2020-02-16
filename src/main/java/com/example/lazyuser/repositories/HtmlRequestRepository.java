package com.example.lazyuser.repositories;

import android.util.Log;

import com.example.lazyuser.config.AppConfig;
import com.example.lazyuser.interfaces.DownloadDataListener;
import com.example.lazyuser.utils.AsyncRequestHtml;

public class HtmlRequestRepository {

    public HtmlRequestRepository() {
    }

    public void downloadImagesHtml(String word, DownloadDataListener<String> listener) {

        String url = AppConfig.URL_FIRST_PART + word + AppConfig.URL_SECOND_PART;
        new AsyncRequestHtml(result -> {
            if (result.getFirst() == AppConfig.REQUEST_CODE_SUCCESS) {
                Log.d(AppConfig.APPLICATION_TAG, AppConfig.DOWNLOADED_SUCCESS);
                listener.onDownloadSuccessful(result.getSecond());
            } else {
                Log.d(AppConfig.APPLICATION_TAG, AppConfig.DOWNLOADED_FAIL);
                Log.d(AppConfig.APPLICATION_TAG, result.getSecond());
                listener.onFailure(AppConfig.DOWNLOADED_FAIL);
            }
        }).execute(url);
    }
}
