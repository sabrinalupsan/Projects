package com.example.seminar_4.Wishlist;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.seminar_4.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapView;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        intent = getIntent();


    }

    /** once available.
     * This callback is tr
     * Manipulates the mapiggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

       mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String str = marker.getTitle();
                intent.putExtra("wishname", str);
                setResult(10, intent);
                finish();
                return true;
            }
        });
        LatLng sydney = new LatLng(-34, 151);
        LatLng borabora = new LatLng( -16, -151);
        LatLng madagascar = new LatLng(-19, 46);
        LatLng rome = new LatLng(41.890251, 12.492373);
        LatLng mexicocity = new LatLng(19.432608, -99.133209);
        LatLng tokyo = new LatLng(35.652832, 139.839478);
        LatLng moscow = new LatLng(55.751244, 37.618423);
        LatLng karachi = new LatLng(24.860966, 66.990501);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Sydney"));
        mMap.addMarker(new MarkerOptions().position(borabora).title("Bora Bora"));
        mMap.addMarker(new MarkerOptions().position(madagascar).title("Madagascar"));
        mMap.addMarker(new MarkerOptions().position(rome).title("Rome"));
        mMap.addMarker(new MarkerOptions().position(mexicocity).title("Mexico City"));
        mMap.addMarker(new MarkerOptions().position(tokyo).title("Tokyo"));
        mMap.addMarker(new MarkerOptions().position(moscow).title("Moscow"));
        mMap.addMarker(new MarkerOptions().position(karachi).title("Karachi"));


        mMap.moveCamera(CameraUpdateFactory.newLatLng(rome));
    }
}