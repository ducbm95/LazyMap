package com.anca.lazymap.GoogleData.PlaceSearch;

/**
 * Created by MinhDuc on 22/01/2016.
 */
public class Photo {

    private int height;
    private int width;
    private String photo_reference;

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public String getPhotoReference() {
        return this.photo_reference;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setPhotoReference(String photo_reference) {
        this.photo_reference = photo_reference;
    }
}
