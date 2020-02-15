package com.example.lazyuser.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lazyuser.R;
import com.example.lazyuser.config.AppConfig;
import com.example.lazyuser.models.RelatedImageItem;

import java.util.ArrayList;
import java.util.List;

public class RelatedImageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RelatedImageItem> mList;
    private Context mContext;

    public RelatedImageListAdapter(List<RelatedImageItem> list, Context context) {
        if (list != null) {
            mList = new ArrayList<>(list);
        }
        mContext = context;
    }

    public void updateList(List<RelatedImageItem> list) {
        mList = new ArrayList<>(list);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.related_image_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RelatedImageItem item = mList.get(position);
        ImageViewHolder imageHolder = (ImageViewHolder) holder;

        String source = item.getSource();
        //Log.d(AppConfig.APPLICATION_TAG, source);
        if (source != null) {
            Glide.with(mContext)
                    .load(source)
                    .centerCrop()
                    .into(imageHolder.image);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image;

        ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }
}
