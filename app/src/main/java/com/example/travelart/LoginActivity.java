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

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText username , password;
    private Button forgetBtn , login, gotoRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         username = findViewById(R.id.username);
         password = findViewById(R.id.password);
         forgetBtn = findViewById(R.id.forgetPassword);
         login = findViewById(R.id.login);
         gotoRegister = findViewById(R.id.registration);


         checkExistingUser();
         
         login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String usrname = username.getText().toString();
                 String pswd = password.getText().toString();
                 processLogin(usrname,pswd);
             }
         });

         //TODO forgetBtn.setOnClickListener();

         gotoRegister.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 gotoRegisterActivity();
             }
         });
    }

    private void checkExistingUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("credentials",MODE_PRIVATE);
        if (sharedPreferences.contains("username")){
            startActivity(new Intent(this,HomeActivity.class));
        }
        else {
            TastyToast.makeText(getApplicationContext(),"Hey, Login Please..!",TastyToast.LENGTH_SHORT, TastyToast.WARNING);
        }
    }

    private void processLogin(String username,String password) {
        Call<ResponseModel> call = Controller
                .getInstance()
                .getApi()
                .verifyUser(username,password);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                ResponseModel responseModel = response.body();
                String output = responseModel.getStatus();
                if (output.equals("error")){
                    TastyToast.makeText(getApplicationContext(),"Enter Valid Details..!",TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);

                }
                if (output.equals("success")){
                    SharedPreferences sharedPreferences = getSharedPreferences("credentials",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username",username);
                    editor.putString("password",password);
                    editor.apply();
                    TastyToast.makeText(getApplicationContext(),"Logged In Successfully..!",TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                TastyToast.makeText(getApplicationContext(),"Sorry, but.. Something's Wrong..!!",TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            }
        });
    }

    private void gotoRegisterActivity() {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
        TastyToast.makeText(getApplicationContext(),"Details Please..!",TastyToast.LENGTH_SHORT, TastyToast.DEFAULT);

        finish();
    }


}