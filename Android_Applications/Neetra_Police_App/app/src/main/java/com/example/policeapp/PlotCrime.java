package com.example.policeapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlotCrime extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int COLOR_BLACK_ARGB = 0xff120000;
    private static final int POLYLINE_STROKE_WIDTH_PX = 5;
    int iconId;
    int size;
    MarkerData markerData;
    ArrayList<MarkerData> markersArray = new ArrayList<>();
    DatabaseReference reported_crime = FirebaseDatabase.getInstance().getReference().child("Reported Crime");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot_crime);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        markerData=new MarkerData();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        reported_crime.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                size = (int) dataSnapshot.getChildrenCount();


                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        MarkerData data=new MarkerData();
                        String crime_type = ds.child("crimeType").getValue(String.class);
                        String latt = ds.child("lat").getValue(String.class);
                        String lngg = ds.child("lon").getValue(String.class);


                        assert latt != null;
                        assert lngg != null;

                        double lati = Double.parseDouble(latt);
                        double longi = Double.parseDouble(lngg);

                        data.setCrime(crime_type);
                        data.setLat(lati);
                        data.setLon(longi);

                        markersArray.add(data);



//                        mMap.addMarker(createMarker(markersArray.get(i).getLat(),
//                                markersArray.get(i).getLon(),
//                                markersArray.get(i).getCrime()));
//
//                        Polyline polyline=mMap.addPolyline(new PolylineOptions()
//                                .clickable(true)
//                                .add(new LatLng(markersArray.get(i).getLat(),markersArray.get(i).getLon())
//                                ,new LatLng(markersArray.get(i).getLat(),markersArray.get(i).getLon())));
//
//                        Toast.makeText(PlotCrime.this, "test", Toast.LENGTH_SHORT).show();
                    }
                //Toast.makeText(PlotCrime.this, "test", Toast.LENGTH_SHORT).show();
                addMarkersAndPolylines();
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.7041,77.1025),10));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void addMarkersAndPolylines() {
        //MarkerData data=new MarkerData();
        for (int i=0;i<size;i++)
        {
            mMap.addMarker(createMarker(markersArray.get(i).getLat(),
                                        markersArray.get(i).getLon(),
                                        markersArray.get(i).getCrime()));
            if(i!=size-1) {
                Polyline polyline = mMap.addPolyline(new PolylineOptions()
                        .clickable(true)
                        .add(new LatLng(markersArray.get(i).getLat(), markersArray.get(i).getLon())
                                , new LatLng(markersArray.get(i + 1).getLat(), markersArray.get(i + 1).getLon())));
                polyline.setWidth(POLYLINE_STROKE_WIDTH_PX);
                polyline.setColor(COLOR_BLACK_ARGB);
                polyline.setJointType(JointType.ROUND);
            }
        }

    }


    protected MarkerOptions createMarker(double latitude, double longitude, String crime) {
//
//        MarkerOptions mark_op = new MarkerOptions();
//
//                                assert crime != null;
//                        if (crime.contentEquals("Chain Snatching")) {
////                             iconId = R.drawable.place_chain;
//                            mark_op.position(new LatLng(latitude, longitude))
//                                    .title(crime)
//                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//                        }
//
//                        if (crime.contentEquals("Vandalism")) {
//                          //  iconId = R.drawable.place_vandalism;
//                            mark_op.position(new LatLng(latitude, longitude))
//                                    .title(crime)
//                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//                        }
//
//                        if (crime.contentEquals("Pick Pocketing")) {
//                            //iconId = R.drawable.place_pocket;
//                            mark_op.position(new LatLng(latitude, longitude))
//                                    .title(crime)
//                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//                        }
//                        if (crime.contentEquals("Eve Teasing")) {
//                            //iconId = R.drawable.place_eve;
//                            mark_op.position(new LatLng(latitude, longitude))
//                                    .title(crime)
//                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
//                        }

        return new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title(crime)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

       // return  mark_op;
    }
}
