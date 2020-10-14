package com.example.safetapp;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CurrentCrime extends AppCompatActivity {

    Button next_page;
    TextInputEditText coordiantes, place, city, time;
    double Coordiantes_lat;
    double Coordiantes_long;
    String Place;
    String City;
    String Time;
    View viewBack;
    private static final int REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_crime);

        next_page = findViewById(R.id.current_crim_next);
        next_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CurrentCrime.this, Crime_current_2.class);
                i.putExtra("Lat", Coordiantes_lat);
                i.putExtra("Lon", Coordiantes_long);
                startActivity(i);
            }
        });

        Coordiantes_lat = getIntent().getDoubleExtra("Latitude", 00.00);
        Coordiantes_long = getIntent().getDoubleExtra("Longitude", 00.00);
        Place = getIntent().getStringExtra("Address");
        City = getIntent().getStringExtra("City");


        coordiantes = findViewById(R.id.gps_coordinates_edit_text);
        place = findViewById(R.id.place_edit_text);
        city = findViewById(R.id.city_edit_text);
        time = findViewById(R.id.time_edit_text);

        coordiantes.setText(Coordiantes_lat + " " + Coordiantes_long);
        place.setText(Place);
        city.setText(City);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        Time = sdf.format(new Date());
        time.setText(Time);

        viewBack= findViewById(R.id.view_back);
        viewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSpeechInput(view);
            }
        });

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
                    if (mostLikelyThingHeard.toUpperCase().equals("NEXT")) {
                        startActivity(new Intent(this, Crime_current_2.class));
                    }

                }
            }

            super.onActivityResult(requestCode, resultCode, data);


        }
    }
}
