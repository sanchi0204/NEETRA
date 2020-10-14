package com.example.safetapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class Settings extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    Button change_lang, stop_stress, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoadLocale();
        setContentView(R.layout.activity_settings);


        //code for handling bottom navigation of app

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.nav_settings);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_settings:
                        return true;

                    case R.id.nav_report_crime:
                        startActivity(new Intent(getApplicationContext(), Report_Crime.class));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        return true;

                    case R.id.nav_fir_token:
                        startActivity(new Intent(getApplicationContext(), Fir_token.class));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        return true;

                    case R.id.nav_geofence:
                        startActivity(new Intent(getApplicationContext(), GeoFence.class));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        return true;

                    case R.id.nav_crime_map:
                        startActivity(new Intent(getApplicationContext(), PolygonMap.class));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        return true;

                }
                return false;
            }
        });


        Intent serviceIntent = new Intent(this, BackgroundWorker.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);              //TODO: add backwards compatibility
        }


        change_lang = findViewById(R.id.btn_change_lang);
        change_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Chooser();
            }
        });

        stop_stress = findViewById(R.id.btn_stop_stress_signal);
        stop_stress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new FirestoreHandler(Settings.this).stopStressSignal();
                Toast.makeText(Settings.this, "Stress Signal Stopped", Toast.LENGTH_SHORT).show();


            }
        });


        logout = findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Settings.this, Splash_w.class);
                Toast.makeText(Settings.this, "Logging out", Toast.LENGTH_SHORT).show();
                startActivity(i);


            }
        });


    }


    private void Chooser() {

        final String[] listItems = {getString(R.string.english),
                getString(R.string.hindi)};

        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
        builder.setTitle(getString(R.string.choose));
        builder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        setLocale("en");
                        recreate();
                        break;

                    case 1:
                        setLocale("hi");
                        recreate();
                        break;

                }
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void setLocale(String lang) {

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("Language", MODE_PRIVATE).edit();
        editor.putString("My_lang", lang);
        editor.apply();

    }

    public void LoadLocale() {
        SharedPreferences preferences = getSharedPreferences("Language", Activity.MODE_PRIVATE);
        final String language = preferences.getString("My_lang", "");
        setLocale(language);
    }

}
