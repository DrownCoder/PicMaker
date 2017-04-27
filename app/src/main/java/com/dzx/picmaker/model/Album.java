package com.dzx.picmaker.model;

import java.util.List;

/**
 * Created by dengzhaoxuan on 2017/4/27.
 * 相册类
 */

public class Album {
    private String albumUri;
    private String title;
    private List<PhotoItem> photos;

    public Album(String uri, String title, List<PhotoItem> photos) {
        this.albumUri = uri;
        this.title = title;
        this.photos = photos;
    }

    public String getAlbumUri() {
        return albumUri;
    }

    public void setAlbumUri(String albumUri) {
        this.albumUri = albumUri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<PhotoItem> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoItem> photos) {
        this.photos = photos;
    }
}
