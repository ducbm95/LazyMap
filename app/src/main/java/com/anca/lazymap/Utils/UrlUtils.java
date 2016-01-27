package com.anca.lazymap.Utils;

/**
 * Created by MinhDuc on 25/01/2016.
 */
public class UrlUtils {
    public static String getPlacePhoto(String photoReference, String apiKey) {
        return "https://maps.googleapis.com/maps/api/place/photo?" +
                "maxwidth=400" +
                "&photoreference=" + photoReference +
                "&key=" + apiKey;
    }
}
