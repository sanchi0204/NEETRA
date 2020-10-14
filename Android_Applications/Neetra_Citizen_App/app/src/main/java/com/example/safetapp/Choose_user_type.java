package com.example.safetapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Choose_user_type extends AppCompatActivity {

    Button Local, Tourist;
    String User_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user_type);

        Local = findViewById(R.id.text_local);
        Local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Choose_user_type.this, Sign_up.class);
                User_type = "Local";
                i.putExtra("User_type", User_type);
                startActivity(i);
            }
        });
        Tourist = findViewById(R.id.text_tourist);
        Tourist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Choose_user_type.this, Sign_up.class);
                User_type = "Tourist";
                i.putExtra("User_type", User_type);
                startActivity(i);
            }
        });

    }
}
