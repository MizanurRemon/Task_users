package com.example.themelooks_users.Model.Products;

import com.google.gson.annotations.SerializedName;

public class Price_response {
    @SerializedName("price")
    public String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
