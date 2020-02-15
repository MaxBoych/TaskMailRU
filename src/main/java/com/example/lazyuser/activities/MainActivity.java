package com.example.lazyuser.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.lazyuser.R;
import com.example.lazyuser.config.AppConfig;
import com.example.lazyuser.fragments.ImageListFragment;
import com.example.lazyuser.fragments.HtmlRequestDialog;
import com.example.lazyuser.interfaces.OnMainStateChangeListener;

public class MainActivity extends AppCompatActivity implements OnMainStateChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onImageListScreen();
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
}
