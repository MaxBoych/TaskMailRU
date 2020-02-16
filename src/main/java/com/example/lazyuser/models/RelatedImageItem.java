package com.example.lazyuser.models;

import android.graphics.Bitmap;

public class RelatedImageItem {

    private Bitmap bitmap;
    private String source;

    public RelatedImageItem(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public RelatedImageItem(String source) {
        this.source = source;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getSource() {
        return source;
    }
}
