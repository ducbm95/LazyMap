package com.anca.lazymap.RecyclerView;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.anca.lazymap.GoogleData.PlaceDetail.Review;
import com.anca.lazymap.GoogleData.PlaceSearch.Photo;
import com.anca.lazymap.R;
import com.anca.lazymap.RecyclerView.RecyclerObj.Detail;
import com.anca.lazymap.RecyclerView.RecyclerObj.Map;
import com.anca.lazymap.RecyclerView.RecyclerObj.Pager;
import com.anca.lazymap.RecyclerView.RecyclerObj.Tag;
import com.anca.lazymap.RecyclerView.RecyclerObj.Title;
import com.anca.lazymap.RetrofitService.GoogleUserImageService;
import com.anca.lazymap.Slider.ImageSliderAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by MinhDuc on 26/01/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements OnMapReadyCallback {

    private static final String TAG = "RecyclerViewAdapter";

    /**
     * Define list of ViewType
     */
    public static final int TYPE_DEFAULT = -1;
    public static final int TYPE_MAP = 1;
    public static final int TYPE_TITLE = 2;
    public static final int TYPE_DETAIL = 3;
    public static final int TYPE_TAG = 6;
    public static final int TYPE_PAGER = 4;
    public static final int TYPE_REVIEW = 5;

    private List<Object> mItems;
    private AppCompatActivity mContext;
    private Map mMapObj;

    /**
     * Prepare for RESTful call
     * Use Retrofit library
     */
    private Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl(GoogleUserImageService.GOOGLE_USER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private GoogleUserImageService mGoogleUserImage = mRetrofit.create(GoogleUserImageService.class);
    private Call<com.anca.lazymap.GoogleData.GoogleUser.Data> mCall;

    public RecyclerViewAdapter(List<Object> items, AppCompatActivity context) {
        mItems = items;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_MAP:
                view = inflater.inflate(R.layout.item_map, parent, false);
                viewHolder = new ViewHolder1(view);
                break;
            case TYPE_TITLE:
                view = inflater.inflate(R.layout.item_title, parent, false);
                viewHolder = new ViewHolder2(view);
                break;
            case TYPE_DETAIL:
                view = inflater.inflate(R.layout.item_detail, parent, false);
                viewHolder = new ViewHolder3(view);
                break;
            case TYPE_PAGER:
                view = inflater.inflate(R.layout.item_pager, parent, false);
                viewHolder = new ViewHolder4(view);
                break;
            case TYPE_REVIEW:
                view = inflater.inflate(R.layout.item_review, parent, false);
                viewHolder = new ViewHolder5(view);
                break;
            case TYPE_TAG:
                view = inflater.inflate(R.layout.item_tag, parent, false);
                viewHolder = new ViewHolder6(view);
                break;
            default:
                viewHolder = null;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_MAP:
                ViewHolder1 vh1 = (ViewHolder1) holder;
                mMapObj = (Map) mItems.get(position);
                vh1.mMapFragment.getMapAsync(this);
                break;
            case TYPE_TITLE:
                ViewHolder2 vh2 = (ViewHolder2) holder;
                vh2.mTvTitle.setText(((Title) mItems.get(position)).getTitle());
                break;
            case TYPE_DETAIL:
                ViewHolder3 vh3 = (ViewHolder3) holder;
                Detail detail = ((Detail) mItems.get(position));
                vh3.mIcon.setImageDrawable(mContext.getResources().getDrawable(
                        detail.getIcon()
                ));
                if (detail.getDetail() != null)
                    vh3.mTvDetail.setText(detail.getDetail());
                else
                    vh3.mTvDetail.setText(mContext.getResources().getString(R.string.app_no_data));
                break;
            case TYPE_PAGER:
                ViewHolder4 vh4 = (ViewHolder4) holder;
                Pager pager = (Pager) mItems.get(position);
                if (pager.getPhotos() == null) {
                    vh4.itemView.getLayoutParams().height = 0;
                    break;
                }
                ImageSliderAdapter mAdapter = new ImageSliderAdapter(
                        mContext.getSupportFragmentManager(),
                        pager.getPhotos());
                vh4.mPager.setAdapter(mAdapter);
                break;
            case TYPE_REVIEW:
                ViewHolder5 vh5 = (ViewHolder5) holder;
                Review review = (Review) mItems.get(position);
                if (review.getAuthorUrl() != null) {
                    String gId = review.getAuthorUrl().substring(24);
                    loadGoogleUserImage(gId, vh5.mIvAuthorAvatar);
                }
                vh5.mTvAuthorName.setText(review.getAuthorName());
                vh5.mTvContent.setText(review.getText());
                vh5.mRatingBar.setRating(review.getRating());
                break;
            case TYPE_TAG:
                ViewHolder6 vh6 = (ViewHolder6) holder;
                vh6.mIcon.setImageDrawable(mContext.getResources().getDrawable(
                        ((Tag) mItems.get(position)).getIcon()
                ));
                List<String> tags = ((Tag) mItems.get(position)).getTypes();
                if (tags != null)
                    vh6.mTvTag.setText(tags.get(0));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mItems.get(position);
        if (item instanceof Map) {
            return TYPE_MAP;
        } else if (item instanceof Title) {
            return TYPE_TITLE;
        } else if (item instanceof Detail) {
            return TYPE_DETAIL;
        } else if (item instanceof Tag) {
            return TYPE_TAG;
        } else if (item instanceof Pager) {
            return TYPE_PAGER;
        } else if (item instanceof Review) {
            return TYPE_REVIEW;
        }
        return TYPE_DEFAULT;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(mMapObj.getLat(), mMapObj.getLng());
        googleMap.addMarker(new MarkerOptions().position(latLng));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.0f));
        googleMap.getUiSettings().setAllGesturesEnabled(false);
    }

    private void loadGoogleUserImage(String gID, final ImageView imageView) {
        mCall = mGoogleUserImage.loadGoogleUserImage(
                gID,
                mContext.getResources().getString(R.string.google_web_service_key),
                "image"
        );
        mCall.enqueue(new Callback<com.anca.lazymap.GoogleData.GoogleUser.Data>() {
            @Override
            public void onResponse(Response<com.anca.lazymap.GoogleData.GoogleUser.Data> response,
                                   Retrofit retrofit) {
                Picasso.with(mContext)
                        .load(response.body().getImage().getUrl())
                        .into(imageView);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    /**
     * Define MAPView
     */
    class ViewHolder1 extends RecyclerView.ViewHolder {

        public SupportMapFragment mMapFragment;

        public ViewHolder1(View itemView) {
            super(itemView);
            mMapFragment = (SupportMapFragment) mContext.getSupportFragmentManager()
                    .findFragmentById(R.id.map);
        }
    }

    /**
     * Define Title View (below Map View)
     */
    class ViewHolder2 extends RecyclerView.ViewHolder {

        public TextView mTvTitle;

        public ViewHolder2(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }

    /**
     * Define Detail View
     * Include location, global phone, website
     */
    class ViewHolder3 extends RecyclerView.ViewHolder {

        public ImageView mIcon;
        public TextView mTvDetail;

        public ViewHolder3(View itemView) {
            super(itemView);
            mIcon = (ImageView) itemView.findViewById(R.id.iv_icon_detail);
            mTvDetail = (TextView) itemView.findViewById(R.id.tv_detail);
        }
    }

    /**
     * Define Image Location View
     * Using ViewPager to display the list of Location Image
     */
    class ViewHolder4 extends RecyclerView.ViewHolder {

        public ViewPager mPager;

        public ViewHolder4(View itemView) {
            super(itemView);
            mPager = (ViewPager) itemView.findViewById(R.id.viewpager);
        }
    }

    /**
     * Define Review View
     */
    class ViewHolder5 extends RecyclerView.ViewHolder {

        public ImageView mIvAuthorAvatar;
        public TextView mTvAuthorName;
        public TextView mTvContent;
        public RatingBar mRatingBar;

        public ViewHolder5(View itemView) {
            super(itemView);
            mIvAuthorAvatar = (ImageView) itemView.findViewById(R.id.iv_author_avatar);
            mTvAuthorName = (TextView) itemView.findViewById(R.id.tv_author_name);
            mTvContent = (TextView) itemView.findViewById(R.id.tv_content_text);
            mRatingBar = (RatingBar) itemView.findViewById(R.id.ratingbar);
        }
    }

    /**
     * Define the tag of Location
     * Ex: bank, atm, school, ext
     * This view is below Detail View
     */
    class ViewHolder6 extends RecyclerView.ViewHolder {

        public ImageView mIcon;
        public TextView mTvTag;

        public ViewHolder6(View itemView) {
            super(itemView);
            mIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
            mTvTag = (TextView) itemView.findViewById(R.id.tv_tag);
        }
    }
}
