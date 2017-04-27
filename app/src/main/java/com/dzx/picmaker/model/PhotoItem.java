package com.dzx.picmaker.model;

import android.support.annotation.NonNull;

/**
 * Created by dengzhaoxuan on 2017/4/27.
 * 图片类
 */

public class PhotoItem implements Comparable<PhotoItem> {
    private String imgUri;
    private long date;

    public PhotoItem(String imgUri, long date) {
        this.imgUri = imgUri;
        this.date = date;
    }

    public PhotoItem(String imgUri) {
        this.imgUri = imgUri;
    }
    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }


    @Override
    public int compareTo(@NonNull PhotoItem another) {
        if (another == null) {
            return 1;
        }
        return (int) ((another.getDate() - date) / 1000);
    }
}
