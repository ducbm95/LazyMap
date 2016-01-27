package com.anca.lazymap;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.anca.lazymap.GoogleData.PlaceDetail.Data;
import com.anca.lazymap.GoogleData.PlaceDetail.PlaceDetail;
import com.anca.lazymap.RecyclerView.RecyclerObj.Detail;
import com.anca.lazymap.RecyclerView.RecyclerObj.Map;
import com.anca.lazymap.RecyclerView.RecyclerObj.Pager;
import com.anca.lazymap.RecyclerView.RecyclerObj.Tag;
import com.anca.lazymap.RecyclerView.RecyclerObj.Title;
import com.anca.lazymap.RecyclerView.RecyclerViewAdapter;
import com.anca.lazymap.RetrofitService.PlaceDetailService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class DetailActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    /**
     * Prepare for RESTful call
     * Use Retrofit library
     */
    private Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl(PlaceDetailService.DETAIL_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private PlaceDetailService mPlaceDetailService = mRetrofit.create(PlaceDetailService.class);
    private Call<Data> mCall;

    private PlaceDetail mPlaceDetail;
    private String mPlaceId;
    private double mLat;
    private double mLng;

    private RelativeLayout mContainer;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Initial the View component
        mContainer = (RelativeLayout) findViewById(R.id.container_detail);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        // Get intent extra data
        mPlaceId = getIntent().getStringExtra("placeid");
        mLat = getIntent().getDoubleExtra("lat", 0);
        mLng = getIntent().getDoubleExtra("lng", 0);
        loadDetailInfo();

        // Set main content to margin fit system window with transparent status bar
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mContainer.getLayoutParams();
            layoutParams.setMargins(0, -getStatusBarHeight(), 0, 0);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(mLat, mLng);
        googleMap.addMarker(new MarkerOptions().position(latLng));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.0f));
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    /**
     *
     */
    private void loadDetailInfo() {
        mCall = mPlaceDetailService.loadPlaceDetail(
                getResources().getString(R.string.google_web_service_key),
                mPlaceId
        );
        mCall.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Response<Data> response, Retrofit retrofit) {
                mPlaceDetail = response.body().getResult();
                initialViewComponent();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    /**
     * Initial view component in this activity
     * Create list of Object to RecyclerViewAdapter
     * Set adapter for RecyclerView
     */
    private void initialViewComponent() {
        List<Object> items = new ArrayList<>();
        items.add(new Map(mLat, mLng));
        items.add(new Title(mPlaceDetail.getName()));
        items.add(new Detail(R.drawable.ic_place_black_24dp, mPlaceDetail.getFormattedAddress()));
        items.add(new Detail(R.drawable.ic_phone_black_24dp, mPlaceDetail.getInternationalPhoneNumber()));
        items.add(new Detail(R.drawable.ic_public_black_24dp, mPlaceDetail.getWebsite()));
        items.add(new Tag(R.drawable.ic_label_black_24dp, mPlaceDetail.getTypes()));
        items.add(new Pager(mPlaceDetail.getPhotos()));
        if (mPlaceDetail.getReviews() != null)
            items.addAll(mPlaceDetail.getReviews());

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(items, this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemViewCacheSize(items.size());
    }

    /**
     * Get the height of StatusBar
     * @return result in pixcel
     */
    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
