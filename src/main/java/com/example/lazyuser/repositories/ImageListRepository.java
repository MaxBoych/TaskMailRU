package com.example.lazyuser.repositories;

import android.util.Log;

import com.example.lazyuser.config.AppConfig;
import com.example.lazyuser.interfaces.OnDownloadDataListener;
import com.example.lazyuser.models.RelatedImageItem;
import com.example.lazyuser.utils.AsyncRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ImageListRepository {

    private List<RelatedImageItem> mList;

    public ImageListRepository() {
        mList = new ArrayList<>();
    }

    public void downloadRelatedImages(String url, OnDownloadDataListener<List<RelatedImageItem>> listener) {
        AsyncRequest request = new AsyncRequest(url);
        try {
            String result = request.execute().get();
            if (request.getResultCode() == AppConfig.REQUEST_CODE_SUCCESS) {
                Log.d(AppConfig.APPLICATION_TAG, AppConfig.RELATED_DOWNLOADED_SUCCESS);

                Elements images = Jsoup.parse(result).select(AppConfig.RELATED_IMAGE_SOURCE_TAG_NAME);
                for (Element image : images) {
                    String source = image.absUrl(AppConfig.RELATED_IMAGE_SOURCE_ATTR_NAME);
                    mList.add(new RelatedImageItem(source));
                }
                listener.onDownloadSuccessful(mList);
            } else {
                Log.e(AppConfig.APPLICATION_TAG, AppConfig.RELATED_DOWNLOADED_FAIL);
                Log.e(AppConfig.APPLICATION_TAG, result);

                listener.onFailure(AppConfig.RELATED_DOWNLOADED_FAIL);
            }
        } catch (ExecutionException | InterruptedException e) {
            if (e.getMessage() != null) {
                Log.e(AppConfig.APPLICATION_TAG, e.getMessage());
                listener.onFailure(AppConfig.RELATED_DOWNLOADED_FAIL);
            }
        }
    }
}
