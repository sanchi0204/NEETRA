package com.example.safetapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safetapp.Adapter.FirAdapter;
import com.example.safetapp.Model.FirRecord;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fir_token extends AppCompatActivity {
    private List<FirRecord> firList;
    private FirAdapter firAdapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fir_token);


        //code for handling bottom navigation of app

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.nav_fir_token);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.nav_fir_token:
                        return true;

                    case R.id.nav_crime_map:
                        startActivity(new Intent(getApplicationContext(), PolygonMap.class));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        return true;

                    case R.id.nav_report_crime:
                        startActivity(new Intent(getApplicationContext(), Report_Crime.class));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        return true;

                    case R.id.nav_geofence:
                        startActivity(new Intent(getApplicationContext(), GeoFence.class));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        return true;


                    case R.id.nav_settings:
                        startActivity(new Intent(getApplicationContext(), Settings.class));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        return true;


                }
                return false;
            }
        });

        final RecyclerView recyclerView = findViewById(R.id.recycler_fir);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firList = new ArrayList<>();
        DatabaseReference dbFir = FirebaseDatabase.getInstance().getReference().child("FIRdetail");

        dbFir.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    FirRecord firRecord = ds.getValue(FirRecord.class);
                    firList.add(firRecord);
                }
                firAdapter = new FirAdapter(Fir_token.this, firList);
                recyclerView.setAdapter(firAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}

