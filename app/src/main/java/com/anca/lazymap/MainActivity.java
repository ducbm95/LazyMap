package com.anca.lazymap;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.anca.lazymap.GoogleData.PlaceSearch.PlaceSearchResult;
import com.anca.lazymap.GoogleData.PlaceSearch.Data;
import com.anca.lazymap.RetrofitService.PlaceSearchService;
import com.anca.lazymap.Utils.PermissionUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit.GsonConverterFactory;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by MinhDuc on 21/01/2016.
 */
public class MainActivity extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final String TAG = "MainActivity";

    private boolean mPermissionDenied = false;

    private GoogleMap mMap;
    private CoordinatorLayout mCoordinator;

    private Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl(PlaceSearchService.NEARBY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private PlaceSearchService mPlaceSearchService = mRetrofit.create(PlaceSearchService.class);
    private Call<Data> mCall;

    private List<Marker> mListMarker = new ArrayList<>();
    private List<PlaceSearchResult> mPlaceSearchResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCoordinator = (CoordinatorLayout) findViewById(R.id.coordinator);

        // Setting the Navigation Drawer
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FloatingActionButton btnNav = (FloatingActionButton) findViewById(R.id.btn_nav);
        btnNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        // Set main content to margin fit system window with transparent status bar
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) drawer.getLayoutParams();
            layoutParams.setMargins(0, -getStatusBarHeight(), 0, 0);
            navigationView.setPadding(0, getStatusBarHeight(), 0, 0);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Get user location data
        buildGoogleApiClient();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_atm) {
            createMarker(PlaceSearchService.ATM);
        } else if (id == R.id.nav_bank) {
            createMarker(PlaceSearchService.BANK);
        } else if (id == R.id.nav_bar) {
            createMarker(PlaceSearchService.BAR);
        } else if (id == R.id.nav_book_store) {
            createMarker(PlaceSearchService.BOOK_STORE);
        } else if (id == R.id.nav_bus_station) {
            createMarker(PlaceSearchService.BUS_STATION);
        } else if (id == R.id.nav_cafe) {
            createMarker(PlaceSearchService.CAFE);
        } else if (id == R.id.nav_church) {
            createMarker(PlaceSearchService.CHURCH);
        } else if (id == R.id.nav_convenience_store) {
            createMarker(PlaceSearchService.CONVENIENCE_STORE);
        } else if (id == R.id.nav_food) {
            createMarker(PlaceSearchService.FOOD);
        } else if (id == R.id.nav_gas_station) {
            createMarker(PlaceSearchService.GAS_STATION);
        } else if (id == R.id.nav_hardware_store) {
            createMarker(PlaceSearchService.HARDWARE_STORE);
        } else if (id == R.id.nav_hospital) {
            createMarker(PlaceSearchService.HOSPITAL);
        } else if (id == R.id.nav_movie_theater) {
            createMarker(PlaceSearchService.MOVIE_THEATER);
        } else if (id == R.id.nav_park) {
            createMarker(PlaceSearchService.PARK);
        } else if (id == R.id.nav_pharmacy) {
            createMarker(PlaceSearchService.PHARMACY);
        } else if (id == R.id.nav_post_office) {
            createMarker(PlaceSearchService.POST_OFFICE);
        } else if (id == R.id.nav_restaurant) {
            createMarker(PlaceSearchService.RESTAURANT);
        } else if (id == R.id.nav_school) {
            createMarker(PlaceSearchService.SCHOOL);
        } else if (id == R.id.nav_shopping_mall) {
            createMarker(PlaceSearchService.SHOPPING_MALL);
        } else if (id == R.id.nav_university) {
            createMarker(PlaceSearchService.UNIVERSITY);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mMap.setPadding(0, getStatusBarHeight(), 0, 0);
        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();

        // Set map listener
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;

    protected void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        moveToMyLocation();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
    }

    private void moveToMyLocation() {
        getLastLocation();
        if (mLastLocation != null) {
            // Move camera to device's last known location
            LatLng coordinate = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 15);
            mMap.moveCamera(yourLocation);
        } else {
            Snackbar.make(mCoordinator, "No location detect", Snackbar.LENGTH_LONG).show();
        }
    }

    private void animateToMyLocation() {
        getLastLocation();
        if (mLastLocation != null) {
            // Animate camera to device's last known location
            LatLng coordinate = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 15);
            mMap.animateCamera(yourLocation);
        } else {
            Snackbar.make(mCoordinator, "No location detect", Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Get the height of StatusBar
     *
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

    /**
     * Load nearby place from Google Place with type
     * Create list of markers on the map
     *
     * @param type
     */
    private void createMarker(String type) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading " + type + "...");
        progressDialog.setProgressStyle(R.style.ProgressBar);
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        getLastLocation();
        if (mLastLocation != null) {
            mCall = mPlaceSearchService.loadPlaceSearchResults(
                    getResources().getString(R.string.google_web_service_key),
                    convertLocationToString(mLastLocation.getLatitude(), mLastLocation.getLongitude()),
                    "1000",
                    type
            );
            mCall.enqueue(new Callback<Data>() {

                @Override
                public void onResponse(Response<Data> response, Retrofit retrofit) {
                    addListMakerToMap(response.body().getPlaceSearchResults());
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        } else {
            Snackbar.make(mCoordinator, "Can not find your location", Snackbar.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }

    private String convertLocationToString(double lat, double lng) {
        return lat + "," + lng;
    }

    /**
     * Add a maker to map
     * @param placeSearchResult
     * @return
     */
    private Marker addMarkerToMap(PlaceSearchResult placeSearchResult) {
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(placeSearchResult.getGeometry().getLocation().getLat(),
                        placeSearchResult.getGeometry().getLocation().getLng()))
                .title(placeSearchResult.getName())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        );
        return marker;
    }

    /**
     * Add list Maker to map
     * @param results
     */
    private void addListMakerToMap(List<PlaceSearchResult> results) {
        mMap.clear();

        mPlaceSearchResults.clear();
        mPlaceSearchResults.addAll(results);

        mListMarker.clear();
        for (PlaceSearchResult item : results) {
            Marker marker = addMarkerToMap(item);
            mListMarker.add(marker);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        for (int i = 0; i < mListMarker.size(); i++) {
            if (marker.equals(mListMarker.get(i))) {
                Intent intent = new Intent(this, DetailActivity.class);
                intent.putExtra("placeid", mPlaceSearchResults.get(i).getPlaceId());
                intent.putExtra("lat", mPlaceSearchResults.get(i).getGeometry().getLocation().getLat());
                intent.putExtra("lng", mPlaceSearchResults.get(i).getGeometry().getLocation().getLng());
                startActivity(intent);
                return;
            }
        }
    }
}