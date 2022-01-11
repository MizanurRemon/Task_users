package com.example.themelooks_users.View.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.themelooks_users.Model.APIUtilize;
import com.example.themelooks_users.Model.Products.Price_range_response;
import com.example.themelooks_users.Model.Products.Price_response;
import com.example.themelooks_users.Model.Products.Products_API;
import com.example.themelooks_users.Model.Products.Products_response;
import com.example.themelooks_users.R;
import com.google.android.material.button.MaterialButtonToggleGroup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class View_product_fragment extends Fragment {

    ImageView backButton;
    String productID, image, size = "small", color = "white";
    TextView productNameText, colorText, amountText, priceText, descriptionText, priceRangeText;
    ImageView productImage;
    Products_API productsApi;
    MaterialButtonToggleGroup toggleSizeButton, toggleColorButton;
    TextView decreaseButton, increaseButton;
    TextView orderAmountText;
    int amount;

    public View_product_fragment(String productID) {
        this.productID = productID;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        main();

        //price_product();
        price_data();
    }

    private void price_product() {
    }

    private void price_data() {

        productsApi.getPriceRange(productID, color, size).enqueue(new Callback<Price_range_response>() {
            @Override
            public void onResponse(Call<Price_range_response> call, Response<Price_range_response> response) {
                priceRangeText.setText(response.body().getPriceRange());
                amountText.setText(response.body().getAmount() + " " + "items available");
                amount = Integer.parseInt(response.body().getAmount());
            }

            @Override
            public void onFailure(Call<Price_range_response> call, Throwable t) {

            }
        });

        //Toast.makeText(getActivity(), size + " " + color, Toast.LENGTH_SHORT).show();
        productsApi.priceResponse(color, size).enqueue(new Callback<Price_response>() {
            @Override
            public void onResponse(Call<Price_response> call, Response<Price_response> response) {
                priceText.setText(response.body().getPrice());
            }

            @Override
            public void onFailure(Call<Price_response> call, Throwable t) {

            }
        });
    }


    private void main() {

        productsApi.getProductDetails(productID).enqueue(new Callback<Products_response>() {
            @Override
            public void onResponse(Call<Products_response> call, Response<Products_response> response) {

                productNameText.setText(response.body().getName());
                descriptionText.setText(response.body().getDescription());
                image = response.body().getImage();
                Bitmap bm = StringToBitMap(image);
                productImage.setImageBitmap(bm);

            }

            @Override
            public void onFailure(Call<Products_response> call, Throwable t) {

            }
        });

        Bitmap bm = StringToBitMap(image);
        productImage.setImageBitmap(bm);

    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.view_product_fragment, container, false);

        productsApi = APIUtilize.productsApi();

        toggleSizeButton = view.findViewById(R.id.toggleSizeButtonID);
        toggleColorButton = view.findViewById(R.id.toggleColorButtonID);

        toggleSizeButton.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    if (checkedId == R.id.smallID) {
                        size = "small";
                    } else if (checkedId == R.id.largeID) {
                        size = "large";
                    }

                    price_data();
                }
            }
        });

        toggleColorButton.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    if (checkedId == R.id.blackID) {
                        color = "black";
                    } else if (checkedId == R.id.whiteID) {
                        color = "white";
                    }

                    price_data();
                }
            }
        });


        backButton = (ImageView) view.findViewById(R.id.backButtonID);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        productNameText = view.findViewById(R.id.productNameID);
        descriptionText = view.findViewById(R.id.descriptionTextID);
        productImage = view.findViewById(R.id.productImageID);
        priceText = view.findViewById(R.id.priceTextID);
        priceRangeText = view.findViewById(R.id.priceRangeTextID);
        amountText = view.findViewById(R.id.amountTextID);
        decreaseButton = view.findViewById(R.id.decreaseButtonID);
        increaseButton = view.findViewById(R.id.increaseButtonID);
        orderAmountText = view.findViewById(R.id.orderAmountTextID);

        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orderAmountText.getText().toString().trim().equals("0")) {
                    orderAmountText.setText("0");
                } else {
                    int value = Integer.parseInt(orderAmountText.getText().toString().trim());
                    value = value - 1;
                    orderAmountText.setText(String.valueOf(value));
                }
            }
        });

        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orderAmountText.getText().toString().trim().equals(String.valueOf(amount))) {

                } else {
                    int value = Integer.parseInt(orderAmountText.getText().toString().trim());
                    value = value + 1;
                    orderAmountText.setText(String.valueOf(value));
                }
            }
        });

        return view;
    }
}