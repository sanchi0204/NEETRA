package com.example.policeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class SignIn extends AppCompatActivity {
    MaterialButton SignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        SignIn = findViewById(R.id.sign_in_btn);
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignIn.this, Home.class);
                startActivity(i);
            }
        });
    }
}
