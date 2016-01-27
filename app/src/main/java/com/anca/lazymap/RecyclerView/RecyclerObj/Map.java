package com.anca.lazymap.RecyclerView.RecyclerObj;

/**
 * Created by MinhDuc on 26/01/2016.
 */
public class Map {
    private double lat;
    private double lng;

    public Map(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
