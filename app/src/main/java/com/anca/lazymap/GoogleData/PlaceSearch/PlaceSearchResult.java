package com.anca.lazymap.GoogleData.PlaceSearch;

import java.util.List;

/**
 * Created by MinhDuc on 22/01/2016.
 */
public class PlaceSearchResult {

    private Geometry geometry;
    private String icon;
    private String id;
    private String name;
    private OpeningHour opening_hours;
    private List<Photo> photos;
    private String place_id;
    private String scope;
    private List<AltId> alt_ids;
    private String reference;
    private List<String> types;
    private String vicinity;

    /**
     * Set method
     */

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOpeningHours(OpeningHour opening_hours) {
        this.opening_hours = opening_hours;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public void setPlaceId(String place_id) {
        this.place_id = place_id;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setAltIds(List<AltId> alt_ids) {
        this.alt_ids = alt_ids;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    /**
     * Get method
     */

    public Geometry getGeometry() {
        return this.geometry;
    }

    public String getIcon() {
        return this.icon;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public OpeningHour getOpening_hours() {
        return opening_hours;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public String getPlaceId() {
        return place_id;
    }

    public String getScope() {
        return scope;
    }

    public List<AltId> getAltIds() {
        return alt_ids;
    }

    public String getReference() {
        return reference;
    }

    public List<String> getTypes() {
        return types;
    }

    public String getVicinity() {
        return vicinity;
    }

}
