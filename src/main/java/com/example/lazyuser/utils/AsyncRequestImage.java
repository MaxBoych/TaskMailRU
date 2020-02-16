package com.example.lazyuser.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.lazyuser.config.AppConfig;
import com.example.lazyuser.interfaces.AsyncTaskResultListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncRequestImage extends AsyncTask<String, Void, Triple<Integer, Integer, String>> {

    private AsyncTaskResultListener<Triple<Integer, Integer, String>> listener;

    //private Bitmap bitmap;

    public AsyncRequestImage(AsyncTaskResultListener<Triple<Integer, Integer, String>> listener) {
        this.listener = listener;
    }

    @Override
    protected Triple<Integer, Integer, String> doInBackground(String... args) {

        Bitmap bitmap = getBitmapFromURL(args[AppConfig.ARGS_URL_INDEX]);
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            return new Triple<>(width, height, args[AppConfig.ARGS_URL_INDEX]);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Triple<Integer, Integer, String> result) {
        super.onPostExecute(result);
        listener.onResultAvailable(result);
    }

    private Bitmap getBitmapFromURL(String src) {

        /*Glide.with(LazyUserApplication.getContext())
                .asBitmap()
                .load(src)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        bitmap = resource;
                    }
                });*/
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla 5.0 (Windows; U; "
                    + "Windows NT 5.1; en-US; rv:1.8.0.11) ");
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}