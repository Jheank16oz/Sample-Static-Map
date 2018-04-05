package com.example.global.selectlocation.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.global.selectlocation.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

public class SelectMapLocationView extends ConstraintLayout implements View.OnClickListener{

    /* variables necesarias en los   */
    public static final int REQUEST_SELECT_LOCATION = 12;

    /* widgets necesarios para las vistas    */
    private LayoutInflater mInflater;
    private TextView mTittleTextView;
    private TextView mSubtitleTextView;
    private ImageView mMapImageView;

    /* propiedades */
    private String tittle;
    private Intent informationMap;
    private double latitude;
    private double longitude;


    public SelectMapLocationView(Context context,String tittle) {
        super(context);
        this.tittle = tittle;
        mInflater = LayoutInflater.from(context);
        init();
    }



    public SelectMapLocationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public SelectMapLocationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        init();
    }

    private void init() {
        View v = mInflater.inflate(R.layout.location_map_view_content, this, true);
        mTittleTextView = v.findViewById(R.id.tittle);
        mSubtitleTextView = v.findViewById(R.id.subtitle);
        mMapImageView = v.findViewById(R.id.map);
        v.setOnClickListener(this);
        bind();

    }

    private void bind() {
        mTittleTextView.setText(tittle!=null?tittle:"");
        if (informationMap!=null){
            Place place = PlaceAutocomplete.getPlace(getContext(),informationMap);
            mSubtitleTextView.setText(place.getName());

            latitude = place.getLatLng().latitude;
            longitude = place.getLatLng().longitude;
            mSubtitleTextView.setVisibility(VISIBLE);
            Glide.with(this).load(getUrl(latitude,longitude)).into(mMapImageView);
            mMapImageView.setVisibility(VISIBLE);
            printLocation();
        }

    }

    private String getUrl(double latitude, double longitude) {
        return "https://maps.googleapis.com/maps/api/staticmap?center="+latitude+","+longitude+
                "&zoom=20&size=600x300&markers=color:red%7Clabel:D%7C"+latitude+","+longitude+"&key=AIzaSyDyZ97UWoD9qgdDfB6I9eol0HxUiq-8aBw";
    }


    @Override
    public void onClick(View v) {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(((Activity)getContext()));
            ((Activity)getContext()).startActivityForResult(intent, REQUEST_SELECT_LOCATION);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public void setInformationMap(Intent informationMap) {
        this.informationMap = informationMap;
        bind();
    }

    public void printLocation(){
        Log.e("location ","latitude "+latitude+" longitude"+longitude);
        Log.e("location ",getUrl(latitude,longitude));
    }
}
