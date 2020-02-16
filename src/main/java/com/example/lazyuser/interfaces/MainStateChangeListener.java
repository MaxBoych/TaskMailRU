package com.example.lazyuser.interfaces;

import androidx.fragment.app.Fragment;

public interface MainStateChangeListener {

    void onImageListScreen();

    void onHtmlRequestScreen(Fragment parent);

    void onNetworkLostScreen();
}
