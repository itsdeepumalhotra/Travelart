package com.example.travelart.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller {

     private static final String url = "http://192.168.42.62/api/";
     private static Controller clientObject;
     private static Retrofit retrofit;

    public Controller() {
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    //returns clientObject, if not made thn first it will create it then return it
    public static synchronized Controller getInstance() {
        if (clientObject == null)
            clientObject = new Controller();
        return clientObject;
    }

       public ApiSet getApi(){

        return retrofit.create(ApiSet.class);
        }

}
