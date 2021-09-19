package com.example.themelooks_users.Model.Registration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Register_response {

    @SerializedName("message")
    @Expose
    public String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
