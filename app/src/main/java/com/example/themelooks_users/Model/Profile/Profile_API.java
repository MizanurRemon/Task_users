package com.example.themelooks_users.Model.Profile;

import com.example.themelooks_users.Model.Login.Login_response;
import com.example.themelooks_users.Model.Products.Products_response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Profile_API {

    @GET("/profile")
    Call<Profile_response> getResponse(@Query("customerID") String customerID);
}
