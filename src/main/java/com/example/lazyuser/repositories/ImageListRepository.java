package com.example.lazyuser.repositories;

import android.util.Log;

import com.example.lazyuser.config.AppConfig;
import com.example.lazyuser.interfaces.DownloadDataListener;
import com.example.lazyuser.models.RelatedImageItem;
import com.example.lazyuser.utils.AsyncRequestHtml;
import com.example.lazyuser.utils.AsyncRequestImage;
import com.example.lazyuser.utils.Couple;
import com.example.lazyuser.utils.Triple;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ImageListRepository {

    private List<RelatedImageItem> mList;
    private int mAmount;
    private int mCount;

    private DownloadDataListener<List<RelatedImageItem>> mListener;

    public ImageListRepository() {
        mList = new ArrayList<>();
        mAmount = 0;
        mCount = 0;
    }

    public void downloadRelatedImages(String url, DownloadDataListener<List<RelatedImageItem>> listener) {
        mListener = listener;
        new AsyncRequestHtml(this::afterReceivingHtml).execute(url);
    }

    private void afterReceivingHtml(Couple<Integer, String> result) {
        if (result.getFirst() == AppConfig.REQUEST_CODE_SUCCESS) {
            Log.d(AppConfig.APPLICATION_TAG, AppConfig.RELATED_DOWNLOADED_SUCCESS);

            Elements images = Jsoup.parse(result.getSecond())
                    .select(AppConfig.RELATED_IMAGE_SOURCE_TAG_NAME);
            mAmount = images.size();
            Log.d(AppConfig.APPLICATION_TAG, mAmount + "");
            for (Element image : images) {
                String source = image.absUrl(AppConfig.RELATED_IMAGE_SOURCE_ATTR_NAME);
                if (source.isEmpty()) {
                    source = image.absUrl(AppConfig.RELATED_IMAGE_SOURCE_ATTR_NAME_RESERVE);
                    if (source.isEmpty()) {
                        Log.d(AppConfig.APPLICATION_TAG, "source is empty!");
                    }
                }
                Log.d(AppConfig.APPLICATION_TAG, "HERE");
                new AsyncRequestImage(this::afterReceivingImage).execute(source);
            }
        } else {
            Log.d(AppConfig.APPLICATION_TAG, AppConfig.RELATED_DOWNLOADED_FAIL);
            Log.d(AppConfig.APPLICATION_TAG, result.getSecond());
            mListener.onFailure(AppConfig.RELATED_DOWNLOADED_FAIL);
        }
    }

    private void afterReceivingImage(Triple<Integer, Integer, String> result) {
        if (result != null && result.getThird() != null &&
                result.getFirst() >= AppConfig.IMAGE_MINIMAL_WIDTH_SIZE &&
                result.getSecond() >= AppConfig.IMAGE_MINIMAL_HEIGHT_SIZE) {

            mList.add(new RelatedImageItem(result.getThird()));
        }
        checkReadiness();
    }

    private void checkReadiness() {
        mCount++;
        Log.d(AppConfig.APPLICATION_TAG, mCount + " " + mAmount);
        if (mCount == mAmount) {
            mListener.onDownloadSuccessful(mList);
        }
    }
}
