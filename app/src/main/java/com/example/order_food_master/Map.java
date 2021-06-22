package com.example.order_food_master;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.OnMapReadyCallback;

public class Map extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        (Map.this).getSupportActionBar().setTitle("Vị trí");
        MapFragment mapFragment=(MapFragment) getFragmentManager().findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(13.758327363934741, 109.21870845982625);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,10));
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Nhà hàng Nguyễn Tính"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}