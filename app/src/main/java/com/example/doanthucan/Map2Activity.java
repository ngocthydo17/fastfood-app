package com.example.doanthucan;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map2Activity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);

        mapView2 = findViewById(R.id.mapView2);
        mapView2.onCreate(savedInstanceState);
        mapView2.getMapAsync(this);
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng location = new LatLng(10.776053, 106.6666063);
        googleMap.addMarker(new MarkerOptions().position(location).title("CN SVH"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 20));
    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mapView2.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mapView2.onPause();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mapView2.onDestroy();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mapView2.onLowMemory();
//    }
}