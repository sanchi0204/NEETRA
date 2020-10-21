package com.example.policeapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.policeapp.Model.CrimeCounter;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Home extends AppCompatActivity {

    final Context context = this;
    ProgressDialog progressDialog;
    TextView chain, vandalism, eve_teasing, pick;
    DatabaseReference policeLocRef= FirebaseDatabase.getInstance().getReference();
    DatabaseReference crimeCounter1 = FirebaseDatabase.getInstance().getReference().child("CrimeCounter");
    CrimeCounter crimeCounter;
    String lati, loni;
    int[] colorClassArray=new int[]{Color.RED,Color.BLUE,Color.GREEN,R.color.colorPrimary_w};
    boolean gotLocation = false;
    private static final int REQUEST_CODE_LOCATION_PERMISION = 1;


    private int pickPocketing, chainSnatching, EveTeasing, Vandalism;
     EditText broad_cast_msg;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (gotLocation) {
            final String finalLati = lati;
            final String finalLoni = loni;
            crimeCounter1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if ((dataSnapshot.child("Data").child(finalLati + " " + finalLoni)).exists()) {
                        crimeCounter = dataSnapshot.child("Data").child(finalLati + " " + finalLoni).getValue(CrimeCounter.class);

                        Vandalism = crimeCounter.getVandalism();
                        EveTeasing = crimeCounter.getEveTeasing();
                        pickPocketing = crimeCounter.getPickPocketing();
                        chainSnatching = crimeCounter.getChainSnatching();

                        chain.setText(String.valueOf(chainSnatching));
                        eve_teasing.setText(String.valueOf(EveTeasing));
                        pick.setText(String.valueOf(pickPocketing));
                        vandalism.setText(String.valueOf(Vandalism));
                    } else {
                        Toast.makeText(Home.this, "Data Doesn't Exist in your area", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        chain = findViewById(R.id.num_chain_snatching);
        vandalism = findViewById(R.id.num_vandalism);
        eve_teasing = findViewById(R.id.num_eve_teasing);
        pick = findViewById(R.id.num_pick_pocket);
        crimeCounter = new CrimeCounter();
        progressDialog=new ProgressDialog(Home.this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISION);
        } else {
            progressDialog.setTitle("Please Wait");
            progressDialog.setMessage("Getting Location");
            progressDialog.show();
            getCurrentLocation();
        }


    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        LocationServices.getFusedLocationProviderClient(Home.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(Home.this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            Location location = locationResult.getLastLocation();
                            gotLocation = true;
                            Log.d("LocationValue", "onLocationResult:"+location.getLatitude());

                            updateLocationToDB(location);
                            updateValue(location);
                        }
                    }
                }, Looper.getMainLooper());


    }

    private void updateLocationToDB(Location location) {
        double latitude,longitude;
        latitude = location.getLatitude();
       // final double roundOffLatitude = (double) Math.round(latitude * 100) / 100;

        longitude = location.getLongitude();
       // final double roundOffLongitude = (double) Math.round(longitude * 100) / 100;

        lati = String.valueOf(latitude);
        loni = String.valueOf(longitude);
        lati = lati.replace(".", "!");
        loni = loni.replace(".", "!");

        HashMap<String,Object>policeLoc=new HashMap<>();
        policeLoc.put("latitude",lati);
        policeLoc.put("longitude",loni);

        policeLocRef.child("Live Police Location").updateChildren(policeLoc);


    }

    public void updateValue(Location location) {
        double latitude;
        double longitude;


        latitude = location.getLatitude();
        final double roundOffLatitude = (double) Math.round(latitude * 100) / 100;

        longitude = location.getLongitude();
        final double roundOffLongitude = (double) Math.round(longitude * 100) / 100;

        // Toast.makeText(this, roundOffLatitude+" "+roundOffLongitude, Toast.LENGTH_SHORT).show();

        lati = String.valueOf(roundOffLatitude);
        loni = String.valueOf(roundOffLongitude);
        lati = lati.replace(".", "!");
        loni = loni.replace(".", "!");

        final String finalLati = lati;
        final String finalLoni = loni;
        crimeCounter1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ((dataSnapshot.child("Data").child(finalLati + " " + finalLoni)).exists()) {
                    crimeCounter = dataSnapshot.child("Data").child(finalLati + " " + finalLoni).getValue(CrimeCounter.class);

                    Vandalism = crimeCounter.getVandalism();
                    EveTeasing = crimeCounter.getEveTeasing();
                    pickPocketing = crimeCounter.getPickPocketing();
                    chainSnatching = crimeCounter.getChainSnatching();
                    progressDialog.dismiss();
                    progressDialog.setMessage("Loading Chart");
                    progressDialog.show();
                    updatePieChart();

                    chain.setText(String.valueOf(chainSnatching));
                    eve_teasing.setText(String.valueOf(EveTeasing));
                    pick.setText(String.valueOf(pickPocketing));
                    vandalism.setText(String.valueOf(Vandalism));

                } else {
                    Toast.makeText(Home.this, "Data Dosen't Exist in your area", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();

            }
        });


    }

    private void updatePieChart() {
        PieChart pieChart = findViewById(R.id.pieChart);
        ArrayList<PieEntry>crimeCount= new ArrayList<>();
        crimeCount.add(new PieEntry(chainSnatching,"Chain Snatching"));
        crimeCount.add(new PieEntry(pickPocketing,"Pick Pocketing"));
        crimeCount.add(new PieEntry(Vandalism,"Vandalism"));
        crimeCount.add(new PieEntry(EveTeasing,"Eve Teasing"));

        PieDataSet pieDataSet=new PieDataSet(crimeCount,"Crime Count");
        pieDataSet.setColors(colorClassArray);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);
        PieData pieData=new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("CrimeCount");
        progressDialog.dismiss();
        pieChart.invalidate();


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notif_dash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.item1:
                Intent i = new Intent(Home.this, Reports.class);
                startActivity(i);
                return true;

            case R.id.item2:
                Intent intent = new Intent(Home.this, PlotCrime.class);
                startActivity(intent);
                return true;

            case R.id.item3:
                openDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openDialog() {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog_broadcast);
        Button dialogButton = dialog.findViewById(R.id.btn_broadcast);
        ImageButton mic = dialog.findViewById(R.id.mic);
        broad_cast_msg = dialog.findViewById(R.id.broadcast_msg);

        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getSpeechInput(view);
            }
        });
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = broad_cast_msg.getText().toString();   //process this
                openDialogSuccess();
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                assert result != null;
                broad_cast_msg.setText(result.get(0));
            }
        }
    }

    public void openDialogSuccess() {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.btn_broadcast_sent);

        dialog.show();
    }

}