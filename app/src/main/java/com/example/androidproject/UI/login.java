package com.example.androidproject.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.androidproject.Models.Model;
import com.example.androidproject.R;
import com.google.android.material.snackbar.Snackbar;

public class login extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        EditText email = view.findViewById(R.id.login_email);
        EditText password = view.findViewById(R.id.login_password);
        Button btn = view.findViewById(R.id.login_btn);
        btn.setOnClickListener(v -> Model.signInUser(email.getText().toString(), password.getText().toString(), m -> {
                    Snackbar.make(view, "parameters are valid", 5 * 1000).show();
                    //Navigation.findNavController(view).navigate(R.id.action_signup_to_login);
                },
                m -> Snackbar.make(view, "invalid parameters (email or password)", 5 * 1000).show()));

        return view;
    }
}