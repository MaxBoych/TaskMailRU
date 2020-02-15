package com.example.lazyuser.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lazyuser.R;
import com.example.lazyuser.config.AppConfig;
import com.example.lazyuser.interfaces.OnListItemClickListener;
import com.example.lazyuser.models.ImageItem;
import com.example.lazyuser.models.RelatedImageItem;

import java.util.ArrayList;
import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnListItemClickListener mOnListItemClickListener;

    private List<ImageItem> mList;
    private RecyclerView.RecycledViewPool mViewPool;
    private Context mContext;

    public ImageListAdapter(List<ImageItem> list, Context context) {
        if (list != null) {
            mList = new ArrayList<>(list);
        }
        mViewPool = new RecyclerView.RecycledViewPool();
        mContext = context;
    }

    public void setOnListItemClickListener(OnListItemClickListener listener) {
        mOnListItemClickListener = listener;
    }

    public void updateList(List<ImageItem> list) {
        mList = new ArrayList<>(list);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ImageItem item = mList.get(position);
        ImageViewHolder itemHolder = (ImageViewHolder) holder;
        itemHolder.url.setText(item.getUrl());
        itemHolder.size.setText(item.getSize());

        String source = item.getSource();
        if (source != null) {
            Glide.with(mContext)
                    .load(source)
                    .into(itemHolder.image);
        }

        List<RelatedImageItem> related = item.getRelatedImageList();
        if (related != null) {
            Log.d(AppConfig.APPLICATION_TAG, related.size() + "");
            Log.d(AppConfig.APPLICATION_TAG, related.get(0).getSource());
            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    /*mContext*/  itemHolder.list.getContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
            );
            layoutManager.setInitialPrefetchItemCount(related.size());
            itemHolder.list.setLayoutManager(layoutManager);

            RelatedImageListAdapter adapter = new RelatedImageListAdapter(related, mContext);
            itemHolder.list.setAdapter(adapter);
            //adapter.notifyDataSetChanged();
            itemHolder.list.setRecycledViewPool(mViewPool);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image;
        private final TextView url;
        private final TextView size;
        private final RecyclerView list;

        ImageViewHolder(@NonNull View itemView) {

            super(itemView);

            image = itemView.findViewById(R.id.image);
            url = itemView.findViewById(R.id.url);
            size = itemView.findViewById(R.id.size);
            list = itemView.findViewById(R.id.related_image_list);

            itemView.setOnClickListener(v -> {
                if (mOnListItemClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mOnListItemClickListener.onItemClicked(position);
                    }
                }
            });
        }
    }
}
