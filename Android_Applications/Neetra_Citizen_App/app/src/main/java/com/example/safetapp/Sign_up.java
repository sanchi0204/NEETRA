package com.example.safetapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.safetapp.Model.CrimeCounter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Sign_up extends AppCompatActivity implements LocationListener {

    MaterialButton SignUp;
    String User_type;
    TextView SignIn_;
    TextInputLayout user_id;
    TextInputEditText user_id_edit;

    String lat, loni;
    protected LocationManager locationManager;
    private int molestation, rape, kidnaping, trafficking, pos, Max;
    int colour;
    int[] maxFinder = new int[4];
    private CrimeCounter crimeCounter = new CrimeCounter();
    private DatabaseReference crimeDataRef = FirebaseDatabase.getInstance().getReference().child("CrimeCounter");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        user_id = findViewById(R.id.aadhar_layout);
        user_id_edit = findViewById(R.id.aadhar_edit_text);

        SignUp = findViewById(R.id.sign_up_btn);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAdhaar();
            }
        });
        SignIn_ = findViewById(R.id.signIn_text2);

        SignIn_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Sign_up.this, SignIn.class);
                startActivity(i);

            }
        });

        User_type = getIntent().getStringExtra("User_type");

        if (User_type.equals("Tourist")) {
            user_id.setHint("PassportId");
//            user_id_edit.setHint("PassportId");
        }


        Intent serviceIntent = new Intent(this, BackgroundWorker.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);              //TODO: add backwards compatibility
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }

    private void checkAdhaar() {
        if ((user_id_edit.getText().length() == 12)) {
            Intent i = new Intent(Sign_up.this, SignIn.class);
            startActivity(i);
        } else {
            Toast.makeText(this, "Enter valid Adhaar Number.", Toast.LENGTH_SHORT).show();
        }
    }

    private void GiveColourValue() {
        while ((lat.length() > 7)) {
            lat = lat.substring(0, lat.length() - 1);
        }
        while (loni.length() > 7) {
            loni = loni.substring(0, loni.length() - 1);
        }

        loni = loni.replace(".", "!");
        lat = lat.replace(".", "!");

//        lat=lat.substring(0,lat.length()-11);
//        loni=loni.substring(0,loni.length()-10);
        //Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show();
        final String finalLat = lat;
        final String finalLon = loni;

        crimeDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("Data").child(finalLat + " " + finalLon).exists()) {

                    crimeCounter = dataSnapshot.child("Data").child(finalLat + " " + finalLon).getValue(CrimeCounter.class);
                    Toast.makeText(Sign_up.this, "Test", Toast.LENGTH_SHORT).show();
                    molestation = crimeCounter.getChainSnatching();
                    rape = crimeCounter.getPickPocketing();
                    trafficking = crimeCounter.getVandalism();
                    kidnaping = crimeCounter.getEveTeasing();
                    maxFinder[0] = molestation;
                    maxFinder[1] = rape;
                    maxFinder[2] = trafficking;
                    maxFinder[3] = kidnaping;
                    Max = maxFinder[0];
                    pos = 0;
                    for (int i = 1; i < maxFinder.length; i++) {
                        if (maxFinder[i] > Max) {
                            Max = maxFinder[i];
                            pos = i;
                        }
                    }
                    if (pos == 1) {
                        colour = 0x44ff0000;//red;
                    } else if (pos == 0) {
                        colour = 0x4400ecff;//blue
                    } else if (pos == 2) {
                        colour = 0x4434e10d;//green
                    } else if (pos == 3) {
                        colour = 0x44a30fe5;//purple
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onLocationChanged(Location location) {
        lat = String.valueOf(location.getLatitude());
        loni = String.valueOf(location.getLongitude());
        GiveColourValue();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
