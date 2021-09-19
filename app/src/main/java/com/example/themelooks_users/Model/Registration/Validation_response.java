package com.example.themelooks_users.Model.Registration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Validation_response {
    @SerializedName("customerID")
    @Expose
    public Integer customerID;

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }
}
