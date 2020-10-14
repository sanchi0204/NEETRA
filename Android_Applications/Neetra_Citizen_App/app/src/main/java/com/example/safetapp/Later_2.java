package com.example.safetapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Later_2 extends AppCompatActivity {

    Button next_later_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_later_2);

        next_later_2 = findViewById(R.id.later_crime_next);
        next_later_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Later_2.this, Later_3.class);
                startActivity(i);
            }
        });

    }


}
