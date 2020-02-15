package com.example.lazyuser.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lazyuser.config.AppConfig;
import com.example.lazyuser.interfaces.OnDownloadDataListener;
import com.example.lazyuser.models.ImageItem;
import com.example.lazyuser.models.RelatedImageItem;
import com.example.lazyuser.repositories.ImageListRepository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ImageListViewModel extends ViewModel {

    private List<ImageItem> mImageList;
    private List<RelatedImageItem> mRelatedImageList;

    private MutableLiveData<AppConfig.LoadStageState> mLoadState;
    private MutableLiveData<String> mErrorMessage;

    private Context mContext;

    public ImageListViewModel() {
        mImageList = new ArrayList<>();
        //mRelatedImageList = new ArrayList<>();
        mLoadState = new MutableLiveData<>();
        mLoadState.setValue(AppConfig.LoadStageState.NONE);
        mErrorMessage = new MutableLiveData<>();
        mErrorMessage.setValue(AppConfig.DEFAULT_ERROR_VALUE);
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void downloadRelatedImages(String url) {
        mLoadState.setValue(AppConfig.LoadStageState.PROGRESS);
        new ImageListRepository().downloadRelatedImages(url, new OnDownloadDataListener<List<RelatedImageItem>>() {
            @Override
            public void onDownloadSuccessful(List<RelatedImageItem> data) {
                updateRelatedList(data);
            }

            @Override
            public void onFailure(String errorMessage) {
                updateError(errorMessage);
            }
        });
    }

    public void parseHtml(String html) {
        mLoadState.setValue(AppConfig.LoadStageState.PROGRESS);
        Document document = Jsoup.parse(html);
        Elements images = document.getElementsByClass(AppConfig.IMAGE_CLASS_NAME);
        for (Element image : images) {
            String size = image.getElementsByClass(AppConfig.IMAGE_SIZE_CLASS_NAME).first().text();
            String url = image.getElementsByClass(AppConfig.IMAGE_URL_CLASS_NAME).first().attr(AppConfig.IMAGE_URL_ATTR_NAME);
            String source = image.getElementsByClass(AppConfig.IMAGE_SOURCE_CLASS_NAME).attr(AppConfig.IMAGE_SOURCE_ATTR_NAME);
            Log.d(AppConfig.APPLICATION_TAG, source);
            mImageList.add(new ImageItem(source, url, size));
        }
        mLoadState.setValue(AppConfig.LoadStageState.SUCCESS);
    }

    public List<RelatedImageItem> getRelatedImageList() {
        return mRelatedImageList;
    }

    private void updateRelatedList(List<RelatedImageItem> list) {
        mRelatedImageList = new ArrayList<>(list);
        mLoadState.setValue(AppConfig.LoadStageState.SUCCESS);
    }

   /* public void resetRelated() {
        mRelatedImageList = null;
    }*/

    public ImageItem updateRelated(int position) {
        ImageItem item = mImageList.get(position);
        item.setRelatedImageList(mRelatedImageList);
        mRelatedImageList = null;
        return item;
    }

    private void updateError(String errorMessage) {
        mErrorMessage.setValue(errorMessage);
        mLoadState.setValue(AppConfig.LoadStageState.FAIL);
    }

    public ImageItem getImageItem(int position) {
        return mImageList.get(position);
    }

    public List<ImageItem> getImageList() {
        return mImageList;
    }

    public LiveData<AppConfig.LoadStageState> getLoadState() {
        return mLoadState;
    }

    public LiveData<String> getErrorMessage() {
        return mErrorMessage;
    }
}
