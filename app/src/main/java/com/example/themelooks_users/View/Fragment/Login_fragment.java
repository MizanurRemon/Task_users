package com.example.themelooks_users.View.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.example.themelooks_users.Model.APIUtilize;
import com.example.themelooks_users.Model.Login.Login_API;
import com.example.themelooks_users.Model.Login.Login_response;
import com.example.themelooks_users.Model.Session_Management;
import com.example.themelooks_users.R;
import com.example.themelooks_users.ViewModel.LoginViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Login_fragment extends Fragment {

    AppCompatButton signInButton;
    TextInputEditText phoneText, passwordText;
    TextInputLayout phoneError, passwordError;
    LoginViewModel loginViewModel;
    Login_API loginApi;
    String userID;
    Session_Management session_management;
    Dialog loaderDialog;
    TextView registerButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        session_management = new Session_Management(getActivity());
        String userID = session_management.getSession();

        if (!userID.equals("-1")) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new Home_fragment()).addToBackStack(null).commit();
        }

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginApi = APIUtilize.loginApi();

        signInButton = (AppCompatButton) view.findViewById(R.id.signInButtonID);
        phoneText = (TextInputEditText) view.findViewById(R.id.phoneTextID);
        passwordText = (TextInputEditText) view.findViewById(R.id.passwordTextID);
        registerButton = (TextView) view.findViewById(R.id.registerButtonID);

        phoneError = (TextInputLayout) view.findViewById(R.id.phoneErrorID);
        passwordError = (TextInputLayout) view.findViewById(R.id.passwordErrorID);

        loaderDialog = new Dialog(getActivity());
        loaderDialog.setContentView(R.layout.loader_alert);
        loaderDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loaderDialog.setCancelable(false);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phoneText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();

                phoneError.setErrorEnabled(false);
                passwordError.setErrorEnabled(false);

                if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
                    if (TextUtils.isEmpty(phone)) {
                        phoneError.setError(" ");
                    } else if (TextUtils.isEmpty(password)) {
                        passwordError.setError(" ");
                    }
                } else {
                    loaderDialog.show();
                    sendData(phone, password);
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Registration_fragment()).addToBackStack(null).commit();

            }
        });

        return view;
    }

    private void sendData(String phone, String password) {
        loginViewModel.getMessage(phone, password).observe(getViewLifecycleOwner(), new Observer<Login_response>() {
            @Override
            public void onChanged(Login_response login_response) {
                loaderDialog.dismiss();

                userID = String.valueOf(login_response.getCustomerID());
                session_management.saveSession(userID);
                Toast.makeText(getActivity(), userID, Toast.LENGTH_SHORT).show();

                if (!userID.equals("-1")) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Home_fragment()).addToBackStack(null).commit();

                } else {
                    Toast.makeText(getActivity(), "Invalid phone/password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}