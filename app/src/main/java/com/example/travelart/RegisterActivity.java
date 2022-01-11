package com.example.travelart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.travelart.api.Controller;
import com.example.travelart.api.responseModel.ResponseModel;
import com.google.android.material.textfield.TextInputEditText;
import com.sdsmdg.tastytoast.TastyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
private TextInputEditText username, password, confirmpassword;
private Button registerBtn , gotoLoginBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.Rusername);
        password = findViewById(R.id.Rpassword);
        confirmpassword = findViewById(R.id.RconfirmPassword);
        registerBtn = findViewById(R.id.signUpBtn);
        gotoLoginBtn = findViewById(R.id.alreadyRegBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = username.getText().toString();
                String pswd = password.getText().toString();
                String cnfmPswd = confirmpassword.getText().toString();
                if (pswd.equals(cnfmPswd)){
                    registerProcess(name,pswd);
                }
                else {
                    TastyToast.makeText(getApplicationContext(),"Password do not match\nTry Again",TastyToast.LENGTH_LONG,TastyToast.WARNING);
                }
            }
        });

        gotoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoLoginActivity();
            }
        });

    }

    private void registerProcess(String name, String pswd) {
        Call<ResponseModel> call = Controller
                .getInstance()
                .getApi()
                .insertUser(name,pswd);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                ResponseModel responseModel = response.body();
                String output = responseModel.getStatus();
                if (output.equals("error")){
                    TastyToast.makeText(getApplicationContext(),"Unable to register..!",TastyToast.LENGTH_SHORT, TastyToast.ERROR);

                }
                if (output.equals("success")){
                    TastyToast.makeText(getApplicationContext(),"Registered Successfully..!",TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                TastyToast.makeText(getApplicationContext(),"Sorry, but.. Something's Wrong..!",TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);

            }
        });


    }

    private void gotoLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}