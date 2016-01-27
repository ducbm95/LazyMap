package com.anca.lazymap.RecyclerView.RecyclerObj;

import com.anca.lazymap.GoogleData.PlaceSearch.Photo;

import java.util.List;

/**
 * Created by MinhDuc on 26/01/2016.
 */
public class Pager {
    private List<Photo> photos;

    public Pager(List<Photo> photos) {
        this.photos = photos;
    }

    public List<Photo> getPhotos() {
        return photos;
    }
}
