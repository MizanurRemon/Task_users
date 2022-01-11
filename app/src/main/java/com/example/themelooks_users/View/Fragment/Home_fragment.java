package com.example.themelooks_users.View.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themelooks_users.Adapter.Product_adapter;
import com.example.themelooks_users.Model.Products.Products_response;
import com.example.themelooks_users.Model.Profile.Profile_response;
import com.example.themelooks_users.Model.Session_Management;
import com.example.themelooks_users.R;
import com.example.themelooks_users.ViewModel.ProductsViewModel;
import com.example.themelooks_users.ViewModel.ProfileViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Home_fragment extends Fragment implements Product_adapter.OnItemClickListener{

    Session_Management session_management;
    String userID;
    ImageView logOutButton;
    Dialog loaderDialog;
    RecyclerView productView;
    ProductsViewModel productsViewModel;
    ProfileViewModel profileViewModel;
    private List<Products_response> productsList;
    private Product_adapter adapter;
    TextView nameText;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main();

        profile_func();

    }

    private void profile_func() {

        profileViewModel.getData(userID).observe(getViewLifecycleOwner(), new Observer<Profile_response>() {
            @Override
            public void onChanged(Profile_response profile_response) {
                nameText.setText(profile_response.getCustomerName());
            }
        });
    }

    private void main() {
        productsViewModel.getProduct().observe(getViewLifecycleOwner(), new Observer<List<Products_response>>() {
            @Override
            public void onChanged(List<Products_response> products_responses) {
                productsList = new ArrayList<>();
                productsList = products_responses;
                adapter = new Product_adapter(productsList);
                adapter.setOnClickListener(Home_fragment.this::OnItemClick);
                productView.setAdapter(adapter);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        productsViewModel = new ViewModelProvider(this).get(ProductsViewModel.class);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        session_management = new Session_Management(getActivity());
        userID = session_management.getSession();

        loaderDialog = new Dialog(getActivity());
        loaderDialog.setContentView(R.layout.loader_alert);
        loaderDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loaderDialog.setCancelable(false);

        logOutButton = (ImageView) view.findViewById(R.id.logOutButtonID);

        productView = (RecyclerView) view.findViewById(R.id.productViewID);

        nameText = (TextView) view.findViewById(R.id.nameTextID);

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaderDialog.show();
                session_management.removeSession();
                loaderDialog.dismiss();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Login_fragment()).addToBackStack(null).commit();

            }
        });

        productView.setHasFixedSize(true);
        productView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        return view;
    }

    @Override
    public void OnItemClick(int position) {
        Products_response response = productsList.get(position);
        String productID = String.valueOf(response.getProductsID());

        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        ).replace(R.id.frame_container, new View_product_fragment(productID)).addToBackStack(null).commit();
    }
}