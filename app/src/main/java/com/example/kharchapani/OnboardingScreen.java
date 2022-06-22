package com.example.kharchapani;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class OnboardingScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_screen);

        AppCompatButton button = findViewById(R.id.onboard);

        SharedPreferences sharedPreferences = getSharedPreferences("one_time_details", Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("first_time?", false).commit();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();

                fm.beginTransaction().replace(R.id.fcont2, new OnBoardingFeatures(),null).addToBackStack(null).commit();
            }
        });
    }
}