package com.anca.lazymap.RetrofitService;

import com.anca.lazymap.GoogleData.PlaceSearch.Data;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by MinhDuc on 22/01/2016.
 */
public interface PlaceSearchService {

    String NEARBY_BASE_URL = "https://maps.googleapis.com";

    /**
     * List of support types
     */
    String ATM = "atm";
    String BANK = "bank";
    String BAR = "bar";
    String BOOK_STORE = "book_store";
    String BUS_STATION = "bus_station";
    String CAFE = "cafe";
    String CHURCH = "church";
    String CONVENIENCE_STORE = "convenience_store";
    String FOOD = "food";
    String GAS_STATION = "gas_station";
    String HARDWARE_STORE = "hardware_store";
    String HOSPITAL = "hospital";
    String MOVIE_THEATER = "movie_theater";
    String PARK = "park";
    String PHARMACY = "pharmacy";
    String POST_OFFICE = "post_office";
    String RESTAURANT = "restaurant";
    String SCHOOL = "school";
    String SHOPPING_MALL = "shopping_mall";
    String UNIVERSITY = "university";

    /**
     * Load place search results from Google Place API
     * Text below is copied on https://developers.google.com/places/web-service/search
     * by Minh Duc
     *
     * @param key
     * Your application's API key. This key identifies your application for purposes of quota
     * managemen and so that places added from your application are made immediately available to your app.
     * See Get a key for more information.
     *
     * @param location
     * The latitude/longitude around which to retrieve place information.
     * This must be specified as latitude,longitude.
     *
     * @param radius
     * Defines the distance (in meters) within which to return place results.
     * The maximum allowed radius is 50 000 meters. Note that radius must not be included if rankby=distance
     * (described under Optional parameters below) is specified
     *
     * @param keyword
     *  A term to be matched against all content that Google has indexed for this place,
     *  including but not limited to name, type, and address, as well as customer reviews
     *  and other third-party content.
     *
     * @param language
     * The language code, indicating in which language the results should be returned, if possible.
     * See the list of supported languages and their codes. Note that we often update supported
     * languages so this list may not be exhaustive.
     *
     * @param minprice
     * @param maxprice
     * Restricts results to only those places within the specified range.
     * Valid values range between 0 (most affordable) to 4 (most expensive), inclusive.
     * The exact amount indicated by a specific value will vary from region to region.
     *
     * @param name
     * One or more terms to be matched against the names of places, separated with a space character.
     * Results will be restricted to those containing the passed name values.
     * Note that a place may have additional names associated with it, beyond its listed name.
     * The API will try to match the passed name value against all of these names.
     * As a result, places may be returned in the results whose listed names do not match
     * the search term, but whose associated names do.
     *
     * @param opennow
     * Returns only those places that are open for business at the time the query is sent.
     * Places that do not specify opening hours in the Google Places database will not be returned
     * if you include this parameter in your query.
     *
     * @param rankby
     * Specifies the order in which results are listed. Possible values are:
     * --prominence (default). This option sorts results based on their importance.
     * Ranking will favor prominent places within the specified area.
     * Prominence can be affected by a place's ranking in Google's index, global popularity,
     * and other factors.
     * --distance. This option biases search results in ascending order by their distance from
     * the specified location. When distance is specified, one or more of
     * keyword, name, or types is required.
     *
     * @param types
     * Restricts the results to places matching at least one of the specified types.
     * Types should be separated with a pipe symbol, like this:
     * type1|type2|etc
     *
     * @param pagetoken
     * Returns the next 20 results from a previously run search.
     * Setting a pagetoken parameter will execute a search with the same parameters used previously
     * — all parameters other than pagetoken will be ignored.
     *
     * @param zagatselected
     * Add this parameter (just the parameter name, with no associated value) to restrict
     * your search to locations that are Zagat selected businesses. This parameter must not include
     * a true or false value. The zagatselected parameter is experimental, and is only available
     * to Google Places API customers with a Premium Plan license.
     *
     * @return
     */

    @GET("/maps/api/place/nearbysearch/json")
    Call<Data> loadPlaceSearchResults (
            @Query("key") String key,
            @Query("location") String location,
            @Query("radius") String radius,
            @Query("keyword") String keyword,
            @Query("language") String language,
            @Query("minprice") String minprice,
            @Query("maxprice") String maxprice,
            @Query("name") String name,
            @Query("opennow") String opennow,
            @Query("rankby") String rankby,
            @Query("types") String types,
            @Query("pagetoken") String pagetoken,
            @Query("zagatselected") String zagatselected
    );

    @GET("/maps/api/place/nearbysearch/json")
    Call<Data> loadPlaceSearchResults (
            @Query("key") String key,
            @Query("location") String location,
            @Query("radius") String radius
    );

    @GET("/maps/api/place/nearbysearch/json")
    Call<Data> loadPlaceSearchResults (
            @Query("key") String key,
            @Query("location") String location,
            @Query("radius") String radius,
            @Query("types") String types
    );
}
