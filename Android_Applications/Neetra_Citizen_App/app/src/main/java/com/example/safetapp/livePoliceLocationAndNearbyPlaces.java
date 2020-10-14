package com.example.safetapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.safetapp.Model.LocationC;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class livePoliceLocationAndNearbyPlaces extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Context context = this;
    Marker m;
    private static final int REQUEST_CODE_LOCATION_PERMISION = 1;
    private String lat,lon;
    double latitude,longitude;
    DatabaseReference livePoliceLocRef= FirebaseDatabase.getInstance().getReference("Live Police Location");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_police_location_and_nearby_places);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);




    }
    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.getFusedLocationProviderClient(livePoliceLocationAndNearbyPlaces.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(livePoliceLocationAndNearbyPlaces.this)
                                .removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            Location location = locationResult.getLastLocation();
                            Log.d("LocationValue", "onLocationResult:"+location.getLatitude());
                            latitude=location.getLatitude();
                            longitude=location.getLongitude();
                            mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title("You"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude,longitude)));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
                        }
                    }
                }, Looper.getMainLooper());


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {



        mMap = googleMap;
    m=  mMap.addMarker(new MarkerOptions().position(new LatLng(22.78,77.59)).title("Police Officer").icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_police_icon)));
        //police station
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(new LatLng(28.6252181,77.1196005));
        markerOptions1.title("Mayapuri Police Station");
        markerOptions1.icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_police_icon));
        mMap.addMarker(markerOptions1);

        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(new LatLng(28.6388225,77.1018134));
        markerOptions2.title("Tilak Nagar Police Station");
        markerOptions2.icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_police_icon));
        mMap.addMarker(markerOptions2);


        MarkerOptions markerOptions3 = new MarkerOptions();
        markerOptions3.position(new LatLng(28.6514951,77.1200363));
        markerOptions3.title("Rajouri Garden Police Station");
        markerOptions3.icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_police_icon));
        mMap.addMarker(markerOptions3);


        //hospital
        MarkerOptions markerOptions4 = new MarkerOptions();
        markerOptions4.position(new LatLng(28.6280643,77.1122969));
        markerOptions4.title("Deen Dayal Hospital");
        markerOptions4.icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_hospital));
        mMap.addMarker(markerOptions4);

        MarkerOptions markerOptions5 = new MarkerOptions();
        markerOptions5.position(new LatLng( 28.6340289,77.109629));
        markerOptions5.title("Amit Nursing Home");
        markerOptions5.icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_hospital));
        mMap.addMarker(markerOptions5);

        MarkerOptions markerOptions6 = new MarkerOptions();
        markerOptions6.position(new LatLng( 28.6393204,77.1195934));
        markerOptions6.title("MKW Hospital");
        markerOptions6.icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_hospital));
        mMap.addMarker(markerOptions6);

        MarkerOptions markerOptions7 = new MarkerOptions();
        markerOptions7.position(new LatLng( 28.634452,77.098353));
        markerOptions7.title("Tyagi Hospital");
        markerOptions7.icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_hospital));
        mMap.addMarker(markerOptions7);

        MarkerOptions markerOptions8 = new MarkerOptions();
        markerOptions8.position(new LatLng( 28.630396,77.098858));
        markerOptions8.title("Dashmesh Hospital");
        markerOptions8.icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_hospital));
        mMap.addMarker(markerOptions8);

        MarkerOptions markerOptions9 = new MarkerOptions();
        markerOptions9.position(new LatLng( 28.626706,77.110765));
        markerOptions9.title("Lotus Hospital");
        markerOptions9.icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_hospital));
        mMap.addMarker(markerOptions9);

        MarkerOptions markerOptions11 = new MarkerOptions();
        markerOptions11.position(new LatLng( 28.6342693,77.1026662));
        markerOptions11.title("Swasthik Hospital");
        markerOptions11.icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_hospital));
        mMap.addMarker(markerOptions11);


        //ngo
        MarkerOptions markerOptions10 = new MarkerOptions();
        markerOptions10.position(new LatLng( 28.644192,77.107184));
        markerOptions10.title("Rising People Welfare Society");
        markerOptions10.icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ngo));
        mMap.addMarker(markerOptions10);


        MarkerOptions markerOptions12 = new MarkerOptions();
        markerOptions12.position(new LatLng( 28.630524,77.14126));
        markerOptions12.title("Indraprastha Sanjeevni NGO");
        markerOptions12.icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ngo));
        mMap.addMarker(markerOptions12);

        MarkerOptions markerOptions13 = new MarkerOptions();
        markerOptions13.position(new LatLng( 28.6446006,77.1315826));
        markerOptions13.title("MIW Foundation NGO");
        markerOptions13.icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ngo));
        mMap.addMarker(markerOptions13);

        MarkerOptions markerOptions14 = new MarkerOptions();
        markerOptions14.position(new LatLng( 28.637944,77.104574));
        markerOptions14.title("Priyansh Foundation");
        markerOptions14.icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ngo));
        mMap.addMarker(markerOptions14);

        MarkerOptions markerOptions15 = new MarkerOptions();
        markerOptions15.position(new LatLng( 28.6390839,77.0978125));
        markerOptions15.title("Sofia NGO");
        markerOptions15.icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ngo));
        mMap.addMarker(markerOptions15);




        livePoliceLocRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                LocationC locationC=dataSnapshot.getValue(LocationC.class);

                lat=locationC.getLatitude();
                lon=locationC.getLongitude();
                lat = lat.replace("!", ".");
                lon = lon.replace("!", ".");

                double latitude,longitude;

                latitude=Double.parseDouble(lat);
                longitude=Double.parseDouble(lon);
                updateMarker(latitude,longitude);

            }

            private void updateMarker(double latitude,double longitude) {
                m.setPosition(new LatLng(latitude,longitude));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        getCurrentLocation();

    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId)
    {
        Drawable drawable = ContextCompat.getDrawable(context, vectorResId);
        drawable.setBounds(0,0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onBackPressed() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);
        Button dialogButton = dialog.findViewById(R.id.btn_submit);
        final EditText Review = dialog.findViewById(R.id.review);
        final RatingBar ratingBar = dialog.findViewById(R.id.rating_bar);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rating = String.valueOf(ratingBar.getRating());
                String review = Review.getText().toString();
                //addToDb(rating,review);
                //add to db
                Intent intent= new Intent(livePoliceLocationAndNearbyPlaces.this, PolygonMap.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();


    }


}
