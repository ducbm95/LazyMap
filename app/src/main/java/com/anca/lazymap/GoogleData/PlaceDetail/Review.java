package com.anca.lazymap.GoogleData.PlaceDetail;

import java.util.List;

/**
 * Created by MinhDuc on 24/01/2016.
 */
public class Review {
    private List<Aspect> aspects;
    private String author_name;
    private String author_url;
    private String language;
    private float rating;
    private String text;
    private int time;

    public List<Aspect> getAspects() {
        return aspects;
    }

    public String getAuthorName() {
        return author_name;
    }

    public String getAuthorUrl() {
        return author_url;
    }

    public String getLanguage() {
        return language;
    }

    public float getRating() {
        return rating;
    }

    public String getText() {
        return text;
    }

    public int getTime() {
        return time;
    }
}
