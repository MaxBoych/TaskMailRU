package com.example.lazyuser.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.lazyuser.R;
import com.example.lazyuser.application.LazyUserApplication;
import com.example.lazyuser.config.AppConfig;
import com.example.lazyuser.fragments.ImageListFragment;
import com.example.lazyuser.fragments.HtmlRequestDialog;
import com.example.lazyuser.fragments.NetworkLostDialog;
import com.example.lazyuser.interfaces.MainStateChangeListener;

public class MainActivity extends AppCompatActivity implements MainStateChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        observeNetworkState();
        onImageListScreen();
    }

    private void observeNetworkState() {
        LazyUserApplication.from(getApplicationContext())
                .getNetworkState()
                .observe(this, networkState -> {
                    if (networkState == AppConfig.NetworkState.LOST) {
                        onNetworkLostScreen();
                    } else if (networkState == AppConfig.NetworkState.AVAILABLE) {
                        Toast.makeText(getApplicationContext(),
                                AppConfig.NETWORK_AVAILABLE_MESSAGE,
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
    }

    @Override
    public void onImageListScreen() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, new ImageListFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onHtmlRequestScreen(Fragment parent) {
        HtmlRequestDialog dialog = new HtmlRequestDialog();
        dialog.setTargetFragment(parent, AppConfig.DATEPICKER_FRAGMENT);
        dialog.show(getSupportFragmentManager(), null);
    }

    @Override
    public void onNetworkLostScreen() {
        NetworkLostDialog dialog = new NetworkLostDialog();
        dialog.show(getSupportFragmentManager(), null);
    }
}
