package com.example.themelooks_users.View.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.themelooks_users.Model.Registration.Register_response;
import com.example.themelooks_users.Model.Registration.Validation_response;
import com.example.themelooks_users.R;
import com.example.themelooks_users.ViewModel.RegisterViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Registration_fragment extends Fragment {

    ImageView backButton;
    TextInputEditText nameText, phoneText, passwordText, confirmPasswordText;
    TextInputLayout nameError, phoneError, passwordError, confirmPasswordError;
    AppCompatButton registerButton;
    RegisterViewModel registerViewModel;
    Dialog loaderDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.registration_fragment, container, false);

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        backButton = (ImageView) view.findViewById(R.id.backButtonID);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        nameText = (TextInputEditText) view.findViewById(R.id.nameTextID);
        phoneText = (TextInputEditText) view.findViewById(R.id.phoneTextID);
        passwordText = (TextInputEditText) view.findViewById(R.id.passwordTextID);
        confirmPasswordText = (TextInputEditText) view.findViewById(R.id.confirmPasswordTextID);

        nameError = (TextInputLayout) view.findViewById(R.id.nameErrorID);
        phoneError = (TextInputLayout) view.findViewById(R.id.phoneErrorID);
        passwordError = (TextInputLayout) view.findViewById(R.id.passwordErrorID);
        confirmPasswordError = (TextInputLayout) view.findViewById(R.id.confirmPasswordErrorID);

        registerButton = (AppCompatButton) view.findViewById(R.id.registerButtonID);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameText.getText().toString().trim();
                String phone = phoneText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                String confirmPassword = confirmPasswordText.getText().toString().trim();

                nameError.setErrorEnabled(false);
                phoneError.setErrorEnabled(false);
                passwordError.setErrorEnabled(false);
                confirmPasswordError.setErrorEnabled(false);

                if (TextUtils.isEmpty(name)) {
                    nameError.setError(" ");
                } else if (TextUtils.isEmpty(phone)) {
                    phoneError.setError(" ");
                } else if (TextUtils.isEmpty(password)) {
                    passwordError.setError(" ");
                } else if (TextUtils.isEmpty(confirmPassword)) {
                    confirmPasswordError.setError(" ");
                } else {
                    if (password.equals(confirmPassword)) {
                        validation(name, phone, password);
                    } else {
                        confirmPasswordError.setError("Password don't match");
                    }
                }

            }
        });

        loaderDialog = new Dialog(getActivity());
        loaderDialog.setContentView(R.layout.loader_alert);
        loaderDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loaderDialog.setCancelable(false);

        return view;
    }

    private void validation(String name, String phone, String password) {

        loaderDialog.show();
        // check validation API call, user available or not
        registerViewModel.getValidate(phone).observe(getViewLifecycleOwner(), new Observer<Validation_response>() {
            @Override
            public void onChanged(Validation_response validation_response) {
                String userID = String.valueOf(validation_response.getCustomerID());

                if (userID.equals("-1")) {
                    //register API call
                    registerViewModel.getRegister(name, phone, password).observe(getViewLifecycleOwner(), new Observer<Register_response>() {
                        @Override
                        public void onChanged(Register_response register_response) {
                            String message = register_response.getMessage();

                            loaderDialog.dismiss();
                            if (message.equals("registered")) {
                                Toast.makeText(getActivity(), "Registered", Toast.LENGTH_SHORT).show();
                                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                                        R.anim.slide_in,  // enter
                                        R.anim.fade_out,  // exit
                                        R.anim.fade_in,   // popEnter
                                        R.anim.slide_out  // popExit
                                ).replace(R.id.frame_container, new Login_fragment()).commit();
                            } else {
                                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    loaderDialog.dismiss();
                    Toast.makeText(getActivity(), "Already registered with this number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}