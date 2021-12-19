package com.example.travelart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sdsmdg.tastytoast.TastyToast;

public class HomeActivity extends AppCompatActivity {

    Button logout,map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        logout = findViewById(R.id.logout);
        map = findViewById(R.id.map);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoMapActivity();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutProcess();
            }
        });
        
    }

    private void gotoMapActivity() {
        startActivity(new Intent(this, MapviewsActivity.class));
    }


    private void logoutProcess() {
        SharedPreferences sharedPreferences = getSharedPreferences("credentials",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.remove("password");
        editor.apply();
        TastyToast.makeText(getApplicationContext(),"Logged Out..!",TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }
}