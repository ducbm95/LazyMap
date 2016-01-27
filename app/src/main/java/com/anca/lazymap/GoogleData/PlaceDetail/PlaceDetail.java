package com.anca.lazymap.GoogleData.PlaceDetail;

import com.anca.lazymap.GoogleData.PlaceSearch.Geometry;
import com.anca.lazymap.GoogleData.PlaceSearch.Photo;

import java.util.List;

/**
 * Created by MinhDuc on 24/01/2016.
 */
public class PlaceDetail {
    private String formatted_address;
    private String formatted_phone_number;
    private Geometry geometry;
    private String icon;
    private String id;
    private String international_phone_number;
    private List<Photo> photos;
    private String name;
    private String place_id;
    private String scope;
    private float rating;
    private List<Review> reviews;
    private List<String> types;
    private String url;
    private String vicinity;
    private String website;

    public String getFormattedAddress() {
        return formatted_address;
    }

    public String getFormattedPhoneNumber() {
        return formatted_phone_number;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public String getIcon() {
        return icon;
    }

    public String getId() {
        return id;
    }

    public String getInternationalPhoneNumber() {
        return international_phone_number;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public String getName() {
        return name;
    }

    public String getPlaceId() {
        return place_id;
    }

    public String getScope() {
        return scope;
    }

    public float getRating() {
        return rating;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public List<String> getTypes() {
        return types;
    }

    public String getUrl() {
        return url;
    }

    public String getVicinity() {
        return vicinity;
    }

    public String getWebsite() {
        return website;
    }
}
