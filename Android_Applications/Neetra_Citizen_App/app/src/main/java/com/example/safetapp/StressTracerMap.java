package com.example.safetapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class StressTracerMap extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap googleMap;
    LatLng senderBounds;
    String senderUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stress_tracer_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMapAlarm);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        Intent intent = this.getIntent();
        senderBounds = new LatLng(intent.getDoubleExtra("latitude", 45.5), intent.getDoubleExtra("longitude", 56.3));
        senderUsername = intent.getStringExtra("username");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        updateCamera(senderBounds);
    }

    private void updateCamera(LatLng senderBounds) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(senderBounds, 14f));
        googleMap.addMarker(new MarkerOptions().position(senderBounds).title(senderUsername + " is in danger!"));                //TODO:change marker icon
    }
}
