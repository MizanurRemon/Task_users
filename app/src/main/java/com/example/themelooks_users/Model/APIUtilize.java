package com.example.themelooks_users.Model;


import com.example.themelooks_users.Model.Login.Login_API;
import com.example.themelooks_users.Model.Products.Products_API;
import com.example.themelooks_users.Model.Profile.Profile_API;
import com.example.themelooks_users.Model.Registration.Register_API;

public class APIUtilize {
    public APIUtilize() {
    }

    public static final String BASE_URL = "http://hoh.fsbbazar.com/";


    public static Login_API loginApi() {
        return Retrofit_client.getClient(BASE_URL).create(Login_API.class);
    }

    public  static Products_API productsApi(){
        return Retrofit_client.getClient(BASE_URL).create(Products_API.class);
    }

    public static Register_API registerApi(){
        return Retrofit_client.getClient(BASE_URL).create(Register_API.class);
    }

    public static Profile_API profileApi(){
        return Retrofit_client.getClient(BASE_URL).create(Profile_API.class);
    }

}
