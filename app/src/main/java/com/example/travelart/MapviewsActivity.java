package com.example.travelart;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.travelart.databinding.ActivityMapviewsBinding;
import com.example.travelart.utils.Venue;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapviewsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapviewsBinding binding;
    ArrayList<Venue> venueArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapviews);


        // try to get intent
        Intent intent = getIntent();
        venueArrayList = (ArrayList<Venue>) intent.getSerializableExtra("VENUE_LIST");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        //////////////// adding marker for every venue ///////////////

        for (int i = 0; i < venueArrayList.size(); i++) {
            // getting data of each venue from array list
            double latitude = venueArrayList.get(i).getLatitude();
            double longitude = venueArrayList.get(i).getLongitude();
            String name = venueArrayList.get(i).getName();
            // now plotting it on map
            LatLng venuePoint = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(venuePoint).title(i + 1 + "." + name));
            // moving camera //
            if (i == 0)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(venuePoint, 11));
        }
    }
}
