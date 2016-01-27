package com.anca.lazymap.RetrofitService;

import com.anca.lazymap.GoogleData.PlaceDetail.Data;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by MinhDuc on 24/01/2016.
 */
public interface PlaceDetailService {
    String DETAIL_BASE_URL = "https://maps.googleapis.com";

    /**
     *
     * Load place detail from Google Place API
     * Text below is copied on https://developers.google.com/places/web-service/search
     * by Minh Duc
     *
     * @param key
     * (required) — Your application's API key.
     * This key identifies your application for purposes of quota management and
     * so that places added from your application are made immediately available
     * to your app. See Get a key for more information.
     * @param placeid
     * A textual identifier that uniquely identifies a place, returned from
     * a Place Search. For more information about place IDs, see the place
     * ID overview.
     *
     * @return
     */
    @GET("/maps/api/place/details/json")
    Call<Data> loadPlaceDetail (
            @Query("key") String key,
            @Query("placeid") String placeid
    );
}
