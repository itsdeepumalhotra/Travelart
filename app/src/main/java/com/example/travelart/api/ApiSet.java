package com.example.travelart.api;

import com.example.travelart.api.responseModel.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiSet {
    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseModel> verifyUser(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseModel> insertUser(
            @Field("username") String username,
            @Field("password") String password
    );
}
