package com.example.safetapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LaterActivity extends AppCompatActivity {

    Button Later_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_later);

        Later_next = findViewById(R.id.later_crime_next);
        Later_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LaterActivity.this, Later_2.class);
                startActivity(i);
            }
        });

    }
}
