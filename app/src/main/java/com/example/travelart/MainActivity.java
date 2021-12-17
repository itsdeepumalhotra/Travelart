package com.example.travelart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gotoLogin();
        gotoSignup();

    }

    private void gotoSignup() {

        
    }

    private void gotoLogin() {

        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);

    }
}