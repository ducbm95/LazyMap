package com.anca.lazymap.Slider;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.anca.lazymap.R;
import com.anca.lazymap.Utils.UrlUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by MinhDuc on 25/01/2016.
 */
public class ImageSliderFragment extends Fragment {

    private int mPhotoNum;
    private ImageView mImageView;

    public static ImageSliderFragment newInstance(int photoNum) {
        final ImageSliderFragment f = new ImageSliderFragment();
        final Bundle args = new Bundle();
        args.putInt("resId", photoNum);
        f.setArguments(args);
        return f;
    }

    public ImageSliderFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPhotoNum = getArguments() != null ? getArguments().getInt("resId") : -1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.slider_item, container, false);
        mImageView = (ImageView) v.findViewById(R.id.iv_slider_item);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Picasso.with(getContext())
                .load(UrlUtils.getPlacePhoto(ImageSliderAdapter.mPhotos.get(mPhotoNum).getPhotoReference(),
                        getResources().getString(R.string.google_web_service_key)))
                .into(mImageView);
    }
}
