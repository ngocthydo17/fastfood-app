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

public class Map1Activity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map1);

        mapView1=findViewById(R.id.mapView1);
        mapView1.onCreate(savedInstanceState);
        mapView1.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng location = new LatLng(10.7812107, 106.9496741);
        googleMap.addMarker(new MarkerOptions().position(location).title("CN Long Thành"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 20));
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        mapView1.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mapView1.onPause();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mapView1.onDestroy();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mapView1.onLowMemory();
//    }
}