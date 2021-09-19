package com.example.themelooks_users.Model.Products;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;


import com.example.themelooks_users.Model.APIUtilize;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Products_repositories {

    private static Products_repositories products_repositories;
    Products_API productsApi;
    MutableLiveData<List<Products_response>> message;

    private Products_repositories() {
        productsApi = APIUtilize.productsApi();
    }

    public synchronized static Products_repositories getInstance() {
        if (products_repositories == null) {
            return new Products_repositories();
        }
        return products_repositories;
    }

    public @NonNull
    MutableLiveData<List<Products_response>> getProducts() {

        if (message == null) {
            message = new MutableLiveData<>();
        }

        Call<List<Products_response>> call = productsApi.getProducts();

        call.enqueue(new Callback<List<Products_response>>() {
            @Override
            public void onResponse(Call<List<Products_response>> call, Response<List<Products_response>> response) {

                if (response.isSuccessful()) {
                    List<Products_response> products_responses = response.body();
                    message.postValue(products_responses);
                }
            }

            @Override
            public void onFailure(Call<List<Products_response>> call, Throwable t) {
                List<Products_response> response = new ArrayList<>();
                message.postValue(response);

            }
        });
        return message;
    }
}
