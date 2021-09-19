package com.example.themelooks_users.Model.Registration;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Register_API {

    @GET("/validation")
    Call<Validation_response> validateResponse (@Query("phone") String phone);

    @FormUrlEncoded
    @POST("/customer_registration")
    Call<Register_response> registerResponse (@Field("customer_name") String name,
                                              @Field("phone") String phone,
                                              @Field("password") String password);
}
