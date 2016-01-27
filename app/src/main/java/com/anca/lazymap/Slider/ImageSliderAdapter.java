package com.anca.lazymap.Slider;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.anca.lazymap.GoogleData.PlaceSearch.Photo;

import java.util.List;

/**
 * Created by MinhDuc on 25/01/2016.
 */
public class ImageSliderAdapter extends FragmentPagerAdapter {

    public static List<Photo> mPhotos;

    public ImageSliderAdapter(FragmentManager fm, List<Photo> photos) {
        super(fm);
        mPhotos = photos;
    }

    @Override
    public Fragment getItem(int position) {
        return new ImageSliderFragment().newInstance(position);
    }

    @Override
    public int getCount() {
        return mPhotos != null ? mPhotos.size() : 0;
    }
}
