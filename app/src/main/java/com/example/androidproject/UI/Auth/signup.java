package com.example.androidproject.UI.Auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.androidproject.R;
import com.google.android.material.snackbar.Snackbar;

public class signup extends Fragment {
    private SignUpViewModel signUpViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        View root = inflater.inflate(R.layout.fragment_signup, container, false);

        EditText username = root.findViewById(R.id.signup_username);
        EditText email = root.findViewById(R.id.signup_email);
        EditText password = root.findViewById(R.id.signup_password);
        Button btn = root.findViewById(R.id.signup_btn);

        btn.setOnClickListener(v -> {}
//                signUpViewModel.signUp(
//                        username.getText().toString(),
//                        email.getText().toString(),
//                        password.getText().toString(),
//                        m -> Snackbar.make(root, "email is good", 5 * 1000).show(),
//                        m -> Snackbar.make(root, "invalid parameters (email or password)", 5 * 1000).show()
//                )
        );

        return root;
    }

}