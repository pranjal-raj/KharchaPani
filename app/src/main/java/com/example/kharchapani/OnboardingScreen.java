package com.example.kharchapani;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

public class OnboardingScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_screen);

        AppCompatButton button = findViewById(R.id.onboard);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();

                fm.beginTransaction().replace(R.id.fcont2, new OnBoardingFeatures(),null).addToBackStack(null).commit();
            }
        });
    }
}