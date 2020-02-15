package com.example.lazyuser.models;

import java.util.ArrayList;
import java.util.List;

public class ImageItem {

    private String source;
    private String url;
    private String size;
    private List<RelatedImageItem> relatedImageList;

    public ImageItem() {}

    public ImageItem(String source, String url, String size) {
        this.source = source;
        this.url = url;
        this.size = size;
    }

    public String getSource() {
        return source;
    }

    public String getUrl() {
        return url;
    }

    public String getSize() {
        return size;
    }

    public List<RelatedImageItem> getRelatedImageList() {
        return relatedImageList;
    }

    public void setRelatedImageList(List<RelatedImageItem> relatedImageList) {
        this.relatedImageList = new ArrayList<>(relatedImageList);
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
