package com.example.researchint;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class SplashActivity2 extends AppCompatActivity {

    private TextView register;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate((savedInstanceState));
        setContentView(R.layout.activity_spashactivity);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(view -> startActivity(new Intent(SplashActivity2.this,Registeruser.class)));
    }
}