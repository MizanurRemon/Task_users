package com.example.themelooks_users.Model.Login;

import com.google.gson.annotations.SerializedName;

public class Login_response {
    @SerializedName("customerID")
    private String customerID;

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
}
