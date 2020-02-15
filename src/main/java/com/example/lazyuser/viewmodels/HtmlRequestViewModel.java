package com.example.lazyuser.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lazyuser.config.AppConfig;
import com.example.lazyuser.interfaces.OnDownloadDataListener;
import com.example.lazyuser.repositories.HtmlRequestRepository;

public class HtmlRequestViewModel extends ViewModel {

    private String mHtml;

    private MutableLiveData<AppConfig.LoadStageState> mLoadState;
    private MutableLiveData<String> mErrorMessage;

    private Context mContext;

    public HtmlRequestViewModel() {
        mLoadState = new MutableLiveData<>();
        mLoadState.setValue(AppConfig.LoadStageState.NONE);
        mErrorMessage = new MutableLiveData<>();
        mErrorMessage.setValue(AppConfig.DEFAULT_ERROR_VALUE);
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void downloadHtml(String word) {
        mLoadState.setValue(AppConfig.LoadStageState.PROGRESS);
        new HtmlRequestRepository().downloadImagesHtml(word, new OnDownloadDataListener<String>() {
            @Override
            public void onDownloadSuccessful(String data) {
                updateHtml(data);
            }

            @Override
            public void onFailure(String errorMessage) {
                updateError(errorMessage);
            }
        });
    }

    private void updateHtml(String html) {
        mHtml = html;
        mLoadState.setValue(AppConfig.LoadStageState.SUCCESS);
    }

    private void updateError(String errorMessage) {
        mErrorMessage.setValue(errorMessage);
        mLoadState.setValue(AppConfig.LoadStageState.FAIL);
    }

    public String getHtml() {
        return mHtml;
    }

    public LiveData<AppConfig.LoadStageState> getLoadState() {
        return mLoadState;
    }

    public LiveData<String> getErrorMessage() {
        return mErrorMessage;
    }
}
