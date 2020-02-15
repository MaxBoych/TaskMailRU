package com.example.lazyuser.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.example.lazyuser.config.AppConfig;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class AsyncRequest extends AsyncTask<String, Integer, String> {

    private String url;
    private int resultCode;

    public AsyncRequest(String url) {
        this.url = url;
    }

    @Override
    protected String doInBackground(String... args) {

        Document document;
        try {
            document = Jsoup.connect(url)
                    .maxBodySize(0)
                    .timeout(60000)
                    .ignoreHttpErrors(true)
                    .followRedirects(true)
                    .get();
            Log.d(AppConfig.APPLICATION_TAG, url);
            Log.d(AppConfig.APPLICATION_TAG, document.body().toString());
        } catch (IOException e) {
            resultCode = AppConfig.REQUEST_CODE_FAIL;
            return e.getMessage();
        }

        resultCode = AppConfig.REQUEST_CODE_SUCCESS;
        return document.body().html();
    }

    public int getResultCode() {
        return resultCode;
    }
}