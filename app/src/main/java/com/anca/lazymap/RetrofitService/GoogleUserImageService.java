package com.anca.lazymap.RetrofitService;

import com.anca.lazymap.GoogleData.GoogleUser.Data;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by MinhDuc on 27/01/2016.
 */
public interface GoogleUserImageService {
    String GOOGLE_USER_BASE_URL = "https://www.googleapis.com";

    /**
     *
     * @param gid
     * Google User unique ID
     *
     * @param key
     * Google Web Service API
     *
     * @param fields
     * type of data to return
     * default in this case is "image"
     *
     * @return
     * Google User Image - url
     */
    @GET("/plus/v1/people/{gid}")
    Call<Data> loadGoogleUserImage (
            @Path("gid") String gid,
            @Query("key") String key,
            @Query("fields") String fields
    );
}
