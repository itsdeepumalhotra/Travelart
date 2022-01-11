package com.example.travelart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handler = new Handler();
        handler.postDelayed(this::gotoLogin,500);


    }


    private void gotoLogin() {
        Intent intent = new Intent(this, LoginActivity.class); // to be changed later from home to login TODO
        startActivity(intent);
        finish();
    }


}