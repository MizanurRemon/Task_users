package com.example.themelooks_users.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.themelooks_users.Model.Products.Products_response;
import com.example.themelooks_users.Model.Profile.Profile_repositories;
import com.example.themelooks_users.Model.Profile.Profile_response;
import com.example.themelooks_users.Model.Registration.Register_repositories;
import com.example.themelooks_users.Model.Registration.Register_response;

public class ProfileViewModel extends AndroidViewModel {
    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Profile_response> getData(String customerID) {

        return Profile_repositories.getInstance().getData(customerID);

    }
}
