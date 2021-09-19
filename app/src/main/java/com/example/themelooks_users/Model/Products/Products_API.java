package com.example.themelooks_users.Model.Products;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Products_API {

    @GET("/products")
    Call<List<Products_response>> getProducts();
}
