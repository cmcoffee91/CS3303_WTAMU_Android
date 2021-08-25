package com.mapapp.cc916647.mapapp;
/*
    App to display the map using the Google Maps.
    When you start the new project in AndroidStudio,
    remember to choose the GoogleMapsActivity instead of the
    usual BlankActivity.
    A GoogleMapsActivity requires a Google Maps API key, using a browser go to
    https://developers.google.com/maps/documentation/android-api/signup
    click on 'GET A KEY' button and enter your google account user-id and password
    Then again press 'GET A KEY', enter the app name for which the we want a key and click next
    The will generate an API which unique for using with the project name provided
    Copy the key which begins with AIza and 40 chars long, paste into the meta-data element in
    AndroidManifest.xml file.
    The map will get displayed as a fragment which is the approach we will take in this app.

 */
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button mapButton, satelliteButton, hybridButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

       mapButton = (Button) findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });


        satelliteButton = (Button) findViewById(R.id.satelliteButton);
        satelliteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

            }
        });

        hybridButton = (Button) findViewById(R.id.hybridButton);
        hybridButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            }
        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker at the Empire State Building and move the camera
        LatLng newYork = new LatLng(40.7485413,-73.98575770000002);
        // how to zoom in a locationhttps://stackoverflow.com/questions/14226453/google-maps-api-v2-how-to-make-markers-clickable
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(newYork)
                .zoom(14.0f)
                .tilt(25)
                .build();

        mMap.moveCamera(CameraUpdateFactory.newLatLng(newYork));
        //mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.addMarker(new MarkerOptions().position(newYork).title("New York"));

        //enable the zoom control
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mapButton.setEnabled(true);
        satelliteButton.setEnabled(true);
        hybridButton.setEnabled(true);

        //to show the directions on the map to from A to B,
        //you will need the latitude, longitude coordinates of A and B
        //empState = 40.7485413, -73.98575770000002
        //timeSquare = 40.758895,-73.98513100000002
        String from = "40.7485413, -73.98575770000002";
        String to = "40.758895,-73.98513100000002";
        String geoUriString = "http://maps.google.com/maps?saddr=" + from + "&daddr=" + to;
        Intent mapCall = new Intent (Intent.ACTION_VIEW, Uri.parse(geoUriString));
        //startActivity(mapCall);
    }
}
