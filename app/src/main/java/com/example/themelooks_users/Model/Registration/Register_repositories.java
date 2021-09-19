package com.example.themelooks_users.Model.Registration;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.themelooks_users.Model.APIUtilize;
import com.example.themelooks_users.Model.Login.Login_API;
import com.example.themelooks_users.Model.Login.Login_repositories;
import com.example.themelooks_users.Model.Login.Login_response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register_repositories {

    private static Register_repositories register_repositories;
    Register_API registerApi;
    MutableLiveData<Validation_response> message;
    MutableLiveData<Register_response> message2;

    private Register_repositories() {
        registerApi = APIUtilize.registerApi();
    }

    public synchronized static Register_repositories getInstance() {
        if (register_repositories == null) {
            return new Register_repositories();
        }
        return register_repositories;
    }

    public @NonNull
    MutableLiveData<Validation_response> getValidate( @NonNull String phone) {

        if (message == null) {
            message = new MutableLiveData<>();
        }

        Call<Validation_response> call = registerApi.validateResponse(phone);

        call.enqueue(new Callback<Validation_response>() {
            @Override
            public void onResponse(Call<Validation_response> call, Response<Validation_response> response) {

                if (response.isSuccessful()) {
                    Validation_response validation_response = response.body();
                    message.postValue(validation_response);
                }
            }

            @Override
            public void onFailure(Call<Validation_response> call, Throwable t) {
                Validation_response response = new Validation_response();
                message.postValue(response);

                Log.d("errorxxx", t.getMessage());
            }
        });
        return message;
    }

    public @NonNull
    MutableLiveData<Register_response> getRegister( @NonNull String name, @NonNull String phone,  @NonNull String password) {

        if (message2 == null) {
            message2 = new MutableLiveData<>();
        }

        Call<Register_response> call = registerApi.registerResponse(name, phone, password);

        call.enqueue(new Callback<Register_response>() {
            @Override
            public void onResponse(Call<Register_response> call, Response<Register_response> response) {

                if (response.isSuccessful()) {
                    Register_response register_response = response.body();
                    message2.postValue(register_response);
                }
            }

            @Override
            public void onFailure(Call<Register_response> call, Throwable t) {
                Register_response response = new Register_response();
                message2.postValue(response);

                Log.d("errorxxx", t.getMessage());
            }
        });
        return message2;
    }
}
