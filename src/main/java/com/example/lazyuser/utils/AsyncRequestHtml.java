package com.example.lazyuser.utils;

import android.os.AsyncTask;

import com.example.lazyuser.config.AppConfig;
import com.example.lazyuser.interfaces.AsyncTaskResultListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class AsyncRequestHtml extends AsyncTask<String, Void, Couple<Integer, String>> {

    private AsyncTaskResultListener<Couple<Integer, String>> listener;

    public AsyncRequestHtml(AsyncTaskResultListener<Couple<Integer, String>> listener) {
        this.listener = listener;
    }

    @Override
    protected Couple<Integer, String> doInBackground(String... args) {

        Document document;
        try {
            document = Jsoup.connect(args[AppConfig.ARGS_URL_INDEX])
                    .maxBodySize(AppConfig.JSOUP_MAX_SIZE)
                    .timeout(AppConfig.JSOUP_TIMEOUT)
 //                   .ignoreContentType(true)
//                    .ignoreHttpErrors(false)
//                    .followRedirects(false)
                    .get();
        } catch (IOException e) {
            return new Couple<>(
                    AppConfig.REQUEST_CODE_FAIL,
                    e.getMessage()
            );
        }

        return new Couple<>(
                AppConfig.REQUEST_CODE_SUCCESS,
                document.body().html()
        );
    }

    @Override
    protected void onPostExecute(Couple<Integer, String> result) {
        super.onPostExecute(result);
        listener.onResultAvailable(result);
    }
}
