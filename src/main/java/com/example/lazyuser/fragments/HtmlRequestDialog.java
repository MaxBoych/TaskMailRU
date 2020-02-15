package com.example.lazyuser.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.lazyuser.R;
import com.example.lazyuser.config.AppConfig;
import com.example.lazyuser.viewmodels.HtmlRequestViewModel;

public class HtmlRequestDialog extends DialogFragment implements View.OnClickListener {

    private HtmlRequestViewModel mViewModel;

    private Button mSearch;
    private EditText mSearchWord;
    private ProgressBar mProgressBar;

   /* private OnMainStateChangeListener mOnMainStateChangeListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mOnMainStateChangeListener = (OnMainStateChangeListener) context;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_search_request, viewGroup, false);

        mProgressBar = view.findViewById(R.id.progress_bar);
        mSearchWord = view.findViewById(R.id.search_word);
        mSearch = view.findViewById(R.id.search_button);

        initViewModel();

        mSearch.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search_button && !mSearchWord.getText().toString().isEmpty()) {
            mViewModel.downloadHtml(mSearchWord.getText().toString());
        }
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(HtmlRequestViewModel.class);
        mViewModel.setContext(getContext());
        observeLoadState();
    }

    private void observeLoadState() {
        mViewModel.getLoadState().observe(this, state -> {
            switch (state) {
                case SUCCESS:
                    sendData(mViewModel.getHtml());
                    dismiss();
                    setLoadProgressBarVisibility(View.GONE);
                    this.setCancelable(true);
                    break;
                case NONE:
                    setLoadProgressBarVisibility(View.GONE);
                    this.setCancelable(true);
                    break;
                case FAIL:
                    setLoadProgressBarVisibility(View.GONE);
                    showError();
                    this.setCancelable(true);
                    break;
                case PROGRESS:
                    setLoadProgressBarVisibility(View.VISIBLE);
                    this.setCancelable(false);
                    break;
            }
        });
    }

    private void sendData(String html) {
        Intent intent = new Intent();
        intent.putExtra(AppConfig.WORD_KEY, mSearchWord.getText().toString());
        intent.putExtra(AppConfig.HTML_KEY, html);
        if (getTargetFragment() != null) {
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        }
    }

    private void showError() {
        Toast.makeText(getContext(),
                mViewModel.getErrorMessage().getValue(),
                Toast.LENGTH_LONG)
                .show();
    }

    private void setLoadProgressBarVisibility(int view) {
        mProgressBar.setVisibility(view);
    }
}
