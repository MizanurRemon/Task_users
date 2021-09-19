package com.example.themelooks_users.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.themelooks_users.Model.Products.Products_repositories;
import com.example.themelooks_users.Model.Products.Products_response;

import java.util.List;

public class ProductsViewModel extends AndroidViewModel {
    public ProductsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Products_response>> getProduct() {

        return Products_repositories.getInstance().getProducts();

    }
}
