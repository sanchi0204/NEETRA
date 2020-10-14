package com.example.safetapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import pl.droidsonroids.gif.GifImageView;

public class Splash_w extends AppCompatActivity {

    Button Get_started;
    TextView app_name, tag;
    GifImageView img;
    Animation slide_up_1, slide_up_2, slide_up_3, slide_up_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_w);

        app_name = findViewById(R.id.app_name);
        tag = findViewById(R.id.tag_line);
        img = findViewById(R.id.logo_img);
        Get_started = findViewById(R.id.btn_start);


        slide_up_1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.atg);
        slide_up_2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.atgtwo);
        slide_up_3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.atgthree);
        slide_up_4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.atgfour);



        app_name.startAnimation(slide_up_1);
        tag.startAnimation(slide_up_2);
        img.startAnimation(slide_up_3);
        Get_started.startAnimation(slide_up_4);


        Get_started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Splash_w.this, Onboard.class);
                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                startActivity(i);
            }
        });


    }
}
