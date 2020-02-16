package com.example.lazyuser.application;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lazyuser.config.AppConfig;

public class LazyUserApplication extends Application {

    private MutableLiveData<AppConfig.NetworkState> mNetworkState;

    @Override
    public void onCreate() {
        super.onCreate();

        mNetworkState = new MutableLiveData<>();
        mNetworkState.setValue(AppConfig.NetworkState.NONE);
        addNetworkListener();
    }

    public static LazyUserApplication from(Context context) {
        return (LazyUserApplication) context.getApplicationContext();
    }

    public static Context getContext() {
        return getContext();
    }

    private void addNetworkListener() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            connectivityManager.registerNetworkCallback(new NetworkRequest.Builder().build(),
                    new ConnectivityManager.NetworkCallback() {

                        @Override
                        public void onAvailable(@NonNull Network network) {
                            super.onAvailable(network);
                            mNetworkState.postValue(AppConfig.NetworkState.AVAILABLE);
                        }

                        @Override
                        public void onLost(@NonNull Network network) {
                            super.onLost(network);
                            mNetworkState.postValue(AppConfig.NetworkState.LOST);
                        }
                    });
        }
    }

    public LiveData<AppConfig.NetworkState> getNetworkState() {
        return mNetworkState;
    }
}
