package com.example.policeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

public class Priority extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;

    String[] departments = {

           "Select Department", "RTO", "Fire Station", "Hospital", "Cyber Crime"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priority);


        spinner = findViewById(R.id.dept_spinner);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> ad = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                departments);

        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        spinner.setAdapter(ad);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Toast.makeText(this, "Selected: " + departments[i], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    @Override
    public void onBackPressed() {
        final Dialog dialog = new Dialog(Priority.this);
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
                Intent intent= new Intent(Priority.this, Home.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();


    }

}
