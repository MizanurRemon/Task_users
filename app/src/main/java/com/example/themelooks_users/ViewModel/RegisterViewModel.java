package com.example.themelooks_users.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.themelooks_users.Model.Products.Products_repositories;
import com.example.themelooks_users.Model.Products.Products_response;
import com.example.themelooks_users.Model.Registration.Register_repositories;
import com.example.themelooks_users.Model.Registration.Register_response;
import com.example.themelooks_users.Model.Registration.Validation_response;

import java.util.List;

public class RegisterViewModel extends AndroidViewModel {
    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Validation_response> getValidate(String phone) {

        return Register_repositories.getInstance().getValidate(phone);

    }

    public LiveData<Register_response> getRegister( String name,  String phone,   String password) {

        return Register_repositories.getInstance().getRegister(name, phone, password);

    }
}
