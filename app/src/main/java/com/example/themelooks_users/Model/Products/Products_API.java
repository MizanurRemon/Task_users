package com.example.themelooks_users.Model.Products;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Products_API {

    @GET("/products")
    Call<List<Products_response>> getProducts();

    @FormUrlEncoded
    @POST("/products_details")
    Call<Products_response> getProductDetails(@Field("productsID") String productsID);

    @FormUrlEncoded
    @POST("/price")
    Call<Price_response> priceResponse(@Field("color") String color,
                                       @Field("size") String size);

    //specific_products_variant_details
    @FormUrlEncoded
    @POST("/specific_products_variant_details")
    Call<Price_range_response> getPriceRange(@Field("productsID") String productsID,
                                       @Field("color") String color,
                                       @Field("size") String size);
}
