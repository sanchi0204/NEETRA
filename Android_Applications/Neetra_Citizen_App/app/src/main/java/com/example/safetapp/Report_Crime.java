package com.example.safetapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Report_Crime extends FragmentActivity implements OnMapReadyCallback,
        LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    private GoogleMap mMap;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    RadioButton current, later;
    Button Submit;
    LatLng latLng, centre;
    String Address, city;
    TextView LOcationAddress;
    ImageView img_pin;
    BottomNavigationView bottomNavigationView;
    ImageButton mic;
    private static final int REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report__crime);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //code for handling bottom navigation of app

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.nav_report_crime);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_report_crime:
                        return true;

                    case R.id.nav_crime_map:
                        startActivity(new Intent(getApplicationContext(), PolygonMap.class));
                        return true;

                    case R.id.nav_fir_token:
                        startActivity(new Intent(getApplicationContext(), Fir_token.class));
                        return true;

                    case R.id.nav_geofence:
                        startActivity(new Intent(getApplicationContext(), GeoFence.class));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        return true;

                    case R.id.nav_settings:
                        startActivity(new Intent(getApplicationContext(), Settings.class));
                        return true;


                }
                return false;
            }
        });

        current = findViewById(R.id.current_crime);
        later = findViewById(R.id.later_crime);
        Submit = findViewById(R.id.submit_button);
        LOcationAddress = findViewById(R.id.location_address);
        img_pin = findViewById(R.id.imgLocationPinUp);
        mic = findViewById(R.id.mic);

        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSpeechInput(view);
            }
        });


        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current.isChecked() && latLng != null) {
                    Intent i = new Intent(Report_Crime.this, CurrentCrime.class);
                    i.putExtra("Address", Address);
                    i.putExtra("City", city);
                    i.putExtra("Latitude", latLng.latitude);
                    i.putExtra("Longitude", latLng.longitude);
                    startActivity(i);
                } else if (later.isChecked()) {
                    Intent i = new Intent(Report_Crime.this, LaterActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(Report_Crime.this, "Please choose a valid option or wait to detect your location", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
                mMap.setBuildingsEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
            mMap.setBuildingsEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {


        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

        mLastLocation = location;
        if (location == null) {
//            mCurrLocationMarker.remove();
            Toast.makeText(this, "Error detecting Location!", Toast.LENGTH_SHORT).show();
        } else {

            if (mCurrLocationMarker == null) {
                //Place current location marker
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Current Position");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                mCurrLocationMarker = mMap.addMarker(markerOptions);

                //move map camera
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            } else {
                mCurrLocationMarker.setPosition(latLng);
            }

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            Double lats = latLng.latitude;
            Double longs = latLng.longitude;

            try {
                List<Address> myAddress = geocoder.getFromLocation(lats, longs, 1);
                Address = myAddress.get(0).getAddressLine(0);
                city = myAddress.get(0).getLocality();
                LOcationAddress.setText(Address + " ," + city);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                centre = mMap.getCameraPosition().target;

                img_pin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mCurrLocationMarker.remove();
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(centre);
                        markerOptions.title("Chosen Position");
                        markerOptions.draggable(true);
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                        mCurrLocationMarker = mMap.addMarker(markerOptions);
                        // img_pin.setVisibility(View.GONE);
                        latLng = mCurrLocationMarker.getPosition();
                        LOcationAddress.setText(getStringAddress(centre.latitude, centre.longitude));
                    }
                });
//
            }
        });


    }

    private String getStringAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> myAddress = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            Address = myAddress.get(0).getAddressLine(0);
            city = myAddress.get(0).getLocality();
            LOcationAddress.setText(Address + " ," + city);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Address + " " + city;
    }


    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Choose Activity");
        startActivityForResult(intent, REQUEST_CODE);


        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> matches = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                assert matches != null;
                if (matches.size() == 0) {
                    Toast.makeText(this, "Please Speak Again", Toast.LENGTH_SHORT).show();
                } else {
                    String mostLikelyThingHeard = matches.get(0);
                    // toUpperCase() used to make string comparison equal
                    if (mostLikelyThingHeard.toUpperCase().equals("SUBMIT")) {
                        startActivity(new Intent(this, CurrentCrime.class));
                    } else if (mostLikelyThingHeard.toUpperCase().equals("CURRENT")) {
                        current.setChecked(true);
                        later.setChecked(false);
                    } else if (mostLikelyThingHeard.toUpperCase().equals("CHOOSE")) {
                        current.setChecked(false);
                        later.setChecked(true);

                    }


                }
            }

            super.onActivityResult(requestCode, resultCode, data);


        }
    }

}