package com.example.lazyuser.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lazyuser.R;
import com.example.lazyuser.adapters.ImageListAdapter;
import com.example.lazyuser.config.AppConfig;
import com.example.lazyuser.interfaces.MainStateChangeListener;
import com.example.lazyuser.models.ImageItem;
import com.example.lazyuser.models.RelatedImageItem;
import com.example.lazyuser.viewmodels.ImageListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ImageListFragment extends Fragment implements View.OnClickListener {

    private RecyclerView mImageList;
    private ImageListAdapter mAdapter;
    private ImageListViewModel mViewModel;
    private ProgressBar mProgressBar;

    private FloatingActionButton mNewSearch;
    private int mClickedPosition;

    private MainStateChangeListener mMainStateChangeListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mMainStateChangeListener = (MainStateChangeListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image_list, viewGroup, false);

        mImageList = view.findViewById(R.id.image_list);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mNewSearch = view.findViewById(R.id.new_search_button);

        initViewModel();

        mNewSearch.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.new_search_button) {
            mMainStateChangeListener.onHtmlRequestScreen(this);
        }
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(ImageListViewModel.class);
        initAdapter();
        observeLoadState();
    }

    private void initAdapter() {
        mImageList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        mAdapter = new ImageListAdapter(mViewModel.getImageList(), getContext());
        mAdapter.setOnListItemClickListener(position -> {
            mClickedPosition = position;
            ImageItem item = mViewModel.getImageItem(position);
            List<RelatedImageItem> related = item.getRelatedImageList();
            if (related != null && !related.isEmpty()) {
                mViewModel.changeRelatedImagesVisibility();
            } else if (related != null) {
                showResponseForInvisible();
            } else {
                prohibitTouch();
                mViewModel.downloadRelatedImages(item.getUrl());
            }
        });
        mImageList.setAdapter(mAdapter);
    }

    private void observeLoadState() {
        mViewModel.getLoadState().observe(this, state -> {
            switch (state) {
                case SUCCESS:
                    List<RelatedImageItem> related = mViewModel.getRelatedImageList();
                    if (related != null) {
                        mViewModel.updateRelated(mClickedPosition);
                        mAdapter.notifyItemChanged(mClickedPosition);
                    } else {
                        updateAdapter();
                    }
                    setLoadProgressBarVisibility(View.GONE);
                    allowTouch();
                    break;
                case NONE:
                    setLoadProgressBarVisibility(View.GONE);
                    allowTouch();
                    break;
                case FAIL:
                    setLoadProgressBarVisibility(View.GONE);
                    showError();
                    allowTouch();
                    break;
                case PROGRESS:
                    setLoadProgressBarVisibility(View.VISIBLE);
                    prohibitTouch();
                    break;
            }
        });
    }

    private void updateAdapter() {
        mAdapter.updateList(mViewModel.getImageList());
    }

    private void showError() {
        Toast.makeText(getContext(),
                mViewModel.getErrorMessage().getValue(),
                Toast.LENGTH_LONG)
                .show();
    }

    private void showResponseForInvisible() {
        Toast.makeText(getContext(),
                AppConfig.RELATED_DOWNLOADED_INVISIBLE,
                Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConfig.DATEPICKER_FRAGMENT &&
                resultCode == Activity.RESULT_OK &&
                data != null) {

            Bundle bundle = data.getExtras();
            String html = "";
            if (bundle != null) {
                html = bundle.getString(AppConfig.HTML_KEY);
            }
            mViewModel.parseHtml(html);
        }
    }

    private void setLoadProgressBarVisibility(int view) {
        mProgressBar.setVisibility(view);
    }

    private void allowTouch() {
        if (getActivity() != null) {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    private void prohibitTouch() {
        if (getActivity() != null) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }
}
